package rs.fon.parlament.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import rs.fon.parlament.database.HibernateUtil;
import rs.fon.parlament.domain.Party;

public class PartyDao {

	public Party getParty(int id) {
		Session session = HibernateUtil.getInstance().getSessionFactory().openSession();
		session.beginTransaction();

		Party p = (Party) session.get(Party.class, id);

		session.close();

		return p;
	}

	public Party insertParty(Party p) {
		Session session = HibernateUtil.getInstance().getSessionFactory().openSession();
		session.beginTransaction();

		try {
			session.save(p);
			session.getTransaction().commit();
		} catch (Exception e) {
			session.getTransaction().rollback();
			session.close();
			return null;
		}

		Party newParty = (Party) session.get(Party.class, p.getId());
		session.close();

		return newParty;

	}

	public boolean deleteParty(int id) {
		Session session = HibernateUtil.getInstance().getSessionFactory().openSession();
		session.beginTransaction();

		try {
			Party p = (Party) session.get(Party.class, id);
			session.delete(p);
			session.getTransaction().commit();
			session.close();

			return true;
		} catch (Exception e) {
			session.getTransaction().rollback();
			session.close();

			return false;
		}
	}

	public Party updateParty(Party p) {
		Session session = HibernateUtil.getInstance().getSessionFactory().openSession();
		session.beginTransaction();

		try {
			session.update(p);
			session.getTransaction().commit();
		} catch (Exception e) {
			session.getTransaction().rollback();
			session.close();
			return null;
		}

		Party newParty = (Party) session.get(Party.class, p.getId());
		session.close();

		return newParty;

	}

	public Long getPartiesTotalCount(String q) {
		Session session = HibernateUtil.getInstance().getSessionFactory().openSession();
		session.beginTransaction();

		String queryString = "SELECT count (p.id) " + "FROM Party p ";

		if (!q.isEmpty()) {
			queryString += "WHERE p.name LIKE CONCAT('%', :name, '%'))";
		}

		Query query = session.createQuery(queryString);

		if (!q.isEmpty()) {
			query.setString("name", q);
		}

		Long countResults = (Long) query.uniqueResult();

		session.close();

		return countResults;
	}

	@SuppressWarnings("unchecked")
	public List<Party> getParties(int page, int limit, String sort, String q) {
		Session session = HibernateUtil.getInstance().getSessionFactory().openSession();
		session.beginTransaction();

		String queryString = "SELECT p FROM Party p ";

		if (!q.isEmpty()) {
			queryString += "WHERE p.name LIKE CONCAT('%', :name, '%'))";
		}
		queryString += "ORDER BY p.name " + sort;

		Query query = session.createQuery(queryString);

		if (!q.isEmpty()) {
			query.setString("name", q);
		}

		List<Party> all = query.setFirstResult((page - 1) * limit).setMaxResults(limit).list();

		session.close();

		return all;
	}

}
