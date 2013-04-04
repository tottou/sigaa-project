package br.tottou.data;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import br.tottou.model.entities.Arquivos;

public class ArquivosDao {

	public static void salvarArquivos(Arquivos arquivos) {

		Session sessao = HibernateUtil.getSessionFactory().openSession();
		Transaction t = sessao.beginTransaction();
		sessao.save(arquivos);
		t.commit();
		sessao.close();
	}

	public static void atualizarArquivos(Arquivos arquivos) {

		Session sessao = HibernateUtil.getSessionFactory().openSession();
		Transaction t = sessao.beginTransaction();
		sessao.merge(arquivos);
		t.commit();
		sessao.close();
	}

	public static Arquivos getArquivos(long id) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Arquivos arquivo = (Arquivos) session.get(Arquivos.class, id);
		session.close();
		return arquivo;
	}

	@SuppressWarnings("unchecked")
	public static List<Arquivos> list() {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction t = session.beginTransaction();
		List<Arquivos> lista = session.createQuery("from Arquivos").list();
		t.commit();
		session.close();
		return lista;

	}
	
	@SuppressWarnings("unchecked")
	public static List<Arquivos> listEmpresa(long idEmpresa) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction t = session.beginTransaction();
		List<Arquivos> lista = session.createQuery("from Arquivos where id_empresaarq ="+idEmpresa).list();
		t.commit();
		session.close();
		return lista;

	}

	public static String remove(Arquivos arquivos) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction t = session.beginTransaction();
		try {
			session.delete(arquivos);
			t.commit();
			session.close();
			return "deleted";
		} catch (Exception e) {
			session.close();
			return "failed";
		}

	}

}
