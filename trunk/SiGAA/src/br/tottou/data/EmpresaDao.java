package br.tottou.data;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import br.tottou.model.entities.Empresa;


public class EmpresaDao {
	
	
		
		public static void salvarEmpresa(Empresa empresa) {

			Session sessao = HibernateUtil.getSessionFactory().openSession();
			Transaction t = sessao.beginTransaction();
			sessao.save(empresa); 
			t.commit();
			sessao.close();
		}
		
		public static void atualizarEmpresa(Empresa empresa) {

			Session sessao = HibernateUtil.getSessionFactory().openSession();
			Transaction t = sessao.beginTransaction();
			sessao.merge(empresa); 
			t.commit();
			sessao.close();
		}
		
		public static Empresa getEmpresa(long id) {
	        Session session = HibernateUtil.getSessionFactory().openSession();
	        return (Empresa) session.get(Empresa.class, id);
	        
	    }
	  
	    @SuppressWarnings("unchecked")
		public static List<Empresa> list() {
	        Session session = HibernateUtil.getSessionFactory().openSession();
	        Transaction t = session.beginTransaction();
	        List<Empresa> lista = session.createQuery("from Empresa").list();
	        t.commit();
	       session.close();
	        return lista;
	       
	    }
	    public static void remove(Empresa empresa) {
	        Session session = HibernateUtil.getSessionFactory().openSession();
	        Transaction t = session.beginTransaction();
	        session.delete(empresa);
	        t.commit();
	        session.close();
	    }

}
