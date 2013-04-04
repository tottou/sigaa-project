package br.tottou.model.entities;

import java.io.Serializable;


import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table (name="ARQUIVOS")
public class Arquivos implements Serializable {

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
	
	@Column(name="URL")
	private String url;
	
	@Column(name="AUTOSTART")
	private String autoStart;
	
	@Column(name="TIPO")
	private int tipo; //0 = txt, 1 = img, 2 = audio, 3 = video, 4 = arq. geral, 5 = flash
	
	@ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
	@JoinColumn(name = "id_empresaarq")
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

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public int getTipo() {
		return tipo;
	}

	public void setTipo(int tipo) {
		this.tipo = tipo;
	}

	public Empresa getEmpresa() {
		return empresa;
	}

	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}

	public String getAutoStart() {
		return autoStart;
	}

	public void setAutoStart(String autoStart) {
		this.autoStart = autoStart;
	}

		
	
}
