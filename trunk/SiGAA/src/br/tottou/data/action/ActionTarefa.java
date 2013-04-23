package br.tottou.data.action;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import br.tottou.action.login.Sessao;
import br.tottou.data.ArquivosDao;
import br.tottou.data.CategoriaDao;
import br.tottou.data.TarefaDao;
import br.tottou.engine.tree.GeraTree;
import br.tottou.interfaces.Tipo;
import br.tottou.model.Arvore;
import br.tottou.model.entities.Arquivos;
import br.tottou.model.entities.ProgPassos;
import br.tottou.model.entities.ProgSequencia;
import br.tottou.model.entities.Programa;
import br.tottou.model.entities.Relatorio;
import br.tottou.model.entities.Tarefa;

@ManagedBean
@SessionScoped
public class ActionTarefa {

	private Arvore tarefaTree;
	private Arvore tarefaTreeSelect;
	private Arvore seqTree;
	private Arvore seqTreeSelect;
	private Arvore passosTree;
	private Arvore passosTreeSelect;
	private String instanciar;
	private Date inicio;
	private Date fim;
	private long tempo;

	GeraTree gt = new GeraTree();
	private ProgPassos passos = new ProgPassos();
	private Tarefa tarefa = new Tarefa();
	private Relatorio relatorio = new Relatorio();
	private Programa programa = new Programa();
	private ProgSequencia sequencia = new ProgSequencia();
	
	private List<ProgSequencia> listaSequencia;
	private List<String> listaImagens;

	public ActionTarefa() {

		Sessao sessao = new Sessao();
		sessao.getUsuario().getEmpresa();
		setTarefaTree(gt.arvoreTarefa(CategoriaDao.listEmpresa(sessao
				.getUsuario().getEmpresa().getId())));
	}
	
	public void selecionarImg (ActionEvent ae) {
		String atributo = ae.getComponent().getAttributes().get( "valor" ).toString();		
		 getTarefa().setImagem(atributo);
	}

	public void popularProg() {
		// tipo => 0=cat, 1=prog, 2=seq, 3=passos
		int tipo;
		if ( (getTarefaTreeSelect().getObjeto()==null) ) {
			tipo=0;
		} else {
			tipo = ((Tipo) getTarefaTreeSelect().getObjeto()).getTipo();
		}
				
		if (tipo == 2) {
			setPassosTree(gt
					.gerarTreePassos((ProgSequencia) getTarefaTreeSelect()
							.getObjeto()));
			
		}

		if (tipo == 1) {
			setPrograma((Programa) getTarefaTreeSelect().getObjeto());
			setSeqTree(gt.gerarTreeSeqz((Programa) getTarefaTreeSelect()
					.getObjeto()));
			
		}
		
		if (tipo==0) {
			setSeqTree(new Arvore(null, null, null));
			setPassosTree(new Arvore(null, null, null));
		}
	}
	
	public void popularSeq() {
		// tipo => 0=cat, 1=prog, 2=seq, 3=passos
		int tipo = ((Tipo) getSeqTreeSelect().getObjeto()).getTipo();	
		if (tipo == 2) {
			setPassosTree(gt
					.gerarTreePassos((ProgSequencia) getSeqTreeSelect()
							.getObjeto()));
			setSequencia((ProgSequencia) getSeqTreeSelect().getObjeto());
		}

		if (tipo == 1) {
			setPrograma((Programa) getTarefaTreeSelect().getObjeto());
			setSeqTree(gt.gerarTreeSeqz((Programa) getTarefaTreeSelect()
					.getObjeto()));
		}
	}

	public void popularPas() {
		setPassos((ProgPassos) getPassosTreeSelect().getObjeto());
		// FacesContext context = FacesContext.getCurrentInstance();
		// context.addMessage(null, new FacesMessage("Janela Pop-Up",
		// "Favor desbloquear Pop-ups em seu navegador."));
	}

	public void addSeq() {
		getListaSequencia().add(getSequencia());
		 FacesContext context = FacesContext.getCurrentInstance();
			 context.addMessage(null, new FacesMessage(getSequencia().getNome(),
			 "Sequencia selecionada"));
	}
	
	public void removeSeq(int num) {
		getListaSequencia().remove(num);
	}
	
	
	public void salvarTarefa() {
		
		FacesContext context = FacesContext.getCurrentInstance();	
		getTarefa().setSequencia(getListaSequencia());
		Sessao sessao = new Sessao();
		getTarefa().setEmpresa(sessao.getUsuario().getEmpresa());		
		if (getTarefa().getImagem()==null ) {
			getTarefa().setImagem("/imagens/tarefa.jpg");
		}
		if (getTarefa().getNome().equals("")  ) {
			context.addMessage(null, new FacesMessage("Ocorreu um erro.",
					 "Insira um nome para a nova Tarefa."));
		}else {	
		
		
		try {
			TarefaDao.salvarTarefa(getTarefa());
			 context.addMessage(null, new FacesMessage(getTarefa().getNome(),
					 "Tarefa criada com sucesso."));
			 setTarefa(new Tarefa());
			 getListaSequencia().clear();
			 setSeqTree(new Arvore(null, null, null));
				setPassosTree(new Arvore(null, null, null));
				passos= new ProgPassos();
				sequencia = new ProgSequencia();
			 
		} catch (Exception e) {
			System.out.println(e);
			context.addMessage(null, new FacesMessage("Ocorreu um erro.",
					 "Tente de novo ou entre em contato com a administração."));
		 }
		}
		
		
	}

//	private void persiste() {
//		long result = getTarefa().getSessoes() - getTarefa().getRemaining();
//		getRelatorio().setSessao(result);
//		getRelatorio().setNome(
//				"Relatório da sessão " + result + "- Passo: "
//						+ getPassos().getNome());
//		getRelatorio().setTempo(getTempo());
//		getRelatorio().setPassos_id(getPassos().getId());
//		getTarefa().getRelatorio().add(getRelatorio());
//		getTarefa().setRemaining(getTarefa().getRemaining() - 1);

//	}

