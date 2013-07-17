package br.tottou.model.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.IndexColumn;

@Entity
@Table(name = "TAREFA")
public class Tarefa implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	@Column(name = "ID")
	private long id;

	@Column(name = "NOME")
	private String nome;
	
	@Column(name="STATUS")
	private String status; 
	
	@Column(name="IMAGEM")
	private String imagem;
	
	@Column(name = "SESSOES")
	private long sessoes;
	
	@Column(name = "REMAINING")
	private long remaining;
	
	@ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
	@JoinColumn(name = "id_alunoT")
	private Set<Aluno> alunos;	

	@ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
	@IndexColumn(base = 1, name = "tarefaseq")
	private List<ProgSequencia> sequencia;
	
	@OneToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
	@IndexColumn(base=1,name = "id_relat")
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

	public List<ProgSequencia> getSequencia() {
		return sequencia;
	}

	public void setSequencia(List<ProgSequencia> sequencia) {
		this.sequencia = sequencia;
	}

	public Set<Aluno> getAlunos() {
		return alunos;
	}

	public void setAlunos(Set<Aluno> alunos) {
		this.alunos = alunos;
	}

	public String getImagem() {
		return imagem;
	}

	public void setImagem(String imagem) {
		this.imagem = imagem;
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
