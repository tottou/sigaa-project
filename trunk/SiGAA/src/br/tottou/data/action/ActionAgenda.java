package br.tottou.data.action;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import org.primefaces.event.DateSelectEvent;
import org.primefaces.event.ScheduleEntryMoveEvent;
import org.primefaces.event.ScheduleEntryResizeEvent;
import org.primefaces.event.ScheduleEntrySelectEvent;
import org.primefaces.model.DefaultScheduleModel;
import org.primefaces.model.DualListModel;
import org.primefaces.model.ScheduleEvent;
import org.primefaces.model.ScheduleModel;

import br.tottou.action.login.Sessao;
import br.tottou.data.AgendaDao;
import br.tottou.data.AlunoDao;
import br.tottou.data.PerfilDao;
import br.tottou.data.TarefaDao;
import br.tottou.model.EventoAgenda;
import br.tottou.model.entities.Agenda;
import br.tottou.model.entities.Aluno;
import br.tottou.model.entities.Perfil;
import br.tottou.model.entities.ProgPassos;
import br.tottou.model.entities.Tarefa;

@ManagedBean
@SessionScoped
public class ActionAgenda {

	
	private Aluno aluno = new Aluno();
	private Agenda agenda = new Agenda();
	private ProgPassos passos = new ProgPassos();
	
	private String limpaCache;
	private boolean acertou;
	private Integer rating;
	private String observacoes;
	private int count;
	
	private String inicio;
	private String fim;
	private String mudarValor;
	private String ajustaHora;
	
	private boolean check=false;

	private List<Aluno> listaAluno;
	private List<Aluno> listaAlunoTutor;
	private List<Long> listaProfId;
	private List<Perfil> listaProfEmp;

	private ScheduleModel eventModel = new DefaultScheduleModel();
	private ScheduleModel lazyEventModel;
	private ScheduleEvent event = new EventoAgenda(agenda, null, null);
	
	private DualListModel<Tarefa> listaDualTarefa;
	
	
	public ActionAgenda() {
		Sessao sessao = new Sessao();
		long userid=sessao.getUsuario().getId();
		long empid=sessao.getUsuario().getEmpresa().getId();
		eventModel= new DefaultScheduleModel();  
		List<Agenda> listaAgenda= new ArrayList<Agenda>();
		if (sessao.getUsuario().getCategoria()==1 || sessao.getUsuario().getCategoria()==0) {
			listaAgenda = AgendaDao.listEmpresa(empid);
		}
		if (sessao.getUsuario().getCategoria()>1) {
			listaAgenda.addAll(PerfilDao.getPerfil(userid).getAgenda());
		}
	
		for (int i = 0; i < listaAgenda.size(); i++) {
			eventModel.addEvent(new EventoAgenda(listaAgenda.get(i), listaAgenda.get(i).getInicio(), listaAgenda.get(i).getFim()));  			
		}
	}
	

	public void popular() {
		setAluno(AlunoDao.getAluno(getAluno().getId()));

	}
	
