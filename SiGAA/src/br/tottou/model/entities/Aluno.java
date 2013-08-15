package br.tottou.model.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
@Table(name = "ALUNO")
public class Aluno implements Serializable {

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

	@Column(name = "NOMERESPONSAVEL")
	private String nomeResponsavel;

	@Column(name = "EMAIL")
	private String email;

	@Column(name = "TELEFONE")
	private String telefone;

	@Column(name = "CELULAR")
	private String celular;

	@Column(name = "ENDERECO", length = 200)
	private String endereco;

	@Column(name = "NASCIMENTO")
	private String nascimento;
	
	@Column( name = "INICIO")
	@Temporal( TemporalType.TIMESTAMP )
	private Date nascimentoDate;
	
	@Column(name = "DIAGNOSTICO", length = 9000)
	private String diagnostico;

	@Column(name = "OBSERVACOES", length = 9000)
	private String observacoes;

	@Column(name = "FOTO")
	private String foto;

	@Column(name = "DATA")
	private String data; // data de criação

	@ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
	@JoinColumn(name = "id_empresaa")
	private Empresa empresa;
	
	@ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
	@JoinTable(name="alunos_professores", 
    joinColumns={@JoinColumn(name="aluno_id")}, 
    inverseJoinColumns={@JoinColumn(name="perfil_id")})	
	@IndexColumn(base = 1, name = "profs")
	private List<Perfil> professores;

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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public String getCelular() {
		return celular;
	}

	public void setCelular(String celular) {
		this.celular = celular;
	}

	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public String getNomeResponsavel() {
		return nomeResponsavel;
	}

	public void setNomeResponsavel(String nomeResponsavel) {
		this.nomeResponsavel = nomeResponsavel;
	}

	public String getNascimento() {
		return nascimento;
	}

	public void setNascimento(String nascimento) {
		this.nascimento = nascimento;
	}

	public String getDiagnostico() {
		return diagnostico;
	}

	public void setDiagnostico(String diagnostico) {
		this.diagnostico = diagnostico;
	}

	public String getObservacoes() {
		return observacoes;
	}

	public void setObservacoes(String observacoes) {
		this.observacoes = observacoes;
	}

	public String getFoto() {
		return foto;
	}

	public void setFoto(String foto) {
		this.foto = foto;
	}

	public Empresa getEmpresa() {
		return empresa;
	}

	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}

	public List<Perfil> getProfessores() {
		if (professores == null) {
			professores = new ArrayList<Perfil>();
		}
		return professores;
	}

	public void setProfessores(List<Perfil> professores) {
		this.professores = professores;
	}

	public Date getNascimentoDate() {
		return nascimentoDate;
	}

	public void setNascimentoDate(Date nascimentoDate) {
		this.nascimentoDate = nascimentoDate;
	}

}
