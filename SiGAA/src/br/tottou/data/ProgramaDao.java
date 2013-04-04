package br.tottou.data;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import br.tottou.model.entities.Programa;

public class ProgramaDao {
	
	public static void salvarPrograma(Programa programa) {

		Session sessao = HibernateUtil.getSessionFactory().openSession();
		Transaction t = sessao.beginTransaction();
		sessao.save(programa); 
		t.commit();
		sessao.close();
	}
	
	public static void atualizarPrograma(Programa programa) {

		Session sessao = HibernateUtil.getSessionFactory().openSession();
		Transaction t = sessao.beginTransaction();
		sessao.merge(programa); 
		t.commit();
		sessao.close();
	}
	
	public static Programa getPrograma(long id) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Programa programa = (Programa) session.get(Programa.class, id);
        session.close();
        return programa;
        
    }
	
	  
    @SuppressWarnings("unchecked")
	public static List<Programa> list() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction t = session.beginTransaction();
        List<Programa> lista = session.createQuery("from Programa").list();
        t.commit();
       session.close();
        return lista;
       
    }
    public static void remove(Programa programa) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction t = session.beginTransaction();
        session.delete(programa);
        t.commit();
        session.close();
    }


}
