package br.tottou.data.action;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.ServletContext;

import org.primefaces.event.CaptureEvent;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DualListModel;

import br.tottou.action.login.Sessao;
import br.tottou.data.AlunoDao;
import br.tottou.data.EmpresaDao;
import br.tottou.data.PerfilDao;
import br.tottou.engine.upload.ControleUploadA;
import br.tottou.engine.util.TrataListas;
import br.tottou.model.entities.Aluno;
import br.tottou.model.entities.Empresa;
import br.tottou.model.entities.Perfil;

@ManagedBean
@SessionScoped
public class ActionAluno {

	// String caminho =
	// System.getProperty("catalina.home")+"\\webapps\\SiGAA\\images\\fotos\\";
	// //usar no deploy em tomcat.
	// String caminho =
	// "C:\\Users\\Tottou\\Work\\Projetos de Psicologia\\Workspace\\SiGAA\\WebContent\\images\\fotos\\";
	ServletContext servletContext = (ServletContext) FacesContext
			.getCurrentInstance().getExternalContext().getContext();
	final String caminho = servletContext.getRealPath("") + File.separator;

	private String limpaBean;
	private String msg;
	Aluno aluno = new Aluno();
	Empresa empresa = new Empresa();
	private List<Aluno> listaAluno;
	private List<Aluno> listaAlunoTutor;
	private DualListModel<Perfil> listaProfessor;
	private DualListModel<Perfil> listaTutor;
	Date dataNascimento;
	Date hoje;
	Calendar cal = Calendar.getInstance();
	

	public Date getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(Date dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	public Empresa getEmpresa() {
		return empresa;
	}

	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}

	public Aluno getAluno() {
		return aluno;
	}

	public void setAluno(Aluno aluno) {
		this.aluno = aluno;
	}

	public void salvar() {
		Sessao sessao = new Sessao();
		if (sessao.getUsuario().getEmpresa().getId()!=0) {

			getEmpresa().setId(sessao.getUsuario().getEmpresa().getId());
		}

		if (getEmpresa().getId() == 0) {
			setMsg("Não é possível concluir o cadastro sem selecionar uma empresa.");

		} else {
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			SimpleDateFormat sdf2 = new SimpleDateFormat("kk:mm:ss");
			Date data = new Date();
			aluno.setData(sdf.format(data) + " - " + sdf2.format(data));
			aluno.setNascimento(sdf.format(getDataNascimento()));
			aluno.setFoto("/imagens/semFoto.png");
			setEmpresa(EmpresaDao.getEmpresa(getEmpresa().getId()));
			aluno.setEmpresa(getEmpresa());
			if (aluno.getObservacoes() == null
					|| aluno.getObservacoes().equals("")) {
				aluno.setObservacoes("Sem observações.");
			}
			if (aluno.getDiagnostico() == null
					|| aluno.getDiagnostico().equals("")) {
				aluno.setDiagnostico("Sem diagnóstico.");
			}

			try {
				AlunoDao.salvarAluno(aluno);
				setMsg("Cadastro concluido com sucesso.");
				aluno = new Aluno();
				dataNascimento = null;
			} catch (Exception e) {

				setMsg("Ocorreu um erro ao cadastrar.");

			}
		}
	}
	
	private void atribuindo() {
		getAluno().setProfessores(getListaProfessor().getTarget());
		getAluno().getProfessores().addAll(getListaTutor().getTarget());
		
		
	}

	public void atualizar(ActionEvent actionEvent) {	
		
		try {
			atribuindo();
			AlunoDao.atualizarAluno(aluno);
			setMsg("Edição concluida com sucesso.");
			aluno = new Aluno();

		} catch (Exception e) {

			setMsg("Ocorreu um erro ao editar.");

		}
		FacesContext context = FacesContext.getCurrentInstance();
		context.addMessage(null, new FacesMessage("Edição de aluno", getMsg()));

	}

	public void save(ActionEvent actionEvent) {

		salvar();
		FacesContext context = FacesContext.getCurrentInstance();

		context.addMessage(null,
				new FacesMessage("Cadastro de aluno", getMsg()));

	}

	public void popular() {
		if (aluno.getId() == 0 || aluno == null) {
			System.out.println("NOPE");
		}
		aluno = AlunoDao.getAluno(aluno.getId());
		//target
		selecionarprof();
		//getListaProfessor().setTarget(aluno.getProfessores());		
		//getListaTutor().setTarget(aluno.getTutores());
	}
	
