package br.tottou.data;


import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import br.tottou.model.entities.Perfil;



public class PerfilDao {
	
	public static void salvarPerfil(Perfil perfil) {

		Session sessao = HibernateUtil.getSessionFactory().openSession();
		Transaction t = sessao.beginTransaction();
		sessao.save(perfil); 
		t.commit();
		sessao.close();
	}
	
	public static void atualizarPerfil(Perfil perfil) {

		Session sessao = HibernateUtil.getSessionFactory().openSession();
		Transaction t = sessao.beginTransaction();
		sessao.merge(perfil);
		t.commit();
		sessao.close();
		
	}
	
	  public static Perfil getPerfil(long id) {
	        Session session = HibernateUtil.getSessionFactory().openSession();
	        Perfil perfil = (Perfil)session.get(Perfil.class, id);
	        session.close();
	        return perfil;
	    }
	  
	    @SuppressWarnings("unchecked")
		public static List<Perfil> list() {
	        Session session = HibernateUtil.getSessionFactory().openSession();
	        Transaction t = session.beginTransaction();
	        List<Perfil> lista = session.createQuery("from Perfil").list();
	        t.commit();
	        session.close();
	        return lista;
	       
	    }
	    
	    @SuppressWarnings("unchecked")
	  		public static List<Perfil> listEmpresa(long id_empresa) {
	  	        Session session = HibernateUtil.getSessionFactory().openSession();
	  	        Transaction t = session.beginTransaction();
	  	        List<Perfil> lista = session.createQuery("from Perfil where id_empresa ="+id_empresa).list();	  	        
	  	        t.commit();
	  	        session.close();
	  	        return lista;
	  	       
	  	    }
	    
	    @SuppressWarnings("unchecked")
  		public static List<Perfil> listEmpresaCategoria(long id_empresa, long categoria) {
  	        Session session = HibernateUtil.getSessionFactory().openSession();
  	        Transaction t = session.beginTransaction();
  	        List<Perfil> lista = session.createQuery("from Perfil where id_empresa ="+id_empresa+"and categoria ="+categoria).list();	  	        
  	        t.commit();
  	        session.close();
  	        return lista;
  	       
  	    }
	    
	    public static void remove(Perfil perfil) {
	        Session session = HibernateUtil.getSessionFactory().openSession();
	        Transaction t = session.beginTransaction();
	        session.delete(perfil);
	        t.commit();
	        session.close();
	    }


}
