package br.tottou.data.action;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.primefaces.event.ScheduleEntrySelectEvent;
import org.primefaces.model.DefaultScheduleModel;
import org.primefaces.model.ScheduleEvent;
import org.primefaces.model.ScheduleModel;

import br.tottou.action.login.Sessao;
import br.tottou.data.AgendaDao;
import br.tottou.data.AlunoDao;
import br.tottou.data.PerfilDao;
import br.tottou.data.TarefaDao;
import br.tottou.engine.util.Cronometro;
import br.tottou.model.EventoAgenda;
import br.tottou.model.entities.Agenda;
import br.tottou.model.entities.Aluno;
import br.tottou.model.entities.ProgPassos;
import br.tottou.model.entities.Relatorio;
import br.tottou.model.entities.Tarefa;

@ManagedBean
@SessionScoped
public class ActionCard {

	private String instanciar;
	private int repeticoes;
	private String observacoes;
	private Integer rating;
	private boolean acertou;
	private int iguais;
	private int tempo;
	private boolean fullscreen = true;

	private Agenda agenda = new Agenda();
	private Agenda agendaHistorico = new Agenda();
	private Agenda agendaAtiva = new Agenda();
	private Tarefa tarefaAtiva = new Tarefa();
	private ProgPassos passoAtivo = new ProgPassos();
	private Aluno aluno = new Aluno();
	private List<Relatorio> listaAlunoRelatorio = new ArrayList<Relatorio>();
	private List<Agenda> listaAgendaAluno = new ArrayList<Agenda>();

	private List<Aluno> listaAluno;
	private List<Tarefa> listaTarefa;
	private ScheduleEvent event = new EventoAgenda(agenda, null, null);
	private ScheduleModel eventModel = new DefaultScheduleModel();

	private List<Agenda> listaAgendaCard;

	Sessao sessao = new Sessao();

	public ActionCard() {
		instanciar();

	}

	private void instanciar() {
		Sessao sessao = new Sessao();
		long userid = sessao.getUsuario().getId();
		long empid = sessao.getUsuario().getEmpresa().getId();
		eventModel = new DefaultScheduleModel();
		List<Agenda> listaAgenda = new ArrayList<Agenda>();
		if (sessao.getUsuario().getCategoria() == 1
				|| sessao.getUsuario().getCategoria() == 0) {
			listaAgenda = AgendaDao.listEmpresa(empid);
		}
		if (sessao.getUsuario().getCategoria() > 1) {
			listaAgenda.addAll(PerfilDao.getPerfil(userid).getAgenda());
		}

		for (int i = 0; i < listaAgenda.size(); i++) {
			eventModel
					.addEvent(new EventoAgenda(listaAgenda.get(i), listaAgenda
							.get(i).getInicio(), listaAgenda.get(i).getFim()));
		}
	}

	public void onEventSelect(ScheduleEntrySelectEvent selectEvent) {
		setEvent(selectEvent.getScheduleEvent());
		EventoAgenda ea = (EventoAgenda) eventModel.getEvent(event.getId());
		agenda = ea.getObjetoAgenda();
		listaTarefa = agenda.getTarefas();

	}

	public void irTarefas(long id_aluno) {
		// long id_aluno= (long)
		// ae.getComponent().getAttributes().get("id_aluno");
		if (sessao.getUsuario().getCategoria() == 0) {
			listaAgendaCard = new ArrayList<Agenda>();
			listaAgendaCard.addAll(AgendaDao.listEmpresa(sessao.getUsuario()
					.getEmpresa().getId()));
		}
		if (sessao.getUsuario().getCategoria() != 0) {
			listaAgendaCard = new ArrayList<Agenda>();
			sessao.atualizaSessao();
			listaAgendaCard.addAll(sessao.getUsuario().getAgenda());
		}
		for (int i = 0; i < listaAgendaCard.size(); i++) {
			if (listaAgendaCard.get(i).getAluno().getId() != id_aluno) {
				listaAgendaCard.remove(i);
			}
		}
	}

	// iniciando tarefas

	List<ProgPassos> listaP = new ArrayList<ProgPassos>();
	List<Relatorio> listaR = new ArrayList<Relatorio>();
	List<Long> ListaT = new ArrayList<Long>();
	Cronometro crono = new Cronometro();
	int contP;
	int maxP;

