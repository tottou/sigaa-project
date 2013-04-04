package br.tottou.data.action;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.ServletContext;


import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DualListModel;

import br.tottou.action.login.Sessao;
import br.tottou.data.ArquivosDao;
import br.tottou.data.CategoriaDao;
import br.tottou.data.PassosDao;
import br.tottou.data.ProgramaDao;
import br.tottou.data.SequenciaDao;
import br.tottou.engine.tree.GeraTree;
import br.tottou.engine.upload.MateriaisUpload;
import br.tottou.engine.util.Tratamento;
import br.tottou.model.Arvore;
import br.tottou.model.entities.Arquivos;
import br.tottou.model.entities.Categoria;
import br.tottou.model.entities.ProgPassos;
import br.tottou.model.entities.ProgSequencia;
import br.tottou.model.entities.Programa;

@ManagedBean
@SessionScoped
public class ActionPrograma {

	ServletContext servletContext = (ServletContext) FacesContext
			.getCurrentInstance().getExternalContext().getContext();
	final String caminho = servletContext.getRealPath("") + File.separator;

	private String msg;
	private String limpaBean;
	private String instanciarS;
	private String atualizaTree;
	private String nulificar;
	private String editarProg;
	private String target = "programas/nome.xhtml";
	private int selecaoMaterial = 0;
	private long arquivoId;
	private boolean autoStart;

	Programa programa = new Programa();
	Categoria categoria = new Categoria();
	Categoria categoriaPai = new Categoria();
	Arquivos arquivos = new Arquivos();
	ProgPassos passos = new ProgPassos();
	ProgSequencia sequencia = new ProgSequencia();
	
	private List<Categoria> listaCatEmp;
	private List<Categoria> listaCategoria;
	private List<Programa> listaPrograma;
	private List<Arquivos> listaArquivos;
	private Arvore root;
	private Arvore noSelecionado;
	private Arvore prog;
	private Arvore noProg;
	private Arvore treePrograma;
	private Arvore treeProgramaSelect;
	private DualListModel<ProgPassos> seqPas; 
	private List<String> listaImagens;
	private List<Arquivos> listaIdArquivos;
	
	Sessao sessao = new Sessao();
	
	

	public Sessao getSessao() {
		return sessao;
	}

	public void setSessao(Sessao sessao) {
		this.sessao = sessao;
	}

	public ActionPrograma() {
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

			getSessao().getUsuario().getEmpresa().setId(sessao.getUsuario().getEmpresa().getId());
		}

		if (getSessao().getUsuario().getEmpresa().getId() == 0) {
			setMsg("Não é possível concluir o cadastro sem selecionar uma empresa.");

		} else {

			try {
				categoria.setEmpresa(getSessao().getUsuario().getEmpresa());
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
					"Nome de categoria já cadastrado."));

		} else {

			salvar();

			context.addMessage(null, new FacesMessage("Cadastro de categoria",
					getMsg()));
		}
	}
	
