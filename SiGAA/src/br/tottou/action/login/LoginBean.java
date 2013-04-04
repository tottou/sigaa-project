package br.tottou.action.login;

import java.io.IOException;

import java.util.List;


import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;  
import javax.faces.context.FacesContext;  
import javax.servlet.http.HttpSession; 


import br.tottou.data.PerfilDao;
import br.tottou.engine.encrypt.CriptografiaMd5;
import br.tottou.model.entities.Empresa;
import br.tottou.model.entities.Perfil;

@ManagedBean  
public class LoginBean {  
  
	Perfil perfil = new Perfil();
    private String usuario;  
    private String senha; 
    
        
   
  
    public String autenticar() {  
    	setSenha(CriptografiaMd5.md5(getSenha()));
    	if (this.usuario.equals("superuser") && this.senha.equals("505a6ebc4656011eed85745d140fafd5")) {
    		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false); 
    		perfil.setNome("Superuser");
    		perfil.setLogin("superuser");
    		Empresa empresa = new Empresa();
    		empresa.setId(0);
    		empresa.setNome("(selecione uma empresa)");
    		perfil.setEmpresa(empresa);    		
    		perfil.setCategoria(0);
    		
    		 session.setAttribute("usuario", perfil); 
           
    		return "/main";
    	}
			
    	
        if (verificaUsuario(usuario, senha) == true) {  
        	HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false); 
            session.setAttribute("usuario", perfil);             
            
            return "/main";  
        } else {  
        	
        	FacesContext context = FacesContext.getCurrentInstance();
	        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,"Falha na autenticação - ", "Dados incorretos." )); 
            return "/index";  
        }  
    }  
  
    public String  sair() throws IOException {  
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);  
        session.removeAttribute("usuario");        
        session.invalidate();   
       
       return "/logout";  
    }  
  
    public String getSenha() {  
        return senha;  
    }  
  
    public void setSenha(String senha) {  
        this.senha = senha;  
    }  
  
    public String getUsuario() {  
        return usuario;  
    }  
  
    public void setUsuario(String usuario) {  
        this.usuario = usuario;  
    }  
    
    private boolean verificaUsuario (String usuario, String senha) {    	
    	List<Perfil> listaPerfil = PerfilDao.list();
    	if (listaPerfil.size()>0) {
    	for (int i = 0; i < listaPerfil.size(); i++) {
    		if (listaPerfil.get(i).getLogin().equals(usuario)) {
    			if (listaPerfil.get(i).getSenha().equals(senha)) {
    				perfil=listaPerfil.get(i);
    				return true;
    			}
    		}
    	 }
    	}
    	
    	return false;
    }




}  