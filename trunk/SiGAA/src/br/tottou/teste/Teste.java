package br.tottou.teste;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.ServletContext;

import br.tottou.engine.encrypt.CriptografiaMd5;

@ManagedBean
@RequestScoped
public class Teste {
	
	
	private String realpath;
	private String catalinaHome;
	private String user;
	
	private String senhaCripto;
	private List<String> listaSenhas;
	
	
	
	
	
	
	public void show (ActionEvent e) {
		
		ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();  
	    setRealpath(servletContext.getRealPath(""));
	    setCatalinaHome(System.getProperty("catalina.home"));
	    setUser(System.getProperty("user.home"));
		
		
	}
	//
	
	public void criptografia () {
		getListaSenhas().add(CriptografiaMd5.md5(getSenhaCripto()));
		
	}
	
    
    
    
	public String getRealpath() {
		return realpath;
	}
	public void setRealpath(String realpath) {
		this.realpath = realpath;
	}





	public String getCatalinaHome() {
		return catalinaHome;
	}





	public void setCatalinaHome(String catalinaHome) {
		this.catalinaHome = catalinaHome;
	}





	public String getUser() {
		return user;
	}





	public void setUser(String user) {
		this.user = user;
	}

	public String getSenhaCripto() {
		return senhaCripto;
	}

	public void setSenhaCripto(String senhaCripto) {
		this.senhaCripto = senhaCripto;
	}

	public List<String> getListaSenhas() {
		if (listaSenhas==null) {
			listaSenhas= new ArrayList<String>();
		}
		return listaSenhas;
	}

	public void setListaSenhas(List<String> listaSenhas) {
		this.listaSenhas = listaSenhas;
	} 

    
    
}
