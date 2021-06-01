/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.demo.servicios;

import com.example.demo.controlador.Controlador;
import com.example.demo.dto.Registro;
import com.example.demo.dto.Result;
import com.example.demo.exceptions.ExcepcionServicio;
import com.example.demo.modelos.Usuario;
import com.example.demo.repositorio.UsuarioRespository;
import com.example.demo.serviciosI.ServiciosI;
import com.itextpdf.text.BadElementException;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.codec.Base64;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.net.ssl.SSLContext;
import javax.transaction.Transactional;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Implementacion del servicio
 *
 * @author ck
 */
@Service("ServiciosI")
@Transactional
public class Servicios implements ServiciosI {

    @Autowired
    private UsuarioRespository repositorio;

    /**
     * Permite realizar un inser en la BD, en caso de que nu hubiera datos de
     * usuario saltaria una excepcion
     *
     * @param usuario
     * @throws ExcepcionServicio
     */
    @Override
    public void insert(Usuario usuario) throws ExcepcionServicio {
        if (usuario == null) {
            throw new ExcepcionServicio("Los datos del usuario no son validos");
        }
        repositorio.save(usuario);
    }

    /**
     * Permite obtener los datos del endpoint tras pasar los datos del login
     * para completar el link con las variables
     *
     * @param codigoTecnico
     * @param dni
     * @return un objeto Usuario con los datos de delntro del Result del XML
     * @throws ExcepcionServicio
     */
    @Override
    public Registro obtenerDatos(String codigoTecnico, String dni) throws ExcepcionServicio {
        Registro usuario = null;
        try {
            URL url = new URL("http://212.225.255.130:8010/ws/accesotec/" + codigoTecnico + "/" + dni);
            JAXBContext context = JAXBContext.newInstance(Result.class);
            Unmarshaller jaxbUnmarshaller = context.createUnmarshaller();
            Result resultado = (Result) jaxbUnmarshaller.unmarshal(url);
            usuario = resultado.getRegistro();
        } catch (JAXBException | MalformedURLException ex) {
            Logger.getLogger(Controlador.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (usuario == null) {
            throw new ExcepcionServicio("Los datos no son validos");
        }
        return usuario;
    }

    /**
     * Permite enviar un Email al correo que se le pase, y adjuta un archivo PDF
     *
     * @param destinatario
     */
    @Override
    public void envioEmail(String destinatario) {
        final String username = "pruebaptv2@gmail.com";
        final String password = "uniko4321";
        Properties props = System.getProperties();
        props.put("mail.smtp.host", "smtp.gmail.com");  //El servidor SMTP de Google
        props.put("mail.smtp.user", username);
        props.put("mail.smtp.clave", password);    //La clave de la cuenta
        props.put("mail.smtp.auth", "true");    //Usar autenticación mediante usuario y clave
        props.put("mail.smtp.starttls.enable", "true"); //Para conectar de manera segura al servidor SMTP
        props.put("mail.smtp.port", "587"); //El puerto SMTP seguro de Google
        props.put("mail.smtp.EnableSSL.enable", "true");
        Session session = Session.getDefaultInstance(props);
        MimeMessage message = new MimeMessage(session);
        try {
            SSLContext ctx = SSLContext.getInstance("TLSv1.2");
            ctx.init(null, null, null);
            SSLContext.setDefault(ctx);
            message.setFrom(new InternetAddress(username));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(destinatario));   //Se podrían añadir varios de la misma manera
            message.setSubject("Pdf de datos del trabajador");
            BodyPart mimeContenido = new MimeBodyPart();
            mimeContenido.setText("Buenos dias.\n Le adjunto un pdf con sus datos,"
                    + "\n\n Saludos");
            FileDataSource fichero = new FileDataSource("fichero.pdf");
            mimeContenido.setDataHandler(new DataHandler(fichero));
            mimeContenido.setFileName("fichero.pdf");
            MimeMultipart multiParte = new MimeMultipart();
            multiParte.addBodyPart(mimeContenido);
            message.setContent(multiParte);
            Transport t = session.getTransport("smtp");
            t.connect(username, password);
            t.sendMessage(message, message.getAllRecipients());
            t.close();
        } catch (MessagingException | KeyManagementException | NoSuchAlgorithmException e) {
        }
    }

    /**
     * Crea un PDF con los datos recogidos del formulario
     *
     * @param nombre
     * @param email
     * @param strImagen
     * @throws com.example.demo.exceptions.ExcepcionServicio
     */
    @Override
    public void crearPDF(String nombre, String email, String strImagen) throws ExcepcionServicio {
        Document documento = new Document(PageSize.LETTER, 20, 20, 20, 20);
        try {
            FileOutputStream ficheroPdf = null;
            ficheroPdf = new FileOutputStream("fichero.pdf");
            PdfWriter.getInstance(documento, ficheroPdf).setInitialLeading(20);
            documento.open();
            Font font = FontFactory.getFont(FontFactory.COURIER, 12, BaseColor.BLACK);
            Font chapterFont = FontFactory.getFont(FontFactory.HELVETICA, 26, Font.BOLDITALIC);
            Chunk chunk = new Chunk("Datos del trabajador", chapterFont);
            chunk.setBackground(BaseColor.GRAY);
            documento.add(chunk);
            Paragraph parrafo = new Paragraph("\n\n Nombre: " + nombre + "\n\n Email:" + email + "\n\n Firma \n\n\n", font);
            parrafo.setAlignment(Element.ALIGN_CENTER);
            documento.add(parrafo);
            Image img = base64ToImagen(strImagen);
            if (img != null) {
                documento.add(img);
            }
            documento.close();
            ficheroPdf.close();
        } catch (FileNotFoundException | DocumentException ex) {
            Logger.getLogger(Servicios.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Servicios.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Permite converitor una cadena de String a imagen
     *
     * @param imagen
     * @return una imagen
     * @throws com.example.demo.exceptions.ExcepcionServicio
     */
    @Override
    public Image base64ToImagen(String imagen) throws ExcepcionServicio {
        Image img = null;
        try {
            final String base64Data = imagen.substring(imagen.indexOf(",") + 1);
            img = Image.getInstance(Base64.decode(base64Data));
        } catch (BadElementException | IOException e) {
        }
        return img;
    }
}
