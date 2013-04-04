package br.tottou.data.action;

import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import br.tottou.action.login.Sessao;
import br.tottou.data.CategoriaDao;
import br.tottou.engine.tree.GeraTree;
import br.tottou.model.Arvore;
import br.tottou.model.entities.Categoria;
import br.tottou.model.entities.Empresa;

@ManagedBean
@SessionScoped
public class ActionCategoria {

	private String msg;
	private String limpaBean;
	private String instanciarS;

	Categoria categoria = new Categoria();
	Categoria categoriaPai = new Categoria();
	private List<Categoria> listaCategoria;
	private List<Categoria> listaCatEmp;
	private Arvore root;
	private Arvore noSelecionado;

	Empresa empresa = new Empresa();
	Sessao sessao = new Sessao();

	public Sessao getSessao() {
		return sessao;
	}

	public void setSessao(Sessao sessao) {
		this.sessao = sessao;
	}

	public Empresa getEmpresa() {
		empresa = getSessao().getUsuario().getEmpresa();
		return empresa;
	}

	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}

	public ActionCategoria() {
		instanciar();

		GeraTree gt = new GeraTree();
		for (int i = 0; i < getListaCatEmp().size(); i++) {
			if (getListaCatEmp().get(i).getNome().equals("SiGAA")) {
				setRoot(gt.geraTree(CategoriaDao.getCategoria(getListaCatEmp()
						.get(i).getId())));
			}

		}

	}
	
	private void instanciar() {
		if ((CategoriaDao.listEmpresa(
				getSessao().getUsuario().getEmpresa().getId()).size() < 1)
				&& (getSessao().getUsuario().getEmpresa().getId() != 0)) {

			categoria.setNome("SiGAA");
			categoria.setDescricao("Categorias do sistema SiGAA");
			categoria.setTags("SiGAA");
			categoria.setEmpresa(getSessao().getUsuario().getEmpresa());
			CategoriaDao.salvarCategoria(categoria);
			categoria = new Categoria();
		}
	}

	public Categoria getCategoria() {
		return categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}

	private void salvar() {
		Sessao sessao = new Sessao();
		if (sessao.getUsuario().getEmpresa().getId() != 0) {

			getEmpresa().setId(sessao.getUsuario().getEmpresa().getId());
		}

		if (getEmpresa().getId() == 0) {
			setMsg("Não é possível concluir o cadastro sem selecionar uma empresa.");

		} else {

			try {
				categoria.setEmpresa(getEmpresa());
				categoriaPai = (Categoria) noSelecionado.getObjeto();
				categoriaPai.addSubCategorias(categoria);
				CategoriaDao.atualizarCategoria(categoriaPai);
				setMsg("Cadastro concluido com sucesso.");
				categoria = new Categoria();

			} catch (Exception e) {

				setMsg("Ocorreu um erro ao cadastrar.\n Selecione a sub-categoria.");

			}
			GeraTree gt = new GeraTree();
			for (int i = 0; i < getListaCatEmp().size(); i++) {
				if (getListaCatEmp().get(i).getNome().equals("SiGAA")) {
					setRoot(gt.geraTree(CategoriaDao
							.getCategoria(getListaCatEmp().get(i).getId())));
				}

			}
		}
	}

	public void save(ActionEvent actionEvent) {

		FacesContext context = FacesContext.getCurrentInstance();
		if (checkNome(categoria.getNome())) {
			context.addMessage(null, new FacesMessage("Cadastro de categoria",
					"Nome de categoria reservado."));

		} else {

			salvar();

			context.addMessage(null, new FacesMessage("Cadastro de categoria",
					getMsg()));
		}
	}

	// deprecated
	// public Categoria procuraTree(String nome) {
	// getListaCategoria();
	// for (int i = 0; i < listaCategoria.size(); i++) {
	// if (listaCategoria.get(i).getNome().equals(nome)) {
	// return listaCategoria.get(i);
	// }
	// }
	// return null;
	// }

	private boolean checkNome(String nome) {
		// deprecated
		// getListaCategoria();
		// for (int i = 0; i < listaCategoria.size(); i++) {
		// if (listaCategoria.get(i).getNome().equals(nome)) {
		// return true;
		// }
		// }
		// return false;

		if (nome.equals("SiGAA")) {
			return true;
		}
		return false;
	}

	public void popular() {
		categoria = (Categoria) noSelecionado.getObjeto();
		System.out.println(categoria.getNome());

	}

	public void atualizarCat() {
		
		if (categoria.getNome().equals("SiGAA")) {
			setMsg("Não é possível editar a categoria raiz SiGAA.");
		} else {
			
		
		try {
			CategoriaDao.atualizarCategoria(categoria);
			setMsg("Edição concluida com sucesso.");
			GeraTree gt = new GeraTree();
			for (int i = 0; i < getListaCatEmp().size(); i++) {
				if (getListaCatEmp().get(i).getNome().equals("SiGAA")) {
					setRoot(gt.geraTree(CategoriaDao
							.getCategoria(getListaCatEmp().get(i).getId())));
				}

			}
			categoria = new Categoria();

		} catch (Exception e) {

			setMsg("Ocorreu um erro ao editar.");

		}
		}
		FacesContext context = FacesContext.getCurrentInstance();
		context.addMessage(null, new FacesMessage("Edição de categoria",
				getMsg()));

	}

	public void deletar(ActionEvent ae) {

		if (categoria.getId() == 1L) {
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage("Edição de categoria",
					"Não é possível excluir a categoria raiz do sistema."));
			return;
		}
		if (categoria.getId() != 0L) {

			if (categoria.getSubCategorias().size() < 1) {
				CategoriaDao.remove(categoria);
				FacesContext context = FacesContext.getCurrentInstance();
				context.addMessage(null, new FacesMessage(
						"Edição de categoria", "Categoria removida."));
				listaCategoria.remove(categoria);
				GeraTree gt = new GeraTree();
				for (int i = 0; i < getListaCatEmp().size(); i++) {
					if (getListaCatEmp().get(i).getNome().equals("SiGAA")) {
						setRoot(gt.geraTree(CategoriaDao
								.getCategoria(getListaCatEmp().get(i).getId())));
					}

				}
				categoria = new Categoria();

			}
			if (categoria.getSubCategorias().size() > 0) {
				FacesContext context = FacesContext.getCurrentInstance();
				context.addMessage(
						null,
						new FacesMessage("Edição de categoria",
								"Categoria selecionada contém sub-categorias. Exclusão não concluida."));
			}

		}

		else {
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage("Edição de categoria",
					"Categoria não selecionada"));
		}

	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getLimpaBean() {
		instanciar();
		categoria = new Categoria();
		return limpaBean;
	}

	public void setLimpaBean(String limpaBean) {
		categoria = new Categoria();
		this.limpaBean = limpaBean;
	}

	public List<Categoria> getListaCategoria() {
		listaCategoria = CategoriaDao.list();
		return listaCategoria;
	}

	public void setListaCategoria(List<Categoria> listaCategoria) {
		this.listaCategoria = listaCategoria;
	}

	public List<Categoria> getListaCatEmp() {
		listaCatEmp = CategoriaDao.listEmpresa(sessao.getUsuario().getEmpresa()
				.getId());
		return listaCatEmp;
	}

	public void setListaCatEmp(List<Categoria> listaCatEmp) {
		this.listaCatEmp = listaCatEmp;
	}

	public Arvore getNoSelecionado() {
		return noSelecionado;
	}

	public void setNoSelecionado(Arvore noSelecionado) {
		this.noSelecionado = noSelecionado;
	}

	public Arvore getRoot() {

		GeraTree gt = new GeraTree();
		for (int i = 0; i < getListaCatEmp().size(); i++) {
			if (getListaCatEmp().get(i).getNome().equals("SiGAA")) {
				setRoot(gt.geraTree(CategoriaDao.getCategoria(getListaCatEmp()
						.get(i).getId())));
			}
		}
		return root;
	}

	public void setRoot(Arvore root) {
		this.root = root;
	}

	public String getInstanciarS() {
		instanciar();
		return instanciarS;
	}

	public void setInstanciarS(String instanciarS) {
		this.instanciarS = instanciarS;
	}



}
