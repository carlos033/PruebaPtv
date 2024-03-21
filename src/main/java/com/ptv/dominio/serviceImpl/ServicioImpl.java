package com.ptv.dominio.serviceImpl;

import java.io.File;
import java.io.IOException;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import com.itextpdf.commons.utils.Base64;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.colors.DeviceGray;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.properties.TextAlignment;
import com.ptv.aplicacion.dto.Registro;
import com.ptv.aplicacion.dto.Result;
import com.ptv.aplicacion.servicio.Servicio;
import com.ptv.dominio.excepcion.DatosNoValidos;
import com.ptv.dominio.excepcion.ErrorDeConexion;
import com.ptv.dominio.repositorio.UsuarioRespository;
import com.ptv.infraestructura.entidad.Usuario;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@AllArgsConstructor
@Service
public class ServicioImpl implements Servicio{
	private static final String NOMBRE_FICHERO = "fichero.pdf";
	private final JavaMailSender javaMailSender;
	private UsuarioRespository repositorio;
	private final WebClient.Builder webClientBuilder;

	@Override
	public void insert(Usuario usuario) throws DatosNoValidos {
		if (usuario == null) {
			throw new DatosNoValidos("Los datos del usuario no son válidos");
		}
		repositorio.save(usuario);
	}

	@CircuitBreaker(name = "accesoTecnicoService", fallbackMethod = "fallbackObtenerDatos")
	public Registro obtenerDatos(String codigoTecnico, String dni) throws ErrorDeConexion {
		String url = "http://212.225.255.130:8010/ws/accesotec/{codigoTecnico}/{dni}";

		WebClient webClient = webClientBuilder.build();

		Result resultado =
				webClient.get().uri(url, codigoTecnico, dni).retrieve().bodyToMono(Result.class).block();
		if (resultado == null) {
			throw new ErrorDeConexion(500, "error al conectar con el endpoit");
		}
		return resultado.getRegistro();
	}

	public Registro fallbackObtenerDatos(String codigoTecnico, String dni, Throwable t) {
		throw new ErrorDeConexion(503, "Servicio no disponible temporalmente");
	}

	@Override
	public void envioEmail(String destinatario) throws MessagingException {
		MimeMessage message = javaMailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message, true);
		helper.setFrom("pruebaptv2@gmail.com");
		helper.setTo(destinatario);
		helper.setSubject("Pdf de datos del trabajador");
		helper.setText("Buenos días.\n Le adjunto un pdf con sus datos,\n\n Saludos");

		log.info("adjuntamos el pdf");
		FileSystemResource file = new FileSystemResource(new File(NOMBRE_FICHERO));
		helper.addAttachment(NOMBRE_FICHERO, file);

		javaMailSender.send(message);
	}

	@Override
	public void crearPDF(String nombre, String email, String strImagen) throws IOException {
		try (PdfDocument pdfDoc = new PdfDocument(new PdfWriter(NOMBRE_FICHERO));
				Document documento = new Document(pdfDoc)) {
			// Configurar los márgenes del documento
			documento.setMargins(36, 36, 36, 36);

			// Añadir cabecera de la empresa
			documento.add(new Paragraph("Datos de la empresa").setFontColor(DeviceGray.WHITE)
					.setBackgroundColor(DeviceGray.BLACK).setBold().setFontSize(18)
					.setTextAlignment(TextAlignment.CENTER));
			documento.add(new Paragraph("Nombre: PROCONO S.A.U.").setMarginTop(10).setMarginBottom(5));
			documento.add(
					new Paragraph("Dirección: Avda. de Cádiz, nº 58, 14013-Córdoba.").setMarginBottom(5));
			documento.add(new Paragraph("Teléfono: +34 957 760 791.").setMarginBottom(10));

			// Añadir datos del trabajador
			documento.add(new Paragraph("\n\nDatos del trabajador").setFontColor(DeviceGray.BLACK)
					.setBold().setFontSize(14));
			documento.add(new Paragraph("Nombre: " + nombre));
			documento.add(new Paragraph("Email: " + email));

			// Añadir etiqueta "Firma:"
			documento.add(new Paragraph("Firma:"));

			// Añadir firma del trabajador si está disponible
			if (strImagen != null && !strImagen.isEmpty()) {
				Image img = base64ToImagen(strImagen);
				if (img != null) {
					documento.add(img);
				}
			}
		}
	}



	@Override
	public Image base64ToImagen(String imagen) throws IOException {
		final String base64Data = imagen.substring(imagen.indexOf(",") + 1);
		byte[] imageData = Base64.decode(base64Data);
		return new Image(ImageDataFactory.create(imageData));
	}
}
