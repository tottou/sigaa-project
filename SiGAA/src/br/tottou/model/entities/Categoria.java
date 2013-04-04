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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import br.tottou.interfaces.Tipo;

@Entity
@Table(name = "CATEGORIA")
public class Categoria implements Serializable, Tipo {

	/**
		 * 
		 */
	private static final long serialVersionUID = 1L;

	public Categoria() {
		subCategorias = new HashSet<Categoria>();
	}

	@Id
	@GeneratedValue
	@Column(name = "ID")
	private long id;

	@Column(name = "NOME")
	private String nome;

	@Column(name = "TAGS")
	private String tags;

	@Column(name = "DESCRICAO", length = 999)
	private String descricao;

	@OneToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
	@JoinColumn(name = "id_subcategoria")
	private Set<Categoria> subCategorias;

	@OneToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
	@JoinColumn(name = "id_programas")
	private Set<Programa> programas;
	
	@ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
	@JoinColumn(name = "id_empresac")
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

	public Set<Categoria> getSubCategorias() {
		if (subCategorias==null) {
			subCategorias = new HashSet<Categoria>();
		}
		return subCategorias;
	}

	public void setSubCategorias(Set<Categoria> subCategorias) {
		this.subCategorias = subCategorias;
	}

	public void addSubCategorias(Categoria categoria) {
		this.subCategorias.add(categoria);
	}

	public Set<Programa> getProgramas() {
		if (programas==null) {
			programas = new HashSet<Programa>();
		}
		return programas;
	}

	public void setProgramas(Set<Programa> programas) {
		this.programas = programas;
	}
	
	public void addPrograma(Programa programas) {
		this.programas.add(programas);
	}

	public Empresa getEmpresa() {
		return empresa;
	}

	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}

	@Override
	public int getTipo() {
		return 0;
	}

}