	public void iniciarTarefa(long id_agenda) {
		limpaAll();
		setFullscreen(true);
		agendaAtiva = AgendaDao.getAgenda(id_agenda);
		agendaAtiva.getTarefas();
		for (int i = 0; i < agendaAtiva.getTarefas().size(); i++) {
			for (int j = 0; j < agendaAtiva.getTarefas().get(i).getSequencia()
					.size(); j++) {
				for (int j2 = 0; j2 < agendaAtiva.getTarefas().get(i)
						.getSequencia().get(j).getPassosTarget().size(); j2++) {
					listaP.add(agendaAtiva.getTarefas().get(i).getSequencia()
							.get(j).getPassosTarget().get(j2));
					ListaT.add(agendaAtiva.getTarefas().get(i).getId());
				}
			}
		}
		maxP = listaP.size();
		setPassoAtivo(listaP.get(contP));
		contP++;
		crono.start();

	}
	
	public void iniciarTarefaSolo(long id_tarefa) {
		limpaAll();
		setFullscreen(true);
		tarefaAtiva = TarefaDao.getTarefa(id_tarefa);
		
		
			for (int j = 0; j < tarefaAtiva.getSequencia().size(); j++) {
				for (int j2 = 0; j2 < tarefaAtiva.getSequencia().get(j).getPassosTarget().size(); j2++) {
					listaP.add(tarefaAtiva.getSequencia().get(j).getPassosTarget().get(j2));
					ListaT.add(tarefaAtiva.getId());
				}
			}
		
		maxP = listaP.size();
		setPassoAtivo(listaP.get(contP));
		contP++;
		crono.start();

	}

	public void proximoPasso() {
		tempo = (int) crono.getSeconds();
		if (getIguais() == 0) { // testar se size eh o ultimo elemento da lista
			listaR.add(gerarRelatorio());
			setPassoAtivo(listaP.get(contP));

		} else {
			listaR.add(gerarRelatorio());
			finalizou();
		}
		contP++;
		tempo = 0;
		rating = 0;
		acertou = false;
		observacoes = "";
		repeticoes = 0;
		crono.start();
	}

	public void repetirPasso() {
		tempo = 0;
		repeticoes++;
		rating = 0;
		acertou = false;
		observacoes = "";
		crono.start();
	}

	private void finalizou() {
		agendaAtiva.setRemaining(agendaAtiva.getRemaining() + 1);
		if (agendaAtiva.getRemaining() == agendaAtiva.getSessoes()) {
			agendaAtiva.setStatus("Finalizado");
		}
		agendaAtiva.getRelatorio().addAll(listaR);
		AgendaDao.atualizarAgenda(agendaAtiva);
		agendaAtiva = new Agenda();
		setPassoAtivo(new ProgPassos());
		instanciar();
		setFullscreen(true);
		// FacesContext context = FacesContext.getCurrentInstance();
		// context.addMessage(null, new FacesMessage("",
		// "Tarefa Concluída com sucesso"));
		//
	}

	private Relatorio gerarRelatorio() {
		Relatorio relatorio = new Relatorio();
		relatorio.setTarefa_id(ListaT.get(contP - 1));
		relatorio.setNome(getPassoAtivo().getNome());
		relatorio.setPassos_id(getPassoAtivo().getId());
		relatorio.setNomePasso(passoAtivo.getNome());
		relatorio.setRepeticoes(getRepeticoes());
		relatorio.setObservacoes(getObservacoes());
		relatorio.setProf_id(sessao.getUsuario().getId());
		relatorio.setProfNome(sessao.getUsuario().getNome());
		relatorio.setScore(getRating());
		relatorio.setSessao(getAgendaAtiva().getRemaining());
		if (isAcertou() == true) {
			relatorio.setSuccess(1);
		} else {
			relatorio.setSuccess(0);
		}
		relatorio.setTempo(formatTime(getTempo()));
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		relatorio.setData((sdf.format(new Date())));
		return relatorio;
	}

	private void limpaAll() {
		contP = 0;
		maxP = 0;
		tempo = 0;
		listaP = new ArrayList<ProgPassos>();
		listaR = new ArrayList<Relatorio>();
		ListaT = new ArrayList<Long>();
		agendaAtiva = new Agenda();
		setRepeticoes(0);
		rating = 0;
		acertou = false;
		observacoes = "";
	}