	private void selecionarprof() {
		List<Perfil> lista = getAluno().getProfessores();
		getListaProfessor().setTarget(new ArrayList<Perfil>());
		getListaTutor().setTarget(new ArrayList<Perfil>());
		Iterator<Perfil> iter = lista.iterator();
		Perfil prof;
		
		while (iter.hasNext()) {
			prof=iter.next();
					if (prof.getCategoria()==2){
						getListaProfessor().getTarget().add(prof);
				
			}else {
				getListaTutor().getTarget().add(prof);
			}
		}
		
	}

	public Date getHoje() {
		setHoje(cal.getTime());
		return hoje;
	}

	public void setHoje(Date hoje) {
		this.hoje = hoje;
	}

	public void limpar(ActionEvent e) {
		aluno = new Aluno();
		dataNascimento = null;
		getLimpaBean();
	}

	public String getLimpaBean() {
		aluno = new Aluno();
		dataNascimento = null;
		return limpaBean;
	}

	public void setLimpaBean(String limpaBean) {
		aluno = new Aluno();
		dataNascimento = null;
		this.limpaBean = limpaBean;
	}

	public void upload(FileUploadEvent event) {
		ControleUploadA ContUp = new ControleUploadA();
		aluno.setFoto("/uploads/fotos/"
				+ ContUp.upload(event, caminho + "/uploads/fotos/",
						aluno.getId(), aluno.getFoto()));
		AlunoDao.atualizarAluno(aluno);
	}

	public void onCapture(CaptureEvent event) {
		ControleUploadA ContUp = new ControleUploadA();
		aluno.setFoto("/uploads/fotos/"
				+ ContUp.oncapture(event, caminho + "/uploads/fotos",
						aluno.getId(), aluno.getFoto()));
		AlunoDao.atualizarAluno(aluno);
	}

	public void deletar(ActionEvent ae) {

		if (aluno.getId() != 0L) {
			AlunoDao.remove(aluno);
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage("Edição de aluno",
					"Perfil de aluno removido."));
			aluno = new Aluno();
		} else {
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage("Edição de aluno",
					"Aluno não selecionado"));
		}

	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public List<Aluno> getListaAluno() {

		Sessao sessao = new Sessao();

		listaAluno = AlunoDao.listEmpresa(sessao.getUsuario().getEmpresa()
				.getId());
		return listaAluno;
	}

	public void setListaAluno(List<Aluno> listaAluno) {
		this.listaAluno = listaAluno;
	}

	public boolean isExiste() {
		if (aluno.getNome().equals("") || aluno.getNome().equals(null)) {
			return false;
		}
		return true;
	}

	public boolean isSelecionado() {
		if (aluno.getId() == 0) {
			return false;
		}
		return true;
	}

	@SuppressWarnings("unchecked")
	public List<Aluno> getListaAlunoTutor() {
		Sessao sessao = new Sessao();
		sessao.atualizaSessao();
		try {
			 
			 listaAlunoTutor = (List<Aluno>) sessao.getUsuario().getAlunos();
		} catch (Exception e) {
			listaAlunoTutor = new ArrayList<Aluno>();
		}
	
		
		return listaAlunoTutor;
	}

	public void setListaAlunoTutor(List<Aluno> listaAlunoTutor) {
		this.listaAlunoTutor = listaAlunoTutor;
	}

	public DualListModel<Perfil> getListaProfessor() {
		if (listaProfessor==null) {
			listaProfessor = new DualListModel<Perfil>();
		}
		//source
		Sessao sessao = new Sessao ();
		long emp = sessao.getUsuario().getEmpresa().getId();		
		//listaProfessor.setSource(PerfilDao.listEmpresaCategoria(emp, 2));
		
		listaProfessor.setSource(TrataListas.removerElementos(PerfilDao.listEmpresaCategoria(emp, 2), listaProfessor.getTarget()));
	
		return listaProfessor;
	}

	public void setListaProfessor(DualListModel<Perfil> listaProfessor) {
		this.listaProfessor = listaProfessor;
	}

	public DualListModel<Perfil> getListaTutor() {
		if (listaTutor==null) {
			listaTutor = new DualListModel<Perfil>();
		}
		//source
		Sessao sessao = new Sessao ();
		long emp = sessao.getUsuario().getEmpresa().getId();
		List<Perfil> listaComposta =  new ArrayList<Perfil>();
		listaComposta.addAll(PerfilDao.listEmpresaCategoria(emp, 3));
		listaComposta.addAll(PerfilDao.listEmpresaCategoria(emp, 4));
		//listaTutor.setSource(listaComposta);
		
		listaTutor.setSource(TrataListas.removerElementos(listaComposta, listaTutor.getTarget()));
		return listaTutor;
		
	}

	public void setListaTutor(DualListModel<Perfil> listaTutor) {
		this.listaTutor = listaTutor;
	}

}
