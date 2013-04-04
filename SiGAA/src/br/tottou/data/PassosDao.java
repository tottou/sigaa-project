package br.tottou.data;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import br.tottou.model.entities.ProgPassos;

public class PassosDao {
	
	public static void salvarProgPassos(ProgPassos passos) {

		Session sessao = HibernateUtil.getSessionFactory().openSession();
		Transaction t = sessao.beginTransaction();
		sessao.save(passos); 
		t.commit();
		sessao.close();
	}
	
	public static void atualizarProgPassos(ProgPassos passos) {

		Session sessao = HibernateUtil.getSessionFactory().openSession();
		Transaction t = sessao.beginTransaction();
		sessao.merge(passos); 
		t.commit();
		sessao.close();
	}
	
	public static ProgPassos getProgPassos(long id) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        ProgPassos passos = (ProgPassos) session.get(ProgPassos.class, id);
        session.close();
        return passos;
        
    }
	
	  
    @SuppressWarnings("unchecked")
	public static List<ProgPassos> list() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction t = session.beginTransaction();
        List<ProgPassos> lista = session.createQuery("from passos").list();
        t.commit();
       session.close();
        return lista;
       
    }
    public static String remove(ProgPassos passos) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction t = session.beginTransaction();       
    	try {
    		 session.delete(passos);
			t.commit();
			session.close();
			return "deleted";
		} catch (Exception e) {
			session.close();
			return "failed";
		}
    }


}
