package br.tottou.action.login;

import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import br.tottou.data.PerfilDao;
import br.tottou.model.entities.Empresa;
import br.tottou.model.entities.Perfil;

@ManagedBean
public class Sessao {
			   
	    private String verificaSessao;
	    
	    Perfil usuario = new Perfil();
	    
	    
	    
	
		public Perfil getUsuario() {
			HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false); 
	        usuario= (Perfil) session.getAttribute("usuario"); 
			return usuario;
		}
	
		public void atualizarEmpresa(Empresa empresa) {
			Perfil user = getUsuario();
			user.setEmpresa(empresa);
			HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false); 
			session.setAttribute("usuario", user);
			
			
		}
		
		public void atualizaSessao(){
			HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false); 
	        usuario= (Perfil) session.getAttribute("usuario"); 
			usuario = PerfilDao.getPerfil(usuario.getId());
			session.setAttribute("usuario", usuario);
		}
	
		private String checkS(){
			HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false); 
	
	      if (session.isNew()) {
	    	  return "zero";
	      }
	      return "um";
	      
	       
		}

		public String getVerificaSessao() {
			verificaSessao=checkS();			
			return verificaSessao;
		}

		public void setVerificaSessao(String verificaSessao) {
			this.verificaSessao = verificaSessao;
		}
	    
}
