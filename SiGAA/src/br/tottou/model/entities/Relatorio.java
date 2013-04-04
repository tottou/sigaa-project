package br.tottou.model.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "RELATORIO")
public class Relatorio implements Serializable {

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

	@Column(name = "SUCESS")
	private boolean sucess;	

	@Column(name = "STATUS")
	private String status; // data de criação

	@Column(name = "TEMPO")
	private long tempo;

	@Column(name = "OBSERVACOES")
	private String observacoes;

	@Column(name = "SESSAO")
	private long sessao;

	@Column(name = "PASSOS_ID")
	private long passos_id;

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


	public String getObservacoes() {
		return observacoes;
	}

	public void setObservacoes(String observacoes) {
		this.observacoes = observacoes;
	}

	public long getSessao() {
		return sessao;
	}

	public void setSessao(long sessao) {
		this.sessao = sessao;
	}

	public long getPassos_id() {
		return passos_id;
	}

	public void setPassos_id(long passos_id) {
		this.passos_id = passos_id;
	}

	public boolean isSucess() {
		return sucess;
	}

	public void setSucess(boolean sucess) {
		this.sucess = sucess;
	}

	public long getTempo() {
		return tempo;
	}

	public void setTempo(long tempo) {
		this.tempo = tempo;
	}

}
