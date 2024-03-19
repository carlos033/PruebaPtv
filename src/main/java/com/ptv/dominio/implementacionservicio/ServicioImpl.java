package com.ptv.dominio.implementacionservicio;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import com.itextpdf.commons.utils.Base64;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.colors.DeviceGray;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
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
import com.ptv.dominio.repositorio.UsuarioRespository;
import com.ptv.infraestructura.entidad.Usuario;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.Unmarshaller;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@AllArgsConstructor
@Service
public class ServicioImpl implements Servicio{
	private static final String NOMBRE_FICHERO = "fichero.pdf";
	private final JavaMailSender javaMailSender;
	private UsuarioRespository repositorio;

	@Override
	public void insert(Usuario usuario) throws DatosNoValidos {
		if (usuario == null) {
			throw new DatosNoValidos("Los datos del usuario no son válidos");
		}
		repositorio.save(usuario);
	}

	public Registro obtenerDatos(String codigoTecnico, String dni) throws Exception {
		// URL del endpoint
		String url = "http://212.225.255.130:8010/ws/accesotec/" + codigoTecnico + "/" + dni;

		// Crear cliente HTTP
		HttpClient httpClient = HttpClient.newHttpClient();

		// Crear solicitud HTTP GET
		HttpRequest httpRequest = HttpRequest.newBuilder().uri(URI.create(url)).build();

		// Enviar la solicitud y obtener la respuesta
		HttpResponse<String> httpResponse =
				httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());

		// Verificar el código de estado de la respuesta
		int statusCode = httpResponse.statusCode();
		if (statusCode != 200) {
			throw new Exception("Error al obtener los datos. Código de estado: " + statusCode);
		}

		// Deserializar el XML en un objeto Registro usando JAXB
		JAXBContext jaxbContext = JAXBContext.newInstance(Result.class);
		Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
		Result resultado = (Result) jaxbUnmarshaller.unmarshal(new StringReader(httpResponse.body()));

		return resultado.getRegistro();
	}

	// @Override
	// public Registro obtenerDatos(String codigoTecnico, String dni) throws Exception {
	// Registro usuario = null;
	// try {
	// URL url = new URL("http://212.225.255.130:8010/ws/accesotec/" + codigoTecnico + "/" + dni);
	// JAXBContext context = JAXBContext.newInstance(Result.class);
	// Unmarshaller jaxbUnmarshaller = context.createUnmarshaller();
	// Result resultado = (Result) jaxbUnmarshaller.unmarshal(url);
	// usuario = resultado.getRegistro();
	// } catch (JAXBException | MalformedURLException ex) {
	// throw new ErrorDeConexion(500, ex.getCause());
	// }
	// if (usuario == null) {
	// throw new DatosNoValidos("Los datos no son validos");
	// }
	// return usuario;
	// }

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

			PdfFont font = PdfFontFactory.createFont();

			documento.add(new Paragraph("Datos del trabajador").setFont(font).setBold()
					.setBackgroundColor(DeviceGray.GRAY));

			documento
					.add(new Paragraph("\n\n Nombre: " + nombre + "\n\n Email:" + email + "\n\n Firma \n\n\n")
							.setFont(font).setTextAlignment(TextAlignment.CENTER));

			Image img = base64ToImagen(strImagen);
			if (img != null) {
				documento.add(img);
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
