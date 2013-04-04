package br.tottou.data;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import br.tottou.model.entities.Tarefa;

public class TarefaDao {
	
	public static void salvarTarefa(Tarefa tarefa) {

		Session sessao = HibernateUtil.getSessionFactory().openSession();
		Transaction t = sessao.beginTransaction();
		sessao.save(tarefa); 
		t.commit();
		sessao.close();
	}
	
	public static void atualizarTarefa(Tarefa tarefa) {

		Session sessao = HibernateUtil.getSessionFactory().openSession();
		Transaction t = sessao.beginTransaction();
		sessao.merge(tarefa); 
		t.commit();
		sessao.close();
	}
	
	public static Tarefa getTarefa(long id) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Tarefa tarefa = (Tarefa) session.get(Tarefa.class, id);
        session.close();
        return tarefa;
        
    }
	
	  
    @SuppressWarnings("unchecked")
	public static List<Tarefa> list() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction t = session.beginTransaction();
        List<Tarefa> lista = session.createQuery("from Tarefa").list();
        t.commit();
       session.close();
        return lista;
       
    }
    
    @SuppressWarnings("unchecked")
 	public static List<Tarefa> listEmpresa(long id_empresa) {
         Session session = HibernateUtil.getSessionFactory().openSession();
         Transaction t = session.beginTransaction();
         List<Tarefa> lista = session.createQuery("from Tarefa where id_empresat="+id_empresa).list();
         t.commit();
        session.close();
         return lista;
        
     }
    public static void remove(Tarefa tarefa) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction t = session.beginTransaction();
        session.delete(tarefa);
        t.commit();
        session.close();
    }


}
