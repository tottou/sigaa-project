package br.tottou.model.entities;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.hibernate.annotations.IndexColumn;

import br.tottou.interfaces.Tipo;

@Entity
@Table(name = "PASSOS")
public class ProgPassos implements Serializable, Tipo {

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

	@Column(name = "DESCRICAO", length = 999)
	private String descricao;

	@Column(name = "INICIO")
	private String inicio;

	@Column(name = "FIM")
	private String fim;

	@ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)	
	@IndexColumn(base = 1, name = "passosarq")
	private List<Arquivos> arquivos;

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

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getInicio() {
		return inicio;
	}

	public void setInicio(String inicio) {
		this.inicio = inicio;
	}

	public String getFim() {
		return fim;
	}

	public void setFim(String fim) {
		this.fim = fim;
	}

	public List<Arquivos> getArquivos() {
		return arquivos;
	}

	public void setArquivos(List<Arquivos> arquivos) {
		this.arquivos = arquivos;
	}

	@Override
	public int getTipo() {		
		return 3;
	}


}
