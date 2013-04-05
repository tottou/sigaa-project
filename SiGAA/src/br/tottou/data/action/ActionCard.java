package br.tottou.data.action;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;


import br.tottou.model.entities.Aluno;

@ManagedBean
@SessionScoped
public class ActionCard {

	private List<Aluno> listaAluno;

	public List<Aluno> getListaAluno() {
		
		if (listaAluno==null) {
			listaAluno = new ArrayList<Aluno>();
		}
		return listaAluno;
	}

	public void setListaAluno(List<Aluno> listaAluno) {
		this.listaAluno = listaAluno;
	}
	
	
}