//deprecated
//	public Categoria procuraTree(String nome) {
//		getListaCategoria();
//		for (int i = 0; i < listaCategoria.size(); i++) {
//			if (listaCategoria.get(i).getNome().equals(nome)) {
//				return listaCategoria.get(i);
//			}
//		}
//		return null;
//	}

	private Programa procuraProg(String nome) {		
		if (!categoria.getProgramas().isEmpty()) {

			Iterator<Programa> iter = categoria.getProgramas().iterator();
			while (iter.hasNext()) {
				programa = iter.next();
				if (programa.getNome().equals(nome)) {					
					return programa;
				}
			}
		}
		return null;
	}

	private boolean checkNome(String nome) {
		getListaCategoria();
		for (int i = 0; i < listaCategoria.size(); i++) {
			if (listaCategoria.get(i).getNome().equals(nome)) {
				return true;
			}
		}
		return false;
	}
	
	public void popularBoolean (){
		if (isAutoStart()==true) {
			arquivos.setAutoStart("true");
		} else {
			arquivos.setAutoStart("false");
		}
		
	}

	public void popular() {
		categoria = (Categoria) noSelecionado.getObjeto();
		geraLista();
		programa = new Programa();
		setTarget("programas/nome.xhtml");

	}

	public void popularProg() {
	
		setPrograma((Programa) noProg.getObjeto());				
	//	categoria = procuraTree(noSelecionado.getData().toString());
		setTarget("programas/nome.xhtml");

	}
	
	public void popularPassos() {
		 if (getArquivoId()==0) {
			 arquivos= new Arquivos();
			 arquivos.setUrl("Sem arquivo");
		 } else {
			 arquivos=ArquivosDao.getArquivos(getArquivoId());
			 
		 }
		
	 }
	
	public void outro () {
		for (int i = 0; i < getListaIdArquivos().size(); i++) {
			if (getListaIdArquivos().get(i).getId()==arquivos.getId()) {
				FacesContext context = FacesContext.getCurrentInstance();
				context.addMessage(null, new FacesMessage("Operação não pode ser feita",
						"Arquivo já adicionado no passo."));
				return;
			}
		}
		popularBoolean();
		setAutoStart(false);
		getListaIdArquivos().add(arquivos);
		arquivos= new Arquivos();
		
	}

		
	

	public void atualizarCat() {
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

	public void salvarPrograma(ActionEvent ae) {

		if (programa.getNome() == null) {
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_WARN,
							"Programas de ensino",
							"Por favor, atualize o nome do programa antes dos outros módulos."));
		} else {
			
			//categoria.addPrograma(programa);
			//CategoriaDao.atualizarCategoria(categoria);
			
			ProgramaDao.atualizarPrograma(programa);
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage("Programas de ensino",
					"Atualização dos dados finalizada com êxito."));
		}

	}

	public void salvarProgramaPassos(ActionEvent ae) {

		if (programa.getNome() == null) {
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_WARN,
							"Programas de ensino",
							"Por favor, atualize o nome do programa antes dos outros módulos."));
		} else {
			programa = ProgramaDao.getPrograma(programa.getId());
			getPassos().setArquivos(getListaIdArquivos());
			getPrograma().getPassos().add(passos);
			ProgramaDao.atualizarPrograma(programa);
			programa = ProgramaDao.getPrograma(programa.getId());
//			categoria.addPrograma(programa);
//			CategoriaDao.atualizarCategoria(categoria);
			setListaIdArquivos(new ArrayList<Arquivos>());
			passos = new ProgPassos();
			arquivos = new Arquivos();
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage("Programas de ensino",
					"Atualização dos dados finalizada com êxito."));
		}

	}
	
	public void salvarProgramaSequencia(ActionEvent ae) {

		if (programa.getNome() == null) {
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_WARN,
							"Programas de ensino",
							"Por favor, atualize o nome do programa antes dos outros módulos."));
		} else {
			
			
			getSequencia().setPassosTarget(getSeqPas().getTarget());			
			getPrograma().getSequencia().add(getSequencia());			
			ProgramaDao.atualizarPrograma(programa);		
			sequencia = new ProgSequencia();
			pickSequencia();
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage("Programas de ensino",
					"Atualização dos dados finalizada com êxito."));
		}

	}

	public void salvarProgramaNome(ActionEvent ae) {

		if (checkNomeProg(programa.getNome())) {
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage(
					FacesMessage.SEVERITY_WARN, "Programas de ensino",
					"Nome de programa de ensino já cadastrado."));
		} else {
			categoria.addPrograma(programa);
			CategoriaDao.atualizarCategoria(categoria);
			categoria=CategoriaDao.getCategoria(categoria.getId());
			setPrograma(procuraProg(programa.getNome()));
			//setPrograma((Programa) noProg.getObjeto());				
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage("Programas de ensino",
					"Atualização dos dados finalizada com êxito."));
		}

	}
	public void atualizarProgramaNome(ActionEvent ae) {

		if (checkNomeProg2(programa.getNome())) {
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage(
					FacesMessage.SEVERITY_WARN, "Programas de ensino",
					"Nome de programa de ensino já cadastrado."));
		} else {
			categoria.addPrograma(programa);
			CategoriaDao.atualizarCategoria(categoria);
			categoria=CategoriaDao.getCategoria(categoria.getId());
			setPrograma(procuraProg(programa.getNome()));
			//setPrograma((Programa) noProg.getObjeto());	
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage("Programas de ensino",
					"Atualização dos dados finalizada com êxito."));
		}

	}

	private boolean checkNomeProg(String nome) {
		getListaPrograma();
		for (int i = 0; i < listaPrograma.size(); i++) {
			if (listaPrograma.get(i).getNome().equals(nome)) {
				return true;
			}
		}
		return false;
	}
	
	private boolean checkNomeProg2(String nome) {
		getListaPrograma();
		boolean se = false;
		for (int i = 0; i < listaPrograma.size(); i++) {
			if (listaPrograma.get(i).getNome().equals(nome)) {
				if (se==true) {
					return true;
				}
				se=true;
			}
		}
		return false;
	}

	public boolean seNomeIgualProg(Categoria categoria, String comparador) {
		Iterator<Programa> iter = categoria.getProgramas().iterator();
		while (iter.hasNext()) {
			if (iter.next().getNome().equals(comparador)) {

				return true;
			}
		}

		return false;
	}

	public void geraLista() {
		// setProg(null);
		setProg(new Arvore("Prog",null, null));
		if (categoria.getProgramas() == null) {
			new Arvore(" --- ",null, prog);
		} else {

			Iterator<Programa> it = categoria.getProgramas().iterator();
			while (it.hasNext()) {
				Programa progz = new Programa();
				progz=	it.next();
				new Arvore(progz.getNome(),progz, prog);
			}
		}

	}
	
	public ProgSequencia tratarSequencia(ProgSequencia seq) {
		for (int i = 0; i < seq.getPassosTarget().size(); i++) {
			for (int j = 1; j < seq.getPassosTarget().size()-1; j++) {
				if (seq.getPassosTarget().get(i).equals(seq.getPassosTarget().get(j))){
					seq.getPassosTarget().remove(j);					
				}
				
				
			}
		}
			
		return seq;
	}

	public void modulo() {
		arquivos = new Arquivos();
		passos = new ProgPassos();
		if (getPrograma().getId() == 0L) {
			if (getTreeProgramaSelect().getData().toString()
					.equals("Novo Programa")) {
				setTarget("programas/nome.xhtml");
			}
		}
		if (getTreeProgramaSelect().getObjeto() != null
				&& getTreeProgramaSelect().getParent()!= null) {
			if (getTreeProgramaSelect().getParent().getData()
					.toString().equals("Passos")) {
				setPassos((ProgPassos) getTreeProgramaSelect().getObjeto());
				//setArquivos(getPassos().getArquivos();
				setTarget("programas/mostrapassos.xhtml");
			}
		}

		if (getTreeProgramaSelect().getObjeto() != null
				&& getTreeProgramaSelect().getParent().getParent() != null) {
			if (getTreeProgramaSelect().getParent().getParent().getData()
					.toString().equals("Materiais")) {
				setArquivos((Arquivos) getTreeProgramaSelect().getObjeto());
				setTarget("programas/mostramateriais.xhtml");
			}
			
			if(getTreeProgramaSelect().getObjeto() != null
				&& getTreeProgramaSelect().getParent() != null) {
				if (getTreeProgramaSelect().getParent().getData()
						.toString().equals("Sequência")) {
					setSequencia((ProgSequencia)getTreeProgramaSelect().getObjeto());
					setSequencia(SequenciaDao.getProgSequencia(getSequencia().getId()));
					setSequencia(tratarSequencia(getSequencia()));
					
					setTarget("programas/mostrasequencia.xhtml");						
				}
			}

		} else {
			if (getTreeProgramaSelect().getData().toString()
					.equals("Materiais") && getTreeProgramaSelect().getParent().getData().toString()
					.equals("Prog")) {				
				setTarget("programas/default.xhtml");
			}	

			if (getTreeProgramaSelect().getData().toString()
					.equals("Textos") && getTreeProgramaSelect().getParent().getData().toString()
					.equals("Materiais")
					&& getTreeProgramaSelect().getParent().getParent()
							.getData().toString().equals("Prog")) {
				setSelecaoMaterial(0);
				setTarget("programas/materiais.xhtml");
			}	
			if (getTreeProgramaSelect().getData().toString()
					.equals("Imagens") && getTreeProgramaSelect().getParent().getData().toString()
					.equals("Materiais")
					&& getTreeProgramaSelect().getParent().getParent()
							.getData().toString().equals("Prog")) {
				setSelecaoMaterial(1);
				setTarget("programas/materiais.xhtml");
			}
			if (getTreeProgramaSelect().getData().toString()
					.equals("Áudios") && getTreeProgramaSelect().getParent().getData().toString()
					.equals("Materiais")
					&& getTreeProgramaSelect().getParent().getParent()
							.getData().toString().equals("Prog")) {
				setSelecaoMaterial(2);
				setTarget("programas/materiais.xhtml");
			}
			if (getTreeProgramaSelect().getData().toString()
					.equals("Vídeos") && getTreeProgramaSelect().getParent().getData().toString()
					.equals("Materiais")
					&& getTreeProgramaSelect().getParent().getParent()
							.getData().toString().equals("Prog")) {
				setSelecaoMaterial(3);
				setTarget("programas/materiais.xhtml");
			}
			if (getTreeProgramaSelect().getData().toString()
					.equals("Arquivos") && getTreeProgramaSelect().getParent().getData().toString()
					.equals("Materiais")
					&& getTreeProgramaSelect().getParent().getParent()
							.getData().toString().equals("Prog")) {
				setSelecaoMaterial(4);
				setTarget("programas/materiais.xhtml");
			}

			if (getTreeProgramaSelect().getData().toString()
					.equals(programa.getNome())) {
				setTarget("programas/nome.xhtml");
			}
		
			if (getTreeProgramaSelect().getData().toString()
					.equals("Procedimento Geral")
					&& getTreeProgramaSelect().getParent().getData().toString()
							.equals("Prog")) {
				setTarget("programas/procedimento.xhtml");
			}
			if (getTreeProgramaSelect().getData().toString().equals("Passos")
					&& getTreeProgramaSelect().getParent().getData().toString()
							.equals("Prog")) {
				setTarget("programas/passos.xhtml");
			}
			if (getTreeProgramaSelect().getData().toString()
					.equals("Sequência")
					&& getTreeProgramaSelect().getParent().getData().toString()
							.equals("Prog")) {
				pickSequencia();
				setTarget("programas/sequencia.xhtml");
			}
			if (getTreeProgramaSelect().getData().toString().equals("Prompts")
					&& getTreeProgramaSelect().getParent().getData().toString()
							.equals("Prog")) {
				setTarget("programas/prompts.xhtml");
			}
			if (getTreeProgramaSelect().getData().toString()
					.equals("Planilhas")
					&& getTreeProgramaSelect().getParent().getData().toString()
							.equals("Prog")) {
				setTarget("programas/planilhas.xhtml");
			}
			if (getTreeProgramaSelect().getData().toString()
					.equals("Observações")
					&& getTreeProgramaSelect().getParent().getData().toString()
							.equals("Prog")) {
				setTarget("programas/observacoes.xhtml");
			}
		}

	}
	
	
	public void anular(ActionEvent ae) {
		arquivos.setUrl(null);
	}

	public void anular() {
		arquivos.setUrl(null);
	}

	public void salvarProgramaUpload(ActionEvent ae) {

		if (programa.getNome() == null) {
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_WARN,
							"Programas de ensino",
							"Por favor, atualize o nome do programa antes dos outros módulos."));
		} else {

			if (arquivos.getUrl() == null) {
				FacesContext context = FacesContext.getCurrentInstance();
				context.addMessage(null, new FacesMessage(
						FacesMessage.SEVERITY_WARN, "Programas de ensino",
						"Nenhum arquivo foi enviado."));
			} else {

				if (arquivos.getNome().equals("") || arquivos.getNome() == null) {
					FacesContext context = FacesContext.getCurrentInstance();
					context.addMessage(null, new FacesMessage(
							FacesMessage.SEVERITY_WARN, "Nome inválido",
							"Insira um nome válido ao arquivo de upload."));
				} else {
					programa = ProgramaDao.getPrograma(programa.getId());
					arquivos.setTipo(selecaoMaterial);
					arquivos.setEmpresa(getSessao().getUsuario().getEmpresa());
					getPrograma().getArquivos().add(arquivos);
					ProgramaDao.atualizarPrograma(programa);
					//categoria.addPrograma(programa);
					//CategoriaDao.atualizarCategoria(categoria);					
					programa=ProgramaDao.getPrograma(programa.getId());
					
					arquivos = new Arquivos();
					//programa = ProgramaDao.getPrograma(programa.getId());
					FacesContext context = FacesContext.getCurrentInstance();
					context.addMessage(null, new FacesMessage(
							"Programas de ensino",
							"Atualização dos dados finalizada com êxito."));
				}

			}

		}

	}

	public void uploadImg(FileUploadEvent fe) {
		MateriaisUpload mu = new MateriaisUpload();
		try {
			arquivos.setUrl("/uploads/imagens/"
					+ mu.uploadImg(
							fe,
							caminho + "uploads/imagens/",
							"img" + categoria.getId() + programa.getId()
									+ Math.random() * 1000));
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage("Arquivo enviado", "É necessário salvar para concluir o envio."));

		} catch (IOException e) {
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage(
					FacesMessage.SEVERITY_WARN, "Upload", "Erro ao enviar."));
			e.printStackTrace();
		}

	}

	public void uploadSom(FileUploadEvent fe) {
		MateriaisUpload mu = new MateriaisUpload();
		try {
			arquivos.setUrl("/uploads/sons/"
					+ mu.uploadSom(
							fe,
							caminho + "uploads/sons/",
							"som" + categoria.getId() + programa.getId()
									+ Math.random() * 1000));
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage("Arquivo enviado", "É necessário salvar para concluir o envio."));

		} catch (IOException e) {
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage(
					FacesMessage.SEVERITY_WARN, "Upload", "Erro ao enviar."));
			e.printStackTrace();
		}

	}

	public void uploadVideo(FileUploadEvent fe) {
		MateriaisUpload mu = new MateriaisUpload();
		try {
			arquivos.setUrl("/uploads/videos/"
					+ mu.uploadVideo(fe, caminho + "uploads/videos/",
							"video" + categoria.getId() + programa.getId()
									+ Math.random() * 1000));
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage("Arquivo enviado", "É necessário salvar para concluir o envio."));

		} catch (IOException e) {
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage(
					FacesMessage.SEVERITY_WARN, "Upload", "Erro ao enviar."));
			e.printStackTrace();
		}

	}
	
	//tratarlinkvideo
	
	public void tratarLink(ActionEvent ae) {
		Tratamento t = new Tratamento();
		arquivos.setUrl(t.tratarVideo(arquivos.getUrl()));
	}
	
	public void tratarLink() {
		Tratamento t = new Tratamento();
		arquivos.setUrl(t.tratarVideo(arquivos.getUrl()));
	}

	public void uploadArq(FileUploadEvent fe) {
		MateriaisUpload mu = new MateriaisUpload();
		try {
			arquivos.setUrl("/uploads/arquivos/"
					+ mu.uploadArq(
							fe,
							caminho + "uploads/arquivos/",
							"arq" + categoria.getId() + programa.getId()
									+ Math.random() * 1000));
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage("Arquivo enviado", "É necessário salvar para concluir o envio."));

		} catch (IOException e) {
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage(
					FacesMessage.SEVERITY_WARN, "Upload", "Erro ao enviar."));
			e.printStackTrace();
		}

	}
	
	//picklist
	
	private void pickSequencia() {
		sequencia = new ProgSequencia();		
		 List<ProgPassos> source = new ArrayList<ProgPassos>();  
	        List<ProgPassos> target = new ArrayList<ProgPassos>();
	        programa=ProgramaDao.getPrograma(programa.getId());
	        Iterator<ProgPassos> iter = programa.getPassos().iterator();
			while (iter.hasNext()) {
				source.add(iter.next());
				
			}
			
			setSeqPas( new DualListModel<ProgPassos>(source, target) );
	}
	
	
	public void imgEditor(ActionEvent ae){
		String atributo = ae.getComponent().getAttributes().get( "valor" ).toString();		
		 getPrograma().setProcedimentoGeral(getPrograma().getProcedimentoGeral()+"<img src='/SiGAA/"+atributo+"'/>");
	}

	public void imgEditor2(ActionEvent ae){
		String atributo = ae.getComponent().getAttributes().get( "valor" ).toString();		
		 getPrograma().setPrompts(getPrograma().getPrompts()+"<img src='/SiGAA/"+atributo+"'/>");
	}
	
	//atualizaçoes
	
	public void atualizarMaterial(){
		try {
			ArquivosDao.atualizarArquivos(arquivos);
			programa=ProgramaDao.getPrograma(programa.getId());
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage("Materias", "Atualização realizada com sucesso."));
		}catch (Exception e) {
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage(
					FacesMessage.SEVERITY_WARN, "Erro", "Atualização não concluída."));
		}
		
	}
	
	
	//deletes
	
	public void deletarMaterial(){
		
			String status = ArquivosDao.remove(arquivos);
			if (status.equals("deleted")) {
				arquivos = new Arquivos();
				setTarget("programas/nome.xhtml");
				programa=ProgramaDao.getPrograma(programa.getId());
				FacesContext context = FacesContext.getCurrentInstance();
				context.addMessage(null, new FacesMessage("Materias", "Exclusão realizada com sucesso."));
			} else {
				FacesContext context = FacesContext.getCurrentInstance();
				context.addMessage(null, new FacesMessage(
						FacesMessage.SEVERITY_WARN, "Erro ao excluir", "O material está associado a outros módulos do programa de ensino."));
			}
			
		
			
		
		
		
	}
	
	public void deletarPasso(){
		
			String status =PassosDao.remove(passos);
			if (status.equals("deleted")) {
			passos = new ProgPassos();
			setTarget("programas/nome.xhtml");
			programa=ProgramaDao.getPrograma(programa.getId());
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage("Passos", "Exclusão realizada com sucesso."));
			}else {
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage(
					FacesMessage.SEVERITY_WARN, "Erro ao excluir", "O passo selecionado está associado a outros módulos do programa de ensino."));
			}
		
		
	}
	
	public void deletarSequencia(){
		
			String status =SequenciaDao.remove(sequencia);
			if (status.equals("deleted")) {
			sequencia = new ProgSequencia();
			setTarget("programas/nome.xhtml");
			programa=ProgramaDao.getPrograma(programa.getId());
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage("Sequências", "Exclusão realizada com sucesso."));
			} else {
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage(
					FacesMessage.SEVERITY_WARN, "Erro ao excluir", "Sequência associada a outros módulos do programa de ensino."));
			}
		
	}
	
	public void videoLink() {
		setSelecaoMaterial(5);
		arquivos.setUrl(null);
	}
	
	
	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Programa getPrograma() {
		if(programa==null){
			programa = new Programa();
		}		
		return programa;
	}

	public void setPrograma(Programa programa) {
		this.programa = programa;
	}

	public Categoria getCategoriaPai() {
		if (categoriaPai==null) {
			categoriaPai=new Categoria();
		}
		return categoriaPai;
	}

	public void setCategoriaPai(Categoria categoriaPai) {
		this.categoriaPai = categoriaPai;
	}

	public String getLimpaBean() {
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



	public String getAtualizaTree() {
		GeraTree gt = new GeraTree();
		for (int i = 0; i < getListaCatEmp().size(); i++) {
			if (getListaCatEmp().get(i).getNome().equals("SiGAA")) {
				setRoot(gt.geraTree(CategoriaDao
						.getCategoria(getListaCatEmp().get(i).getId())));
			}

		}
		return atualizaTree;
	}

	public void setAtualizaTree(String atualizaTree) {
		this.atualizaTree = atualizaTree;
	}

	public Arvore getTreePrograma() {
		GeraTree gt = new GeraTree();
		setTreePrograma(gt.arvorePrograma(getPrograma()));
		return treePrograma;
	}

	public void setTreePrograma(Arvore treePrograma) {
		this.treePrograma = treePrograma;
	}

	public Arvore getTreeProgramaSelect() {		
		return treeProgramaSelect;
	}

	public void setTreeProgramaSelect(Arvore treeProgramaSelect) {
		this.treeProgramaSelect = treeProgramaSelect;
	}

	public String getNulificar() {
		programa = new Programa();
		arquivos = new Arquivos();
		setTarget("programas/nome.xhtml");
		programa=ProgramaDao.getPrograma(programa.getId());
		return nulificar;
	}

	public void setNulificar(String nulificar) {
		this.nulificar = nulificar;
	}

	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}

	public List<Programa> getListaPrograma() {
		listaPrograma = ProgramaDao.list();
		return listaPrograma;
	}

	public void setListaPrograma(List<Programa> listaPrograma) {
		this.listaPrograma = listaPrograma;
	}

	public Arquivos getArquivos() {
		if (arquivos==null) {
			arquivos= new Arquivos();
		}
		return arquivos;
	}

	public void setArquivos(Arquivos arquivos) {
		this.arquivos = arquivos;
	}

	public int getSelecaoMaterial() {
		return selecaoMaterial;
	}

	public void setSelecaoMaterial(int selecaoMaterial) {
		this.selecaoMaterial = selecaoMaterial;
	}

	public ProgPassos getPassos() {
		if (passos==null) {
			passos = new ProgPassos();
		}
		return passos;
	}

	public void setPassos(ProgPassos passos) {
		this.passos = passos;
	}

	public ProgSequencia getSequencia() {
		if (sequencia ==null){
			sequencia = new ProgSequencia();
		}
		return sequencia;
	}

	public void setSequencia(ProgSequencia sequencia) {
		this.sequencia = sequencia;
	}

	public List<Arquivos> getListaArquivos() {
		listaArquivos = ArquivosDao.listEmpresa(getSessao().getUsuario().getEmpresa().getId());
		return listaArquivos;
	}

	public void setListaArquivos(List<Arquivos> listaArquivos) {
		this.listaArquivos = listaArquivos;
	}

	public DualListModel<ProgPassos> getSeqPas() {
		return seqPas;
	}

	public void setSeqPas(DualListModel<ProgPassos> seqPas) {
		this.seqPas = seqPas;
	}

	public long getArquivoId() {
		return arquivoId;
	}

	public void setArquivoId(long arquivoId) {
		this.arquivoId = arquivoId;
	}

	public List<String> getListaImagens() {
		
			listaImagens = new ArrayList<String>();			
		
		Iterator<Arquivos> iter = ArquivosDao.listEmpresa(getSessao().getUsuario().getEmpresa().getId()).iterator();
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

	public String getEditarProg() {
	    programa=ProgramaDao.getPrograma(programa.getId());
		return editarProg;
	}

	public void setEditarProg(String editarProg) {
		this.editarProg = editarProg;
	}

	public List<Categoria> getListaCatEmp() {
		listaCatEmp = CategoriaDao.listEmpresa(sessao.getUsuario().getEmpresa()
				.getId());
		return listaCatEmp;
	}

	public void setListaCatEmp(List<Categoria> listaCatEmp) {
		this.listaCatEmp = listaCatEmp;
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

	public Arvore getNoSelecionado() {
		return noSelecionado;
	}

	public void setNoSelecionado(Arvore noSelecionado) {
		this.noSelecionado = noSelecionado;
	}

	public Arvore getProg() {
		return prog;
	}

	public void setProg(Arvore prog) {
		this.prog = prog;
	}

	public Arvore getNoProg() {
		return noProg;
	}

	public void setNoProg(Arvore noProg) {
		this.noProg = noProg;
	}



	public String getInstanciarS() {
		instanciar();
		return instanciarS;
	}

	public void setInstanciarS(String instanciarS) {
		this.instanciarS = instanciarS;
	}

	public List<Arquivos> getListaIdArquivos() {
		if (listaIdArquivos==null) {
			listaIdArquivos= new ArrayList<Arquivos>();
		}
		return listaIdArquivos;
	}

	public void setListaIdArquivos(List<Arquivos> listaIdArquivos) {
		this.listaIdArquivos = listaIdArquivos;
	}

	public boolean isAutoStart() {
		return autoStart;
	}

	public void setAutoStart(boolean autoStart) {
		this.autoStart = autoStart;
	}


	



}
