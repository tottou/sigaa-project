package br.tottou.model.entities;

import java.io.Serializable;
import java.util.ArrayList;
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
@Table (name="SEQUENCIA")
public class ProgSequencia implements Serializable, Tipo {
	
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
	
	
	@Column(name="DESCRICAO", length=999)
	private String descricao;
	
		
	@ManyToMany(cascade = CascadeType.MERGE, fetch=FetchType.EAGER )	
	@IndexColumn(base = 1, name = "seqpasso")
	private List<ProgPassos> passosTarget;
	
	


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


	public List<ProgPassos> getPassosTarget() {
		if (passosTarget==null){
			passosTarget=new ArrayList<ProgPassos>();
		}
		return passosTarget;
	}


	public void setPassosTarget(List<ProgPassos> passosTarget) {
		this.passosTarget = passosTarget;
	}

	
	public int getTipo() {		
		return 2;
	}





	

}
