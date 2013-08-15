package br.tottou.model.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.IndexColumn;


@Entity
@Table(name = "AGENDA")
public class Agenda implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	@Column(name = "AGENDA_ID")
	private long id;

	@Column(name = "NOME")
	private String nome;

	@Column( name = "INICIO")
	@Temporal( TemporalType.TIMESTAMP )
	private Date inicio; 

	@Column(name = "FIM")
	@Temporal( TemporalType.TIMESTAMP )
	private Date fim; 
	
	@Column(name="STATUS")
	private String status; //data de criação
	
	@Column(name = "SESSOES")
	private long sessoes;
	
	@Column(name = "REMAINING")
	private long remaining; //colocar aqui o dado da sessao atual
	
	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "id_sessaoaluno")
	private Aluno aluno;
	
	@ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
	@JoinTable(name="agenda_professores", 
    joinColumns={@JoinColumn(name="agenda_id")}, 
    inverseJoinColumns={@JoinColumn(name="perfil_id")})
	private Set<Perfil> professores;	

	@ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
	@IndexColumn(base = 1, name = "sessaotarefa")
	private List<Tarefa> tarefas;
	
	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@IndexColumn(base=1,name = "id_rel")
	private List<Relatorio> relatorio;
	
	@ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
	@JoinColumn(name = "id_empresat")
	private Empresa empresa;	


	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}


	public static long getSerialversionuid() {
		return serialVersionUID;
	}



	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public long getSessoes() {
		return sessoes;
	}

	public void setSessoes(long sessoes) {
		this.sessoes = sessoes;
	}


	public long getRemaining() {
		return remaining;
	}

	public void setRemaining(long remaining) {
		this.remaining = remaining;
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


	public List<Tarefa> getTarefas() {
		if (tarefas==null) {
			tarefas = new ArrayList<Tarefa>();
		}
		return tarefas;
	}

	public void setTarefas(List<Tarefa> tarefas) {
		this.tarefas = tarefas;
	}

	public Set<Perfil> getProfessores() {
		if(professores==null) {
			professores=new HashSet<Perfil>();
		}
		return professores;
	}

	public void setProfessores(Set<Perfil> professores) {
		this.professores = professores;
	}

	public Date getInicio() {
		return inicio;
	}

	public void setInicio(Date inicio) {
		this.inicio = inicio;
	}

	public Date getFim() {
		return fim;
	}

	public void setFim(Date fim) {
		this.fim = fim;
	}

	public List<Relatorio> getRelatorio() {
		if (relatorio==null) {
			relatorio = new ArrayList<Relatorio>();
		}
		return relatorio;
	}

	public void setRelatorio(List<Relatorio> relatorio) {
		this.relatorio = relatorio;
	}
	
	

}
