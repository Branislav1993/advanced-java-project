package rs.fon.parlament.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import rs.fon.parlament.database.HibernateUtil;
import rs.fon.parlament.domain.PlenarySession;

public class PlenarySessionDao {
	
	public PlenarySession getPlenarySession(int id) {
		Session session = HibernateUtil.getInstance().getSessionFactory().openSession();
		session.beginTransaction();

		PlenarySession ps = (PlenarySession) session.get(PlenarySession.class, id);

		session.close();

		return ps;
	}

	public PlenarySession insertPlenarySession(PlenarySession ps) {
		Session session = HibernateUtil.getInstance().getSessionFactory().openSession();
		session.beginTransaction();

		try {
			session.save(ps);
			session.getTransaction().commit();
		} catch (Exception e) {
			session.getTransaction().rollback();
			session.close();
			return null;
		}

		PlenarySession newPlenarySession = (PlenarySession) session.get(PlenarySession.class, ps.getId());
		session.close();

		return newPlenarySession;

	}

	public boolean deletePlenarySession(int id) {
		Session session = HibernateUtil.getInstance().getSessionFactory().openSession();
		session.beginTransaction();

		try {
			PlenarySession ps = (PlenarySession) session.get(PlenarySession.class, id);
			session.delete(ps);
			session.getTransaction().commit();
			session.close();

			return true;
		} catch (Exception e) {
			session.getTransaction().rollback();
			session.close();

			return false;
		}
	}
	
	public PlenarySession updatePlenarySession(PlenarySession ps) {
		Session session = HibernateUtil.getInstance().getSessionFactory().openSession();
		session.beginTransaction();

		try {
			session.update(ps);
			session.getTransaction().commit();
		} catch (Exception e) {
			session.getTransaction().rollback();
			session.close();
			return null;
		}

		PlenarySession newPlenarySession = (PlenarySession) session.get(PlenarySession.class, ps.getId());
		session.close();

		return newPlenarySession;

	}

	@SuppressWarnings("unchecked")
	public List<PlenarySession> getPlenarySessions(int limit, int page) {

		Session session = HibernateUtil.getInstance().getSessionFactory().openSession();
		session.beginTransaction();

		String query = "SELECT ps " + 
					   "FROM PlenarySession ps ORDER BY ps.id";

		List<PlenarySession> all = session.createQuery(query)
				.setFirstResult((page - 1) * limit)
				.setMaxResults(limit).list();

		session.close();

		return all;
	}

	public Long getTotalCount(){
		Session session = HibernateUtil.getInstance().getSessionFactory().openSession();
		session.beginTransaction();
		
		String queryString = "SELECT count (ps.id) " +
							 "FROM PlenarySession ps";
		
		Query query = session.createQuery(queryString);
		
		Long countResults = (Long) query.uniqueResult();
		
		session.close();
		
		return countResults;
	}

}
