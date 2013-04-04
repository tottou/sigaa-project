package br.tottou.engine.email;

import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;




public class EnviarEmail {
	
	   private final static String usuario = "sigaa@walden4.com.br";  
	    private final static String senha = "4dms1944";  
	    private final static String remetente = "sigaa@walden4.com.br";  
	    private final static String smtp = "smtp.kinghost.net";  
	    private final static boolean authentication = true;  
	  
	    public static void sendMail(String menssagem, String titulo, String destinatario)  
	            throws EmailException {  
	  
	        SimpleEmail email = new SimpleEmail();  
	        email.setHostName(smtp);  
	        email.setAuthentication(usuario, senha);  
	        email.setSSL(authentication);  
	        email.addTo(destinatario);  
	        email.setFrom(remetente);  
	        email.setSubject(titulo);  
	        email.setMsg(menssagem);  
	        email.send();  
	        email = null;
	    }

}
