package br.tottou.data;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import br.tottou.model.entities.Aluno;


public class AlunoDao {
	

	
	
	
	public static void salvarAluno(Aluno aluno) {

		Session sessao = HibernateUtil.getSessionFactory().openSession();
		Transaction t = sessao.beginTransaction();
		sessao.save(aluno); 
		t.commit();
		sessao.close();
	}
	
	public static void atualizarAluno(Aluno aluno) {

		Session sessao = HibernateUtil.getSessionFactory().openSession();
		Transaction t = sessao.beginTransaction();
		sessao.merge(aluno); 
		t.commit();
		sessao.close();
	}
	
	  public static Aluno getAluno(long id) {
	        Session session = HibernateUtil.getSessionFactory().openSession();
	        Aluno aluno= (Aluno) session.get(Aluno.class, id);
	        session.close();
	        return aluno;
	    }
	  
	    @SuppressWarnings("unchecked")
		public static List<Aluno> list() {
	        Session session = HibernateUtil.getSessionFactory().openSession();
	        Transaction t = session.beginTransaction();
	        List<Aluno> lista = session.createQuery("from Aluno").list();
	        t.commit();
	       session.close();
	        return lista;
	       
	    }
	    
	    @SuppressWarnings("unchecked")
		public static List<Aluno> listEmpresa(long id_empresa) {
	        Session session = HibernateUtil.getSessionFactory().openSession();
	        Transaction t = session.beginTransaction();
	        List<Aluno> lista = session.createQuery("from Aluno where id_empresaa ="+id_empresa).list();
	        t.commit();
	       session.close();
	        return lista;
	       
	    }
	    public static void remove(Aluno aluno) {
	        Session session = HibernateUtil.getSessionFactory().openSession();
	        Transaction t = session.beginTransaction();
	        session.delete(aluno);
	        t.commit();
	        session.close();
	    }

}
