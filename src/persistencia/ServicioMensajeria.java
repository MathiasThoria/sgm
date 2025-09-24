package persistencia;
import java.util.Properties;
import jakarta.mail.*;
import jakarta.mail.internet.*;
import java.io.File;
import java.io.IOException;

/* Se encarga de conexion y envio de mail 
 * 
 */
public class ServicioMensajeria {
	private String micorreo = "tallerINET2025@gmail.com";
	private String clave = "wjvj lssx adib dinn";	
	private String destino;
	private String asunto="";
	private String textoMensaje="";	
	private String cuerpoHtml="";
	private String rutaAdjunto="";
	
	
	public ServicioMensajeria() {		
	}
	
	public boolean enviar(String textoMensaje, String destino) {	
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
        

	}
}
