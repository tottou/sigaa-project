package br.tottou.data.action;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.ServletContext;

import org.apache.commons.mail.EmailException;
import org.primefaces.event.CaptureEvent;
import org.primefaces.event.FileUploadEvent;



import br.tottou.action.login.Sessao;
import br.tottou.data.EmpresaDao;
import br.tottou.data.PerfilDao;
import br.tottou.engine.email.EnviarEmail;
import br.tottou.engine.upload.ControleUpload;
import br.tottou.engine.util.GeraSenha;
import br.tottou.engine.util.Tratamento;
import br.tottou.model.entities.Empresa;
import br.tottou.model.entities.Perfil;

@ManagedBean
@SessionScoped
public class ActionPerfil {
	
	//String caminho = System.getProperty("catalina.home")+"\\webapps\\SiGAA\\images\\fotos\\"; //usar no deploy em tomcat.
	//String caminho = "C:\\Users\\Tottou\\Work\\Projetos de Psicologia\\Workspace\\SiGAA\\WebContent\\images\\fotos\\";
	ServletContext servletContext = (ServletContext) FacesContext
			.getCurrentInstance().getExternalContext().getContext();
		final String caminho = servletContext.getRealPath("") + File.separator;
	
	private String msg;
	private String limpaBean;
	private String popularUser;
	
	Perfil perfil = new Perfil();
	Empresa empresa = new Empresa();

	private List<Perfil> listaPerfil;	
	private List<Empresa> listaEmpresa;
	private String senhaAntiga;
	private String senhaNova="senhaNova";
	
	
	
	public Empresa getEmpresa() {
		return empresa;
	}

	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}
	
	public Perfil getPerfil () {
		return perfil;
	}
	
	public void setPerfil (Perfil perfil) {
		this.perfil=perfil;
	}
	
	private void salvar () {	
		Sessao sessao = new Sessao();
		
		if (sessao.getUsuario().getEmpresa().getId()!=0) {
			
			getEmpresa().setId(sessao.getUsuario().getEmpresa().getId());
		}
		
		if (getEmpresa().getId()==0 ) {
			setMsg("Não é possível concluir o cadastro sem selecionar uma empresa.");
			
		} else {
			
		setEmpresa(EmpresaDao.getEmpresa(getEmpresa().getId()));
		perfil.setEmpresa(getEmpresa());
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		SimpleDateFormat sdf2 = new SimpleDateFormat("kk:mm:ss");
		Date data = new Date(); 
		perfil.setData(sdf.format(data)+" - "+sdf2.format(data));
		perfil.setFoto("/imagens/semFoto.png");
		Tratamento t = new Tratamento();		
		perfil.setLogin(t.gerarLogin(perfil.getNome()));
		GeraSenha gs = new GeraSenha();
		try {
			String senha = gs.gerarSenha(8);
			perfil.setSenha(senha);
		} catch (Exception e1) {
			setMsg("Erro no sistema - PW MISS");
			e1.printStackTrace();
		}
		
		
		
		try {
			PerfilDao.salvarPerfil(perfil);
			setMsg("Cadastro concluido com sucesso.");
			
			
		} catch (Exception e) {
			System.out.println(e);
			setMsg("Ocorreu um erro ao cadastrar.");
			
			}
		
		try {
			enviarEmail();
			perfil = new Perfil();
			setMsg("Cadastro concluido com sucesso.");
		} catch (Exception e) {
			System.out.println(e);
			perfil = new Perfil();
			setMsg("Cadastro concluido mas ocorreu um erro ao enviar o e-mail.");
		}
		}
	
	}
	
	 public void save(ActionEvent actionEvent) {  
		 FacesContext context = FacesContext.getCurrentInstance(); 
		 if (checkEmail(perfil.getEmail())) {
			 context.addMessage(null, new FacesMessage("Cadastro de usuário", "E-mail inserido já cadastrado."));
			
			}
		 else {
			 
		 
		 salvar();
	        	          
	        context.addMessage(null, new FacesMessage("Cadastro de usuário", getMsg() ));  
		 }
	    }
	 
	 public void limpar (ActionEvent ae) {
		 perfil = new Perfil();
	 }
	 
	 private void enviarEmail() throws EmailException {
		 EnviarEmail.sendMail("Olá "+perfil.getNome() +",\n\n Seus dados para acessar o sistema SiGAA são:\n" +
		 		" Login: "+perfil.getLogin() +"\n Senha: "+perfil.getSenha()+"\n Caso exista alguma dúvida, entrar em contato com a administração" +
		 				" do sistema. \n \n Cordialmente, \n Administração SiGAA", "Cadastro concluído - Dados de acesso",perfil.getEmail());
		 
		 FacesContext context = FacesContext.getCurrentInstance();
	        context.addMessage(null, new FacesMessage("Envio de e-mail", "Dados enviados com sucesso!" )); 
	 }
	 
	 public void enviar (ActionEvent ae) {
		 try {
			enviarEmail();
		} catch (EmailException e) {
			FacesContext context = FacesContext.getCurrentInstance();
	        context.addMessage(null, new FacesMessage("Envio de e-mail", "Falha ao enviar" ));
			e.printStackTrace();
		}
	 }
	 	 
 
	
	 public void popular () {
		 if (perfil==null) {
			 System.out.println("NOPE");
		 }
		 perfil=PerfilDao.getPerfil(perfil.getId());
	 }
	 
