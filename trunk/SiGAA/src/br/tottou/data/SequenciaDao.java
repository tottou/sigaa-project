package br.tottou.data;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import br.tottou.model.entities.ProgSequencia;

public class SequenciaDao {
	
	public static void salvarProgSequencia(ProgSequencia sequencia) {

		Session sessao = HibernateUtil.getSessionFactory().openSession();
		Transaction t = sessao.beginTransaction();
		sessao.save(sequencia); 
		t.commit();
		sessao.close();
	}
	
	public static void atualizarProgSequencia(ProgSequencia sequencia) {

		Session sessao = HibernateUtil.getSessionFactory().openSession();
		Transaction t = sessao.beginTransaction();
		sessao.merge(sequencia); 
		t.commit();
		sessao.close();
	}
	
	public static ProgSequencia getProgSequencia(long id) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        ProgSequencia sequencia = (ProgSequencia) session.get(ProgSequencia.class, id);
        session.close();
        return sequencia;
        
    }
	
	  
    @SuppressWarnings("unchecked")
	public static List<ProgSequencia> list() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction t = session.beginTransaction();
        List<ProgSequencia> lista = session.createQuery("from sequencia").list();
        t.commit();
       session.close();
        return lista;
       
    }
    public static String remove(ProgSequencia sequencia) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction t = session.beginTransaction();       
    	try {
    		 session.delete(sequencia);
			t.commit();
			session.close();
			return "deleted";
		} catch (Exception e) {
			session.close();
			return "failed";
		}
    }


}
