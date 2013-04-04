package br.tottou.data.action;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;


import br.tottou.action.login.Sessao;
import br.tottou.data.EmpresaDao;
import br.tottou.model.entities.Empresa;



@ManagedBean
@SessionScoped
public class ActionEmpresa {
	
	private String limpaBean;
	Empresa empresa = new Empresa();
	private String msg;
	private List<Empresa> listaEmpresa;
	private int selecao;
	private boolean qual;
	
	
	public Empresa getEmpresa () {
		return empresa;
	}
	
	public void setEmpresa (Empresa empresa) {
		this.empresa=empresa;
	}
	
	public void salvar () {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		SimpleDateFormat sdf2 = new SimpleDateFormat("kk:mm:ss");
		Date data = new Date(); 
		empresa.setData(sdf.format(data)+" - "+sdf2.format(data));
		
		
		
		
		try {
			EmpresaDao.salvarEmpresa(empresa);
			setMsg("Cadastro concluido com sucesso.");
			 empresa = new Empresa();
			
		} catch (Exception e) {
			
			setMsg("Ocorreu um erro ao cadastrar.");
			
		}
	}
	
	 public void save(ActionEvent actionEvent) {  
		 
		 salvar();
	        FacesContext context = FacesContext.getCurrentInstance();  
	          
	        context.addMessage(null, new FacesMessage("Cadastro de empresa", getMsg() ));  
 
	    }
	 
	 
	 public void popular () {
		 if (empresa.getId()==0 || empresa==null) {
			 System.out.println("NOPE");
		 }
		 empresa=EmpresaDao.getEmpresa(empresa.getId());
	 }
	 
	 public void mudar () {
		 if (getSelecao()==1) {
			 setQual(true);
		 }
		 else {
			 setQual(false);
		 }
	 }
	 
public void atualizar(ActionEvent actionEvent) {  
	EmpresaDao.atualizarEmpresa(empresa);
	try {
		
		setMsg("Edição concluida com sucesso.");
		empresa = new Empresa();
		
	} catch (Exception e) {
		
		setMsg("Ocorreu um erro ao editar.");
		
	}
			FacesContext context = FacesContext.getCurrentInstance();
	        context.addMessage(null, new FacesMessage("Edição de empresa", getMsg() ));  
 
	    }

public void deletar (ActionEvent ae) {
	
	 if (empresa.getId()!=0L) {
		 try {
			 EmpresaDao.remove(empresa);
			 FacesContext context = FacesContext.getCurrentInstance();
		        context.addMessage(null, new FacesMessage("Edição de empresa", "Cadastro de empresa removido." ));  
		        empresa = new Empresa();
		} catch (Exception e) {
			 FacesContext context = FacesContext.getCurrentInstance();
		        context.addMessage(null, new FacesMessage("Edição de empresa", "Empresa já possui dados importantes no sistema e não é possível apagar via cliente." ));
		}
		
	 } else {
		 FacesContext context = FacesContext.getCurrentInstance();
	        context.addMessage(null, new FacesMessage("Edição de empresa", "Empresa não selecionado" ));  
	 }
	 
	 
}

public void selecionarEmpresa(ActionEvent ae) {
	Sessao sessao = new Sessao();
	sessao.atualizarEmpresa(getEmpresa());
	 FacesContext context = FacesContext.getCurrentInstance();
     context.addMessage(null, new FacesMessage("", "Empresa selecionada" ));
}

public void selecionarEmpresaId(long id) {
	setEmpresa(EmpresaDao.getEmpresa(id));
	Sessao sessao = new Sessao();
	sessao.atualizarEmpresa(getEmpresa());
	 FacesContext context = FacesContext.getCurrentInstance();
     context.addMessage(null, new FacesMessage("", "Empresa selecionada" ));
}


public void selecionarEmp(long id) {
	setEmpresa(EmpresaDao.getEmpresa(id));
	Sessao sessao = new Sessao();
	sessao.atualizarEmpresa(getEmpresa());
	 FacesContext context = FacesContext.getCurrentInstance();
     context.addMessage(null, new FacesMessage("", "Empresa selecionada" ));
}



	 
	 public void limpar(ActionEvent e) {
		 empresa = new Empresa();
	 }

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getLimpaBean() {
		empresa = new Empresa();
		return limpaBean;
	}

	public void setLimpaBean(String limpaBean) {
		empresa = new Empresa();
		this.limpaBean = limpaBean;
	}

	public List<Empresa> getListaEmpresa() {
		listaEmpresa = EmpresaDao.list();
		return listaEmpresa;
	}

	public void setListaEmpresa(List<Empresa> listaEmpresa) {
		this.listaEmpresa = listaEmpresa;
	}

	public int getSelecao() {
		return selecao;
	}

	public void setSelecao(int selecao) {
		this.selecao = selecao;
	}

	public boolean isQual() {
		return qual;
	}

	public void setQual(boolean qual) {
		this.qual = qual;
	} 

}
