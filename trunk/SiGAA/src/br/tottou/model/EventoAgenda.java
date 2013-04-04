package br.tottou.model;

import java.util.Date;

import org.primefaces.model.DefaultScheduleEvent;

import br.tottou.model.entities.Agenda;

public class EventoAgenda extends DefaultScheduleEvent{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5448416532767620584L;
	
	private Agenda objetoAgenda;

	
	public EventoAgenda(Agenda agenda, Date start, Date end) {
		super(agenda.getNome(),start,end);
		
		objetoAgenda=agenda;
		
	}


	public Agenda getObjetoAgenda() {
		return objetoAgenda;
	}


	public void setObjetoAgenda(Agenda objetoAgenda) {
		this.objetoAgenda = objetoAgenda;
	}
}
