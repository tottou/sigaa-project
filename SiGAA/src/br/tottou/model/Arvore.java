package br.tottou.model;


import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;

public class Arvore extends DefaultTreeNode {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String label;
	private Object objeto;
	
	public Arvore(String label, Object data, TreeNode parent ) {		
		
			super(label, parent);
		objeto	= data;
		

	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public Object getObjeto() {
		return objeto;
	}

	public void setObjeto(Object objeto) {
		this.objeto = objeto;
	}

}
