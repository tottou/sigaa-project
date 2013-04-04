package br.tottou.data;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import br.tottou.model.entities.Categoria;


public class CategoriaDao {

	
	public static void salvarCategoria(Categoria categoria) {

		Session sessao = HibernateUtil.getSessionFactory().openSession();
		Transaction t = sessao.beginTransaction();
		sessao.save(categoria); 
		t.commit();
		sessao.close();
	}
	
	public static void atualizarCategoria(Categoria categoria) {

		Session sessao = HibernateUtil.getSessionFactory().openSession();
		Transaction t = sessao.beginTransaction();
		sessao.merge(categoria); 
		t.commit();
		sessao.close();
	}
	
	public static Categoria getCategoria(long id) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Categoria categoria = (Categoria) session.get(Categoria.class, id);
        session.close();
        return categoria;
        
    }
	
	  
    @SuppressWarnings("unchecked")
	public static List<Categoria> list() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction t = session.beginTransaction();
        List<Categoria> lista = session.createQuery("from Categoria").list();
        t.commit();
       session.close();
        return lista;
       
    }
    
    @SuppressWarnings("unchecked")
	public static List<Categoria> listEmpresa(long empresaId) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction t = session.beginTransaction();
        List<Categoria> lista = session.createQuery("from Categoria where id_empresac ="+empresaId).list();
        t.commit();
       session.close();
        return lista;
       
    }
    public static void remove(Categoria categoria) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction t = session.beginTransaction();
        session.delete(categoria);
        t.commit();
        session.close();
    }

}