	public void salvar(){
		if (getListaProfId().isEmpty()) {
			 FacesContext context = FacesContext.getCurrentInstance();
			 context.addMessage(null, new FacesMessage("Falha ao salvar",
			 "Selecione um professor para a sessão"));
			 return;
		}
		if (getAgenda().getSessoes()<1) {
			 FacesContext context = FacesContext.getCurrentInstance();
			 context.addMessage(null, new FacesMessage("Falha ao salvar",
			 "O número de sessões tem que ser maior que 1"));
			 return;
		}
		
		if (getListaDualTarefa().getTarget().size()<1) {
			 FacesContext context = FacesContext.getCurrentInstance();
			 context.addMessage(null, new FacesMessage("Falha ao salvar",
			 "Selecione tarefas para esta sessão."));
			 return;
		}
		
		
//		Iterator<String> it = getListaProfId().iterator();
//		while (it.hasNext()) {
//			String a= it.next();
//			long id =Long.parseLong(a) ;
//			getAgenda().getProfessores().add(PerfilDao.getPerfil(id));
//			
//		}
		Set<Perfil> lista=new HashSet<Perfil>();
		for (int i = 0; i < getListaProfId().size(); i++) {
			long id = (getListaProfId().get(i));
			System.out.println(getListaProfId().get(i));
			System.out.println(id);
			getAgenda();
			//getAgenda().getProfessores();
			
			Perfil perfil = PerfilDao.getPerfil(id);
			lista.add(perfil);
			//getAgenda().getProfessores().add(PerfilDao.getPerfil(id));
		}
		
			getAgenda().setProfessores(lista);
		
	
		getAgenda().setAluno(getAluno());
		getAgenda().getTarefas().addAll(getListaDualTarefa().getTarget());
		Sessao ses = new Sessao();
		getAgenda().setEmpresa(ses.getUsuario().getEmpresa());		
		getAgenda().setInicio(getEvent().getStartDate());
		getAgenda().setFim(getEvent().getEndDate());
		getAgenda().setNome(getEvent().getTitle());
		getAgenda().setRemaining(1l);		
		 if(event.getId() == null)  
	            eventModel.addEvent(event);  
	        else  
	            eventModel.updateEvent(event); 
		 
		try {
			AgendaDao.atualizarAgenda(getAgenda());
			event = new EventoAgenda(new Agenda(), null, null); 
			setAgenda(new Agenda());
			setAluno(new Aluno());
			setListaDualTarefa(new DualListModel<Tarefa>());
			setCheck(true);
			FacesContext context = FacesContext.getCurrentInstance();
			 context.addMessage(null, new FacesMessage("Agenda criada",
			 "com sucesso!"));			 
		} catch (Exception e) {
			FacesContext context = FacesContext.getCurrentInstance();
			 context.addMessage(null, new FacesMessage("Agenda",
			 "Ocorreu um erro ao tentar salvar."));
			 System.out.println(e);
		}
		
		
	}
	
	public void updateAgenda(){
		
	}
	
	///Eventos ajax agenda
	
	 public void addEvent(ActionEvent actionEvent) {  
	        if(event.getId() == null)  
	            eventModel.addEvent(event);  
	        else  
	            eventModel.updateEvent(event);  
	          
	      //  event = new EventoAgenda();  
	    }  
	      
	    public void onEventSelect(ScheduleEntrySelectEvent selectEvent) {	    	
	        event = selectEvent.getScheduleEvent();  
	    }  
	      
	    public void onDateSelect(DateSelectEvent selectEvent) {  
	        event = new EventoAgenda(new Agenda(), selectEvent.getDate(), selectEvent.getDate());  
	    }  
	      
	    public void onEventMove(ScheduleEntryMoveEvent event) {  
	    	EventoAgenda ea= (EventoAgenda) eventModel.getEvent(event.getScheduleEvent().getId());
	    	Agenda ag=ea.getObjetoAgenda();
	    	ag.setInicio(ea.getStartDate());
	    	ag.setFim(ea.getEndDate());
	    	AgendaDao.atualizarAgenda(ag);
	        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Agenda", " Data da sessão modificada");  	         
	        addMessage(message);  
	    }  
	      
	    public void onEventResize(ScheduleEntryResizeEvent event) {  
	    	EventoAgenda ea= (EventoAgenda) eventModel.getEvent(event.getScheduleEvent().getId());
	    	Agenda ag=ea.getObjetoAgenda();
	    	ag.setInicio(ea.getStartDate());
	    	ag.setFim(ea.getEndDate());
	    	AgendaDao.atualizarAgenda(ag);
	        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Agenda", " Horário da sessão modificado");  	
	          
	        addMessage(message);  
	    }  
	      
	    private void addMessage(FacesMessage message) {  
	        FacesContext.getCurrentInstance().addMessage(null, message);  
	    }  
	      
	///
	    ///acionando tarefa
	    
	    public void proximoPasso(){
	    	
	    }
	    
	    public void repetirPasso() {
	    	
	    }
	    public void incrementar() {
	    	count++;
	    }


	public Aluno getAluno() {
		return aluno;
	}

