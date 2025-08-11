package persistencia;
import java.util.Properties;
import jakarta.mail.*;
import jakarta.mail.internet.*;
import java.io.File;
import java.io.IOException;


public class ServicioMensajeria {
	private static String micorreo = "tallerINET2025@gmail.com";
	private static String clave = "wjvj lssx adib dinn";
	
	private static String destino;
	private static String asunto="";
	private static String textoMensaje="";	
	private static String cuerpoHtml="";
	private static String rutaAdjunto="";
	
	public static boolean enviar(String textoMensaje, String destino) {		
		// Cuerpo HTML del correo
       /*	String cuerpoHtml = "<h2 style='color: navy;'>Hola desde Java</h2> "
	 		+ "<p>Este es un correo <strong>HTML</strong> con archivo adjunto.<br> "
	 		+ "¡Saludos!</p> "
	 		+ "";*/
		
		Properties propiedades = new Properties();
        propiedades.put("mail.smtp.auth", "true");
        propiedades.put("mail.smtp.host", "smtp.gmail.com");
        propiedades.put("mail.smtp.port", "465");
        propiedades.put("mail.smtp.ssl.enable", "true");
        propiedades.put("mail.smtp.ssl.trust", "smtp.gmail.com");
        
        Session sesion = Session.getInstance(propiedades, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(micorreo, clave);
            }
        });
        try {
            Message mail = new MimeMessage(sesion);
            mail.setFrom(new InternetAddress(micorreo));
            mail.setRecipients(Message.RecipientType.TO, InternetAddress.parse(destino));
            mail.setSubject(asunto);
            
            // Solo texto simple - sin multipart
            mail.setText(textoMensaje);
            
            Transport.send(mail);
            System.out.println("Correo enviado correctamente a: " + destino);
            return true;
            
        } catch (MessagingException e) {
            System.err.println("Error al enviar correo: " + e.getMessage());
            return false;
        }
        /* PROBAR LUEGO
        try {
        	 Message mail = new MimeMessage(sesion);
        	 mail.setFrom(new InternetAddress(micorreo));
        	 mail.setRecipients(Message.RecipientType.TO, InternetAddress.parse(destino));
        	 mail.setSubject(asunto);
        	 
        	// Parte de texto
             //MimeBodyPart cuerpoTexto = new MimeBodyPart();
             //cuerpoTexto.setText(mensaje);
             
        	 cuerpoHtml=textoMensaje;
             
             MimeBodyPart parteHtml = new MimeBodyPart();
             parteHtml.setContent(cuerpoHtml, "text/html; charset=utf-8");

             // Parte de archivo adjunto
             MimeBodyPart adjunto = new MimeBodyPart();
             adjunto.attachFile(new File(rutaAdjunto));

             // Unimos ambas partes en un contenedor Multipart
             Multipart multipart = new MimeMultipart();
             multipart.addBodyPart(parteHtml);
             //multipart.addBodyPart(cuerpoTexto);
             multipart.addBodyPart(adjunto);

             // Enviamos el contenido completo
             mail.setContent(multipart);
             Transport.send(mail);
             System.out.println("Correo enviado correctamente.");
        }
        catch (MessagingException e){
        	e.printStackTrace();
        }
        catch (IOException e) {
        	e.printStackTrace();
        }
	*/

	}
}
