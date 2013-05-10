package br.tottou.data;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import br.tottou.model.entities.Agenda;


public class AgendaDao {
	

	
	public static void salvarAgenda(Agenda agenda) {

		Session sessao = HibernateUtil.getSessionFactory().openSession();
		Transaction t = sessao.beginTransaction();
		sessao.save(agenda); 
		t.commit();
		sessao.close();
	}
	
	public static void atualizarAgenda(Agenda agenda) {

		Session sessao = HibernateUtil.getSessionFactory().openSession();
		Transaction t = sessao.beginTransaction();
		sessao.merge(agenda); 
		t.commit();
		sessao.close();
	}
	
	  public static Agenda getAgenda(long id) {
	        Session session = HibernateUtil.getSessionFactory().openSession();
	        Agenda agenda= (Agenda) session.get(Agenda.class, id);
	        session.close();
	        return agenda;
	    }
	  
	    @SuppressWarnings("unchecked")
		public static List<Agenda> list() {
	        Session session = HibernateUtil.getSessionFactory().openSession();
	        Transaction t = session.beginTransaction();
	        List<Agenda> lista = session.createQuery("from Agenda").list();
	        t.commit();
	       session.close();
	        return lista;
	       
	    }
	    
	    @SuppressWarnings("unchecked")
		public static List<Agenda> listEmpresa(long id_empresa) {
	        Session session = HibernateUtil.getSessionFactory().openSession();
	        Transaction t = session.beginTransaction();
	        List<Agenda> lista = session.createQuery("from Agenda where id_empresat ="+id_empresa).list();
	        t.commit();
	       session.close();
	        return lista;
	       
	    }
	    
	    @SuppressWarnings("unchecked")
	  		public static List<Agenda> listAluno(long id_aluno) {
	  	        Session session = HibernateUtil.getSessionFactory().openSession();
	  	        Transaction t = session.beginTransaction();
	  	        List<Agenda> lista = session.createQuery("from Agenda where id_sessaoaluno ="+id_aluno).list();
	  	        t.commit();
	  	       session.close();
	  	        return lista;
	  	       
	  	    }
	    

	    public static void remove(Agenda agenda) {
	        Session session = HibernateUtil.getSessionFactory().openSession();
	        Transaction t = session.beginTransaction();
	        session.delete(agenda);
	        t.commit();
	        session.close();
	    }

}