	public void fs() {
		setFullscreen(false);
	}

	// historico

	public void gerarAlunoRelatorio(long aluno_id) {
		setListaAgendaAluno(AgendaDao.listAluno(aluno_id));
		setAluno(AlunoDao.getAluno(aluno_id));
		// futricar aqui for teh new shittz

	}

	public void gerarTarefaRelatorio(long id_agenda) {
		setAgendaHistorico(AgendaDao.getAgenda(id_agenda));
	}

	public void stop() {
		crono.stop();
	}

	private String formatTime(long time) {
		long seconds = time % 60;
		long minutes = (time - seconds) / 60;
		String str = String.format("%d:%02d", minutes, seconds);
		return str;
	}

	// get n setterz

	public List<Aluno> getListaAluno() {
		listaAluno = new ArrayList<Aluno>();
		Sessao sessao = new Sessao();
		if (sessao.getUsuario().getCategoria() < 2) {
			listaAluno.addAll(AlunoDao.listEmpresa(sessao.getUsuario()
					.getEmpresa().getId()));
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

	public List<Agenda> getListaAgendaCard() {
		return listaAgendaCard;
	}

	public void setListaAgendaCard(List<Agenda> listaAgendaCard) {
		this.listaAgendaCard = listaAgendaCard;
	}

	public String getInstanciar() {
		instanciar();
		return instanciar;
	}

	public void setInstanciar(String instanciar) {
		this.instanciar = instanciar;
	}

	public Agenda getAgendaAtiva() {
		return agendaAtiva;
	}

	public void setAgendaAtiva(Agenda agendaAtiva) {
		this.agendaAtiva = agendaAtiva;
	}

	public ProgPassos getPassoAtivo() {
		return passoAtivo;
	}

	public void setPassoAtivo(ProgPassos passoAtivo) {
		this.passoAtivo = passoAtivo;
	}

	public int getRepeticoes() {
		return repeticoes;
	}

	public void setRepeticoes(int repeticoes) {
		this.repeticoes = repeticoes;
	}

	public String getObservacoes() {
		return observacoes;
	}

	public void setObservacoes(String observacoes) {
		this.observacoes = observacoes;
	}

	public Integer getRating() {
		return rating;
	}

	public void setRating(Integer rating) {
		this.rating = rating;
	}

	public boolean isAcertou() {
		return acertou;
	}

	public void setAcertou(boolean acertou) {
		this.acertou = acertou;
	}

	public int getTempo() {
		return tempo;
	}

	public void setTempo(int tempo) {
		this.tempo = tempo;
	}

	public int getMaxP() {
		return maxP;
	}

	public int getContP() {
		return contP;
	}

	public int getIguais() {
		if (getMaxP() == getContP()) {
			iguais = 1;
		} else {
			iguais = 0;
		}

		return iguais;
	}

	public void setIguais(int iguais) {
		this.iguais = iguais;
	}

	public Aluno getAluno() {
		return aluno;
	}

	public void setAluno(Aluno aluno) {
		this.aluno = aluno;
	}

	public List<Relatorio> getListaAlunoRelatorio() {
		return listaAlunoRelatorio;
	}

	public void setListaAlunoRelatorio(List<Relatorio> listaAlunoRelatorio) {
		this.listaAlunoRelatorio = listaAlunoRelatorio;
	}

	public List<Agenda> getListaAgendaAluno() {
		return listaAgendaAluno;
	}

	public void setListaAgendaAluno(List<Agenda> listaAgendaAluno) {
		this.listaAgendaAluno = listaAgendaAluno;
	}

	public Agenda getAgendaHistorico() {
		return agendaHistorico;
	}

	public void setAgendaHistorico(Agenda agendaHistorico) {
		this.agendaHistorico = agendaHistorico;
	}

	public boolean isFullscreen() {
		return fullscreen;
	}

	public void setFullscreen(boolean fullscreen) {
		this.fullscreen = fullscreen;
	}

	public Tarefa getTarefaAtiva() {
		return tarefaAtiva;
	}

	public void setTarefaAtiva(Tarefa tarefaAtiva) {
		this.tarefaAtiva = tarefaAtiva;
	}

}
