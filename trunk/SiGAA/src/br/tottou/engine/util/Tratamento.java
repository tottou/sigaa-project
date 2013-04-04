package br.tottou.engine.util;

import java.util.List;

import br.tottou.data.PerfilDao;
import br.tottou.model.entities.Perfil;

public class Tratamento {

	
	public String gerarLogin(String nome){
		String[] conjunto =nome.split("\\s+");
		nome=conjunto[0];
		for (int i = 1; i < conjunto.length; i++) {
			nome+=conjunto[i].charAt(0);
		}
		
		nome=trata(nome);
		nome=nome.toLowerCase();
		nome=verificaLogin(nome);
		
		return nome;
		
	}
	
	
	private String verificaLogin(String login){
		
		List<Perfil> listaP=PerfilDao.list();
		
		for (int i = 0; i < listaP.size(); i++) {
			if (listaP.get(i).getLogin().equals(login)){
				login+=i;
				return verificaLogin(login);
			}
		}
		return login;
	}
	
	
	   public String trata (String passa){  
		      passa = passa.replaceAll("[ÂÀÁÄÃ]","A");  
		      passa = passa.replaceAll("[âãàáä]","a");  
		      passa = passa.replaceAll("[ÊÈÉË]","E");  
		      passa = passa.replaceAll("[êèéë]","e");  
		      passa = passa.replaceAll("ÎÍÌÏ","I");  
		      passa = passa.replaceAll("îíìï","i");  
		      passa = passa.replaceAll("[ÔÕÒÓÖ]","O");  
		      passa = passa.replaceAll("[ôõòóö]","o");  
		      passa = passa.replaceAll("[ÛÙÚÜ]","U");  
		      passa = passa.replaceAll("[ûúùü]","u");  
		      passa = passa.replaceAll("Ç","C");  
		      passa = passa.replaceAll("ç","c");   
		      passa = passa.replaceAll("[ýÿ]","y");  
		      passa = passa.replaceAll("Ý","Y");  
		      passa = passa.replaceAll("ñ","n");  
		      passa = passa.replaceAll("Ñ","N");  
		      passa = passa.replaceAll("['<>\\|/]","");  
		      return passa;  
		   } 


		
	public String tratarVideo(String linkVideo) {
		
		
			linkVideo=linkVideo.replace("youtube.com/watch?v=", "youtube.com/v/");
			linkVideo=linkVideo.replace("http://vimeo.com/", "http://vimeo.com/moogaloop.swf?clip_id=");
			
		return linkVideo;
	}
	
	
	
}