	public void setAluno(Aluno aluno) {
		this.aluno = aluno;
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

	public List<Perfil> getListaProfEmp() {
		Sessao sessao = new Sessao();
		listaProfEmp = PerfilDao.listEmpresaCategoria(sessao.getUsuario()
				.getEmpresa().getId(), 1);
		return listaProfEmp;
	}

	public void setListaProfEmp(List<Perfil> listaProfEmp) {
		this.listaProfEmp = listaProfEmp;
	}

	public ScheduleModel getEventModel() {
	
		return eventModel;
	}

	public void setEventModel(ScheduleModel eventModel) {
		this.eventModel = eventModel;
	}

	public ScheduleModel getLazyEventModel() {
		return lazyEventModel;
	}

	public void setLazyEventModel(ScheduleModel lazyEventModel) {
		this.lazyEventModel = lazyEventModel;
	}

	public ScheduleEvent getEvent() {
		return event;
	}

	public void setEvent(ScheduleEvent event) {
		this.event = event;
	}





	public DualListModel<Tarefa> getListaDualTarefa() {
		if (listaDualTarefa==null) {
			listaDualTarefa= new DualListModel<Tarefa>();			
		}
		Sessao sessao = new Sessao();
		long id_empresa = sessao.getUsuario().getEmpresa().getId();
		listaDualTarefa.setSource(TarefaDao.listEmpresa(id_empresa));
		return listaDualTarefa;
	}





	public void setListaDualTarefa(DualListModel<Tarefa> listaDualTarefa) {
		this.listaDualTarefa = listaDualTarefa;
	}

	public Agenda getAgenda() {
		return agenda;
	}

	public void setAgenda(Agenda agenda) {
		this.agenda = agenda;
	}

	public List<Long> getListaProfId() {
		if (listaProfId==null) {
			listaProfId= new ArrayList<Long>();
		}
		return listaProfId;
	}

	public void setListaProfId(List<Long> listaProfId) {
		this.listaProfId = listaProfId;
	}

	public String getInicio() {		
		if (getEvent().getStartDate()==null) {
			inicio="";
			return inicio;
		}
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		inicio=sdf.format(getEvent().getStartDate());
		return inicio;
	}

	public void setInicio(String inicio) {
		this.inicio = inicio;
	}

	public String getFim() {
		if (getEvent().getEndDate()==null) {
			fim="";
			return fim;
		}
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		fim=sdf.format(getEvent().getEndDate());
		return fim;
	}

	public void setFim(String fim) {
		this.fim = fim;
	}


	public boolean isCheck() {
		return check;
	}


	public void setCheck(boolean check) {
		this.check = check;
	}


	public String getMudarValor() {
		setCheck(false);
		return mudarValor;
	}


	public void setMudarValor(String mudarValor) {
		this.mudarValor = mudarValor;
	}


	public String getLimpaCache() {
		event = new EventoAgenda(new Agenda(), null, null); 
		setAgenda(new Agenda());
		setAluno(new Aluno());
		setListaDualTarefa(new DualListModel<Tarefa>());
		setCheck(false);
		return limpaCache;
	}


	public void setLimpaCache(String limpaCache) {
		this.limpaCache = limpaCache;
	}


	public String getAjustaHora() {
		event.getStartDate().setTime(event.getStartDate().getTime() +(8*3600000));
		event.getEndDate().setTime(event.getEndDate().getTime() +(9*3600000));		
		return ajustaHora;
	}


	public void setAjustaHora(String ajustaHora) {
		this.ajustaHora = ajustaHora;
	}


	public boolean isAcertou() {
		return acertou;
	}


	public void setAcertou(boolean acertou) {
		this.acertou = acertou;
	}


	public ProgPassos getPassos() {
		return passos;
	}


	public void setPassos(ProgPassos passos) {
		this.passos = passos;
	}


	public Integer getRating() {
		return rating;
	}


	public void setRating(Integer rating) {
		this.rating = rating;
	}


	public String getObservacoes() {
		return observacoes;
	}


	public void setObservacoes(String observacoes) {
		this.observacoes = observacoes;
	}


	public int getCount() {
		return count;
	}


	public void setCount(int count) {
		this.count = count;
	}





}