public void atualizar(ActionEvent actionEvent) {  
	
	try {
		PerfilDao.atualizarPerfil(perfil);
		setMsg("Edição concluida com sucesso.");
		//perfil = new Perfil();
		
	} catch (Exception e) {
		
		setMsg("Ocorreu um erro ao editar.");
		
	}
			FacesContext context = FacesContext.getCurrentInstance();
	        context.addMessage(null, new FacesMessage("Edição de usuário", getMsg() ));  
 
	    }


			 
	 
	 public void upload (FileUploadEvent event) {
		 ControleUpload ContUp = new ControleUpload();			 
		 perfil.setFoto("/uploads/fotos/"+ContUp.upload(event, caminho+"/uploads/fotos/", perfil.getId(), perfil.getFoto()));		
		 PerfilDao.atualizarPerfil(perfil);
	 }
	 
	 public void onCapture (CaptureEvent event) {
		 ControleUpload ContUp = new ControleUpload();
		 perfil.setFoto("/uploads/fotos/"+ContUp.oncapture(event, caminho+"/uploads/fotos/", perfil.getId(), perfil.getFoto()));
		 PerfilDao.atualizarPerfil(perfil);
	 }
		 
	 
	 public void deletar (ActionEvent ae) {
		
		 if (perfil.getId()!=0L) {
			 PerfilDao.remove(perfil);
			 FacesContext context = FacesContext.getCurrentInstance();
		        context.addMessage(null, new FacesMessage("Edição de usuário", "Perfil de usuário removido." ));  
		        perfil = new Perfil();
		 } else {
			 FacesContext context = FacesContext.getCurrentInstance();
		        context.addMessage(null, new FacesMessage("Edição de usuário", "Usuário não selecionado" ));  
		 }
		 
		 
	 }
	 
	 public void mudarSenha(ActionEvent ae) {
		 if (perfil.getSenha().equals(getSenhaAntiga())) {
			 
			 perfil.setSenha(getSenhaNova());
			 FacesContext context = FacesContext.getCurrentInstance();
		        context.addMessage(null, new FacesMessage("Alteração de senha", "Senha alterada." )); 
		        context.addMessage(null, new FacesMessage("Atualização requirida", "Atualize o perfil para a modificação de senha surtir efeito." ));
			 
			 
		 } else {
			 FacesContext context = FacesContext.getCurrentInstance();
		        context.addMessage(null, new FacesMessage("Alteração de senha", "Senha atual não confere." )); 
		 }
			 			
			
		 
	 }
	 	 
	 
	private boolean checkEmail (String email) {
		getListaPerfil();
			for (int i = 0; i < listaPerfil.size(); i++) {
				if(listaPerfil.get(i).getEmail().equals(email)) {
					return true;
				}
			}
			return false;
	 }

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public List<Perfil> getListaPerfil() {
		Sessao sessao = new Sessao();
		
		listaPerfil=PerfilDao.listEmpresa(sessao.getUsuario().getEmpresa().getId());
		return listaPerfil;
	}

	public void setListaPerfil(List<Perfil> listaPerfil) {
		this.listaPerfil = listaPerfil;
	}

	public String getLimpaBean() {
		perfil = new Perfil();
		return limpaBean;
	}

	public void setLimpaBean(String limpaBean) {
		perfil = new Perfil();
		this.limpaBean = limpaBean;
	}

	public boolean isExiste() {
		if (perfil.getNome().equals("") || perfil.getNome().equals(null) ) {
			return false;
		}
		return true;
	}

	public boolean isSelecionado() {
		if (perfil.getId()==0) {
			return false;
		}
		return true;
	}

	public String getSenhaAntiga() {
		return senhaAntiga;
	}

	public void setSenhaAntiga(String senhaAntiga) {
		this.senhaAntiga = senhaAntiga;
	}

	public String getSenhaNova() {		
		return senhaNova;
		
	}

	public void setSenhaNova(String senhaNova) {		
		this.senhaNova = senhaNova;
		
	}

	public List<Empresa> getListaEmpresa() {
		listaEmpresa=EmpresaDao.list();
		return listaEmpresa;
	}

	public void setListaEmpresa(List<Empresa> listaEmpresa) {
		this.listaEmpresa = listaEmpresa;
	}

	public String getPopularUser() {
		Sessao sessao = new Sessao();
		 setPerfil(sessao.getUsuario());
		return popularUser;
	}

	public void setPopularUser(String popularUser) {
		this.popularUser = popularUser;
	}








}
