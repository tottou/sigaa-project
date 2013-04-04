package br.tottou.model.entities;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;


import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import br.tottou.interfaces.Tipo;

@Entity
@Table (name="PROGRAMA")
public class Programa implements Serializable, Tipo{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue
	@Column(name="ID")
	private long id;	

	@Column(name="NOME")
	private String nome;
	
	@Column(name="TAGS")
	private String tags;
	
	@Column(name="DESCRICAO", length=999)
	private String descricao;
	
	@Column(name="OBJETIVOS", length=999)
	private String objetivos;
	
	@Column(name="PROCEDIMENTO", length=9999)
	private String procedimentoGeral;	
	
	@OneToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
	@JoinColumn(name = "id_arquivos")
	private Set<Arquivos> arquivos;
	
	@OneToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
	@JoinColumn(name = "id_passos")
	private Set<ProgPassos> passos;
	
	@OneToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
	@JoinColumn(name = "id_sequencia")
	private Set<ProgSequencia> sequencia;
	
	@Column(name="PROMPTS", length=9999)
	private String prompts;
	
	@Column(name="OBSERVACOES", length=999)
	private String observacoes;
		

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

	public String getTags() {
		return tags;
	}

	public void setTags(String tags) {
		this.tags = tags;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getObjetivos() {
		return objetivos;
	}

	public void setObjetivos(String objetivos) {
		this.objetivos = objetivos;
	}

	public String getProcedimentoGeral() {
		return procedimentoGeral;
	}

	public void setProcedimentoGeral(String procedimentoGeral) {
		this.procedimentoGeral = procedimentoGeral;
	}


	public Set<ProgPassos> getPassos() {
		if (passos==null) {
			passos = new HashSet<ProgPassos>();
		}
		return passos;
	}

	public void setPassos(Set<ProgPassos> passos) {
		this.passos = passos;
	}

	public Set<ProgSequencia> getSequencia() {
		if (sequencia==null) {
			sequencia = new HashSet<ProgSequencia>();
		}
		return sequencia;
	}

	public void setSequencia(Set<ProgSequencia> sequencia) {
		this.sequencia = sequencia;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getPrompts() {
		return prompts;
	}

	public void setPrompts(String prompts) {
		this.prompts = prompts;
	}

	public String getObservacoes() {
		return observacoes;
	}

	public void setObservacoes(String observacoes) {
		this.observacoes = observacoes;
	}

	public Set<Arquivos> getArquivos() {
		if (arquivos==null) {
			arquivos = new HashSet<Arquivos>();
		}
		return arquivos;
	}

	public void setArquivos(Set<Arquivos> arquivos) {
		this.arquivos = arquivos;
	}

	@Override
	public int getTipo() {
		
		return 1;
	}

	
	
	
	
	
	
	

}