	public void concluido() {
//		getRelatorio().setSucess(false);
//		persiste();

	}

	public void acertouConcluido() {
//		getRelatorio().setSucess(true);
//		persiste();
	}

	public Arvore getTarefaTree() {
		if (tarefaTree == null) {
			tarefaTree = new Arvore(null, null, null);
		}
		return tarefaTree;
	}

	public void setTarefaTree(Arvore tarefaTree) {
		this.tarefaTree = tarefaTree;
	}

	public Arvore getTarefaTreeSelect() {
		if (tarefaTreeSelect == null) {
			tarefaTreeSelect = new Arvore(null, null, null);
		}
		return tarefaTreeSelect;
	}

	public void setTarefaTreeSelect(Arvore tarefaTreeSelect) {
		this.tarefaTreeSelect = tarefaTreeSelect;
	}

	public Arvore getPassosTree() {
		if (passosTree == null) {
			passosTree = new Arvore(null, null, null);
		}
		return passosTree;
	}

	public void setPassosTree(Arvore passosTree) {
		this.passosTree = passosTree;
	}

	public Arvore getPassosTreeSelect() {
		if (passosTreeSelect == null) {
			passosTreeSelect = new Arvore(null, null, null);
		}
		return passosTreeSelect;
	}

	public void setPassosTreeSelect(Arvore passosTreeSelect) {
		this.passosTreeSelect = passosTreeSelect;
	}

	public ProgPassos getPassos() {
		return passos;
	}

	public void setPassos(ProgPassos passos) {
		this.passos = passos;
	}

	public String getInstanciar() {

		Sessao sessao = new Sessao();
		sessao.getUsuario().getEmpresa();
		setTarefaTree(gt.arvoreTarefa(CategoriaDao.listEmpresa(sessao
				.getUsuario().getEmpresa().getId())));

		return instanciar;
	}

	public void setInstanciar(String instanciar) {
		this.instanciar = instanciar;
	}

	public Tarefa getTarefa() {
		return tarefa;
	}

	public void setTarefa(Tarefa tarefa) {
		this.tarefa = tarefa;
	}

	public Relatorio getRelatorio() {
		return relatorio;
	}

	public void setRelatorio(Relatorio relatorio) {
		this.relatorio = relatorio;
	}

	public long getTempo() {
		return tempo;
	}

	public void setTempo(long tempo) {
		this.tempo = tempo;
	}

	public Programa getPrograma() {
		return programa;
	}

	public void setPrograma(Programa programa) {
		this.programa = programa;
	}

	public Date getInicio() {
		return inicio;
	}

	public void setInicio(Date inicio) {
		this.inicio = inicio;
	}

	public Date getFim() {
		return fim;
	}

	public void setFim(Date fim) {
		this.fim = fim;
	}

	public Arvore getSeqTree() {
		if (seqTree==null) {
			seqTree=new Arvore(null,null,null);
		}
		return seqTree;
	}

	public void setSeqTree(Arvore seqTree) {
		this.seqTree = seqTree;
	}

	public Arvore getSeqTreeSelect() {
		if (seqTreeSelect==null) {
			seqTreeSelect=new Arvore(null,null,null);
		}
		return seqTreeSelect;
	}

	public void setSeqTreeSelect(Arvore seqTreeSelect) {
		this.seqTreeSelect = seqTreeSelect;
	}

	public ProgSequencia getSequencia() {
		return sequencia;
	}

	public void setSequencia(ProgSequencia sequencia) {
		this.sequencia = sequencia;
	}

	public List<ProgSequencia> getListaSequencia() {
		if (listaSequencia==null) {
			listaSequencia = new ArrayList<ProgSequencia>();
		}
		return listaSequencia;
	}

	public void setListaSequencia(List<ProgSequencia> listaSequencia) {
		this.listaSequencia = listaSequencia;
	}

	public List<String> getListaImagens() {
	listaImagens = new ArrayList<String>();			
		Sessao sessao = new Sessao();
		Iterator<Arquivos> iter = ArquivosDao.listEmpresa(sessao.getUsuario().getEmpresa().getId()).iterator();
		Arquivos arquivo;
		while (iter.hasNext()) {
			arquivo = iter.next();
			if (arquivo.getTipo()==1) {
				listaImagens.add(arquivo.getUrl());
			}
		}
		return listaImagens;
	}

	public void setListaImagens(List<String> listaImagens) {
		this.listaImagens = listaImagens;
	}

}
