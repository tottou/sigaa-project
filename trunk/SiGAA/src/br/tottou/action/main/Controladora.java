package br.tottou.action.main;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.event.ActionEvent;

@ManagedBean
@SessionScoped
public class Controladora {
	

	private String target="default.xhtml";
	private String prog2;
	
	
	public void fotoUploadAluno (ActionEvent e) {
		setTarget("alunofupload.xhtml");
	}
	
	public void fotoUpload (ActionEvent e) {
		setTarget("fotoupload.xhtml");
	}
	
	public void usuario (ActionEvent e) {
		setTarget("cadastrop.xhtml");
	}
	
	public void tarefa (ActionEvent e) {
		setTarget("tarefa.xhtml");
	}
	
	public void criaTarefa (ActionEvent e) {
		setTarget("criartarefa.xhtml");
	}
	
	public void criaTarefa2 (ActionEvent e) {
		setTarget("criartarefa2.xhtml");
	}
	public void empresa (ActionEvent e) {
		setTarget("cadastroemp.xhtml");
	}

	public void aluno (ActionEvent e) {
		setTarget("cadastroal.xhtml");
	}
	
	public void categoria (ActionEvent e) {
		setTarget("cadastrocat.xhtml");
	}
	
	public void agenda (ActionEvent e) {
		setTarget("agenda.xhtml");
	}
	
	public void schedule (ActionEvent e) {
		setTarget("schedule.xhtml");
	}
	public void programa (ActionEvent e) {
		setTarget("programa.xhtml");
	}
	
	public void programa2 () {
		setTarget("programa2.xhtml");
	}
	public void editUsuario (ActionEvent e) {
		setTarget("perfil.xhtml");
	}
	
	public void editAluno (ActionEvent e) {
		setTarget("aluno.xhtml");
	}
	
	public void editEmpresa (ActionEvent e) {
		setTarget("empresa.xhtml");
	}

	public void editCategoria (ActionEvent e) {
		setTarget("categoria.xhtml");
	}
	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}

	public String getProg2() {
		setTarget("programa2.xhtml");
		return prog2;
	}

	public void setProg2(String prog2) {
		setTarget("programa2.xhtml");
		this.prog2 = prog2;
	}
}
