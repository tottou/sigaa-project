package br.tottou.engine.tree;

import java.util.Iterator;
import java.util.List;


import org.primefaces.model.TreeNode;

import br.tottou.model.Arvore;
import br.tottou.model.entities.Arquivos;
import br.tottou.model.entities.Categoria;
import br.tottou.model.entities.ProgPassos;
import br.tottou.model.entities.ProgSequencia;
import br.tottou.model.entities.Programa;

public class GeraTree {

	
	private Arvore root;



	public Arvore geraTree(Categoria categoria) {
		if (categoria.getId() != 0L) {

			setRoot(new Arvore("Raiz", null,null));

			geraNo(categoria.getNome(),categoria, root);

		}
		return root;
	}

	private void geraNo(String nome, Categoria categoria, TreeNode raiz) {
		Categoria categ = new Categoria();
		TreeNode node = new Arvore(categoria.getNome(), categoria, raiz); 
		if (categoria.getSubCategorias().size() > 0) {
			Iterator<Categoria> it = categoria.getSubCategorias().iterator();
			while (it.hasNext()) {
				categ = it.next();
				geraNo(categ.getNome(),categ, node);
			}
		}
	}

	
	public Arvore arvorePrograma(Programa programa) {
		Arvore treeProg = new Arvore("Prog", null, null);
		if (programa.getId() == 0L) {
			// novo programa

			new Arvore("Novo Programa", null, treeProg);

		} else {

			new Arvore(programa.getNome(), programa, treeProg);

		}
		new Arvore("Procedimento Geral", null, treeProg);
		Arvore mat = new Arvore("Materiais", null, treeProg);
		Arvore img = new Arvore("Imagens", null, mat);
		Arvore video = new Arvore("Vídeos", null, mat);
		Arvore audio = new Arvore("Áudios", null, mat);
		Arvore arquivos = new Arvore("Arquivos", null, mat);
		Arvore textos = new Arvore("Textos", null, mat);
		Arvore passos =new Arvore("Passos", null, treeProg);
		Arvore sequencia=new Arvore("Sequência", null, treeProg);
		new Arvore("Prompts", null, treeProg);
		new Arvore("Planilhas", null, treeProg);
		new Arvore("Observações", null, treeProg);
		if (programa.getArquivos()!=null) {

			Iterator<Arquivos> arqz = programa.getArquivos().iterator();
			while (arqz.hasNext()) {
				Arquivos arquivo = new Arquivos();
				arquivo = arqz.next();
				if (arquivo.getTipo() == 0) {
					new Arvore(arquivo.getNome(), arquivo, textos);
				}
				if (arquivo.getTipo() == 1) {
					new Arvore(arquivo.getNome(), arquivo, img);
				}
				if (arquivo.getTipo() == 2) {
					new Arvore(arquivo.getNome(), arquivo, audio);
				}
				if (arquivo.getTipo() == 3) {
					new Arvore(arquivo.getNome(), arquivo, video);
				}
				if (arquivo.getTipo() == 4) {
					new Arvore(arquivo.getNome(), arquivo, arquivos);
				}
				
				if (arquivo.getTipo() == 5) {
					new Arvore(arquivo.getNome(), arquivo, video);
				}

			}
		}
		if (programa.getPassos()!=null){
			
			Iterator<ProgPassos> it = programa.getPassos().iterator();
			ProgPassos pp = new ProgPassos();
			while(it.hasNext()) {				
				pp=it.next();
				new Arvore(pp.getNome(),pp,passos);
			}
		}
		
	if (programa.getSequencia()!=null){
			
			Iterator<ProgSequencia> it = programa.getSequencia().iterator();
			ProgSequencia ps = new ProgSequencia();
			while(it.hasNext()) {				
				ps=it.next();
				new Arvore(ps.getNome(),ps,sequencia);
			}
		}
		

		return treeProg;

	}
	
	public Arvore arvoreTarefa(List<Categoria> listaCategoria) {
		Arvore treeTarefa = new Arvore("Tarefa", null, null);
		Categoria cat = new Categoria();
		Iterator<Categoria> it = listaCategoria.iterator();
		while (it.hasNext()) {			
			cat=it.next();
			if (cat.getNome().equals("SiGAA")) {
				
			
			Arvore categ =new Arvore(cat.getNome(),cat,treeTarefa);
			recursivo(categ,cat);
						
			}
		}
		
			
		
		return treeTarefa;
	}
	
	private Arvore recursivo(Arvore arv, Categoria categoria) {
		
		if (!categoria.getSubCategorias().isEmpty()) {
			Iterator<Categoria> itsc = categoria.getSubCategorias().iterator();
			Categoria subCat = new Categoria();
			while (itsc.hasNext()) {
				subCat=itsc.next();
				Arvore sub = new Arvore(subCat.getNome(),null,arv);
				recursivo(sub,subCat);
			}
		}
		
		if (!categoria.getProgramas().isEmpty()) {
			Iterator<Programa> itp = categoria.getProgramas().iterator();
			Programa prog = new Programa();			
			while (itp.hasNext()) {
				prog = itp.next();
				new Arvore (prog.getNome(),prog,arv);
//				Arvore progar = new Arvore ("PROG: "+prog.getNome(),prog,arv);
				
//				if (!prog.getSequencia().isEmpty()) {
//					Iterator<ProgSequencia> itseq = prog.getSequencia().iterator();
//					ProgSequencia seq = new ProgSequencia();
//					while (itseq.hasNext()) {
//						seq = itseq.next();
//						new Arvore ("SEQ: "+seq.getNome(),seq,progar);
//					}
//				}
				
			}
		}
		return arv;
	}
	
	public Arvore gerarTreeSeqz(Programa programa) {
		Arvore treeSeq = new Arvore("Seqz",null,null);
		ProgSequencia seq = new ProgSequencia();
		Iterator<ProgSequencia> it = programa.getSequencia().iterator();
		while (it.hasNext()) {
			seq=it.next();
			new Arvore(seq.getNome(),seq,treeSeq);
		}
		return treeSeq;
	}
	
	
	public Arvore gerarTreePassos(ProgSequencia sequencia) {
		Arvore treePassos = new Arvore("Passos", null, null);
		ProgPassos passo = new ProgPassos();
		Iterator<ProgPassos> it = sequencia.getPassosTarget().iterator();
		while (it.hasNext()) {
			passo=it.next();
			new Arvore(passo.getNome(), passo, treePassos);
		}
		
		return treePassos;
		
	}
	
	
	public Arvore getRoot() {
		return root;
	}

	public void setRoot(Arvore root) {
		this.root = root;
	}

}
