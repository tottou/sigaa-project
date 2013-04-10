package br.tottou.data.action;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;


import br.tottou.action.login.Sessao;
import br.tottou.data.AlunoDao;
import br.tottou.model.entities.Aluno;

@ManagedBean
@SessionScoped
public class ActionCard {

	private List<Aluno> listaAluno;

	public List<Aluno> getListaAluno() {		
		listaAluno = new ArrayList<Aluno>();		
		Sessao sessao = new Sessao();
		if (sessao.getUsuario().getCategoria()<2) {
			listaAluno.addAll(AlunoDao.listEmpresa(sessao.getUsuario().getEmpresa().getId()));
		} else {
			listaAluno.addAll(sessao.getUsuario().getAlunos());
		}
		
				return listaAluno;
	}

	public void setListaAluno(List<Aluno> listaAluno) {
		this.listaAluno = listaAluno;
	}
	
	
}
