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
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


@Entity
@Table (name="PERFIL")
public class Perfil implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	@Column(name="PERFIL_ID")
	private long id;
	
	@Column(name="LOGIN")
	private String login;
	
	@Column(name="SENHA")
	private String senha;
	
	@Column(name="NOME")
	private String nome;
	
	@Column(name="EMAIL")
	private String email;
	
	@Column(name="TELEFONE")
	private String telefone;
	
	@Column(name="CELULAR")
	private String celular;
	
	@Column(name="ENDERECO", length=200)
	private String endereco;	 
	
	@Column(name="CATEGORIA")
	private long categoria; //categoria: (SU: 0, ADM: 1, PROF: 2, TUT1: 3, TUT2: 4)
	
	@Column(name="FOTO")
	private String foto;
	
	@Column(name="DATA")
	private String data; //data de criação
	
	@ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
	@JoinColumn(name = "id_empresa")
	private Empresa empresa;
	
	@ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
	@JoinTable(name="alunos_professores", 
    joinColumns={@JoinColumn(name="perfil_id")}, 
    inverseJoinColumns={@JoinColumn(name="aluno_id")})
	private Set<Aluno> alunos;
	
	@ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
	@JoinTable(name="agenda_professores", 
    joinColumns={@JoinColumn(name="perfil_id")}, 
    inverseJoinColumns={@JoinColumn(name="agenda_id")})
	private Set<Agenda> agenda;
	
	
	public boolean equals(Object obj){
	     Perfil perfil = (Perfil) obj;     

	     if (id==perfil.id) {
	    	 return true;
	     }
	     return false;
	}
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getLogin() {
		return login;
	}
	public void setLogin(String login) {
		this.login = login;
	}
	public String getSenha() {
		return senha;
	}
	public void setSenha(String senha) {
		this.senha = senha;
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
	public long getCategoria() {
		return categoria;
	}
	public void setCategoria(long categoria) {
		this.categoria = categoria;
	}
	public Set<Aluno> getAlunos() {
		if (alunos==null) {
			alunos = new HashSet<Aluno>();
		}
		return alunos;
	}
	public void setAlunos(Set<Aluno> alunos) {
		this.alunos = alunos;
	}

	public Set<Agenda> getAgenda() {
		return agenda;
	}

	public void setAgenda(Set<Agenda> agenda) {
		this.agenda = agenda;
	}
	
	
	
	
	
}



