package br.tottou.data.action;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.event.ActionEvent;

import org.primefaces.event.ScheduleEntrySelectEvent;
import org.primefaces.model.DefaultScheduleModel;
import org.primefaces.model.ScheduleEvent;
import org.primefaces.model.ScheduleModel;


import br.tottou.action.login.Sessao;
import br.tottou.data.AgendaDao;
import br.tottou.data.AlunoDao;
import br.tottou.data.PerfilDao;
import br.tottou.model.EventoAgenda;
import br.tottou.model.entities.Agenda;
import br.tottou.model.entities.Aluno;
import br.tottou.model.entities.Tarefa;

@ManagedBean
@SessionScoped
public class ActionCard {

	private Agenda agenda = new Agenda();
	
	private List<Aluno> listaAluno;
	private List<Tarefa> listaTarefa;
	private ScheduleEvent event = new EventoAgenda(agenda, null, null);
	private ScheduleModel eventModel = new DefaultScheduleModel();
	
	private Set<Agenda> listaAgenda;
	
	Sessao sessao = new Sessao();
	
	
	public ActionCard() {
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
	
	
	
	 public void onEventSelect(ScheduleEntrySelectEvent selectEvent) {	    	
	        setEvent(selectEvent.getScheduleEvent());  
	        EventoAgenda ea= (EventoAgenda) eventModel.getEvent(event.getId());
	        agenda = ea.getObjetoAgenda();
	        listaTarefa=agenda.getTarefas();
	        
	    }  
	 
	 public void irTarefas(ActionEvent ae) {	
		long id_aluno= (long) ae.getComponent().getAttributes().get("id_aluno");
		 if (sessao.getUsuario().getCategoria()==0) {
			 listaAgenda = new HashSet<Agenda>();
			 listaAgenda.addAll(AgendaDao.listEmpresa(sessao.getUsuario().getEmpresa().getId()));
		 }		
		 if (sessao.getUsuario().getCategoria()!=0) {
			 listaAgenda = sessao.getUsuario().getAgenda();
		 }	
			 Agenda ag = new Agenda();
			 Iterator<Agenda> iter = listaAgenda.iterator();			 
			 while (iter.hasNext()) {
				ag=iter.next();
				if (ag.getAluno().getId()!=id_aluno) {
					listaAgenda.remove(ag);
				}
			 }
			
	 }
	
	
	
	public List<Aluno> getListaAluno() {		
		listaAluno = new ArrayList<Aluno>();		
		Sessao sessao = new Sessao();
		if (sessao.getUsuario().getCategoria()<2) {
			listaAluno.addAll(AlunoDao.listEmpresa(sessao.getUsuario().getEmpresa().getId()));
		} else {
			listaAluno.addAll(sessao.getUsuario().getAlunos());
		}
		
				return listaAluno;
	}

	public void setListaAluno(List<Aluno> listaAluno) {
		this.listaAluno = listaAluno;
	}

	public List<Tarefa> getListaTarefa() {
		return listaTarefa;
	}

	public void setListaTarefa(List<Tarefa> listaTarefa) {
		this.listaTarefa = listaTarefa;
	}



	public ScheduleEvent getEvent() {
		return event;
	}



	public void setEvent(ScheduleEvent event) {
		this.event = event;
	}



	public ScheduleModel getEventModel() {
		return eventModel;
	}



	public void setEventModel(ScheduleModel eventModel) {
		this.eventModel = eventModel;
	}



	public Agenda getAgenda() {
		return agenda;
	}



	public void setAgenda(Agenda agenda) {
		this.agenda = agenda;
	}



	public Set<Agenda> getListaAgenda() {
		return listaAgenda;
	}



	public void setListaAgenda(Set<Agenda> listaAgenda) {
		this.listaAgenda = listaAgenda;
	}
	 
	
}
