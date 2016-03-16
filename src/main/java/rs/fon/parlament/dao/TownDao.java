package rs.fon.parlament.dao;

import java.util.List;

import org.hibernate.Session;

import rs.fon.parlament.database.HibernateUtil;
import rs.fon.parlament.domain.Town;

public class TownDao {

	public Town getTown(int id) {
		Session session = HibernateUtil.getInstance().getSessionFactory().openSession();
		session.beginTransaction();

		Town t = (Town) session.get(Town.class, id);

		session.close();

		return t;
	}

	public Town insertTown(Town t) {
		Session session = HibernateUtil.getInstance().getSessionFactory().openSession();
		session.beginTransaction();

		try {
			session.save(t);
			session.getTransaction().commit();
		} catch (Exception e) {
			session.getTransaction().rollback();
			session.close();
			return null;
		}

		Town newTown = (Town) session.get(Town.class, t.getId());
		session.close();

		return newTown;

	}

	public boolean deleteTown(int id) {
		Session session = HibernateUtil.getInstance().getSessionFactory().openSession();
		session.beginTransaction();

		try {
			Town t = (Town) session.get(Town.class, id);
			session.delete(t);
			session.getTransaction().commit();
			session.close();

			return true;
		} catch (Exception e) {
			session.getTransaction().rollback();
			session.close();

			return false;
		}
	}

	public Town updateTown(Town t) {
		Session session = HibernateUtil.getInstance().getSessionFactory().openSession();
		session.beginTransaction();

		try {
			session.update(t);
			session.getTransaction().commit();
		} catch (Exception e) {
			session.getTransaction().rollback();
			session.close();
			return null;
		}

		Town newTown = (Town) session.get(Town.class, t.getId());
		session.close();

		return newTown;

	}
	
	@SuppressWarnings("unchecked")
	public List<Town> getTowns(){
		Session session = HibernateUtil.getInstance().getSessionFactory().openSession();
		session.beginTransaction();
		
		List<Town> towns = session.createCriteria(Town.class).list();
		
		session.close();
		
		return towns;
	}
	
//	@SuppressWarnings("unchecked")
//	public List<Town> getTowns(int page, int limit, String sort, String q) {
//		Session session = HibernateUtil.getInstance().getSessionFactory().openSession();
//		session.beginTransaction();
//
//		String queryString = "SELECT t " + "FROM Town t ";
//
//		if (q != null && !q.isEmpty()) {
//			queryString += "WHERE t.name " + "LIKE :name";
//		}
//		queryString += "ORDER BY t.name " + sort;
//
//		Query query = session.createQuery(queryString);
//
//		if (!q.isEmpty()) {
//			query.setString("name", q);
//		}
//
//		List<Town> all = query.setFirstResult((page - 1) * limit).setMaxResults(limit).list();
//
//		session.close();
//
//		return all;
//	}
//
//	public Long getTotalCount(String q) {
//		Session session = HibernateUtil.getInstance().getSessionFactory().openSession();
//		session.beginTransaction();
//
//		String queryString = "SELECT count (t.id) " + "FROM Town t ";
//
//		if (q != null && !q.isEmpty()) {
//			queryString += "WHERE t.name " + "LIKE :name";
//		}
//
//		Query query = session.createQuery(queryString);
//
//		if (!q.isEmpty()) {
//			query.setString("name", q);
//		}
//
//		Long countResults = (Long) query.uniqueResult();
//
//		session.close();
//
//		return countResults;
//	}
}
