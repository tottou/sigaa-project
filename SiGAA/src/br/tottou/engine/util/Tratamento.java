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
		      passa = passa.replaceAll("[�����]","A");  
		      passa = passa.replaceAll("[�����]","a");  
		      passa = passa.replaceAll("[����]","E");  
		      passa = passa.replaceAll("[����]","e");  
		      passa = passa.replaceAll("����","I");  
		      passa = passa.replaceAll("����","i");  
		      passa = passa.replaceAll("[�����]","O");  
		      passa = passa.replaceAll("[�����]","o");  
		      passa = passa.replaceAll("[����]","U");  
		      passa = passa.replaceAll("[����]","u");  
		      passa = passa.replaceAll("�","C");  
		      passa = passa.replaceAll("�","c");   
		      passa = passa.replaceAll("[��]","y");  
		      passa = passa.replaceAll("�","Y");  
		      passa = passa.replaceAll("�","n");  
		      passa = passa.replaceAll("�","N");  
		      passa = passa.replaceAll("['<>\\|/]","");  
		      return passa;  
		   } 


		
	public String tratarVideo(String linkVideo) {
		
		
			linkVideo=linkVideo.replace("youtube.com/watch?v=", "youtube.com/v/");
			linkVideo=linkVideo.replace("http://vimeo.com/", "http://vimeo.com/moogaloop.swf?clip_id=");
			
		return linkVideo;
	}
	
	
	
}
