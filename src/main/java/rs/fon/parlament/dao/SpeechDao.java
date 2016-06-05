package rs.fon.parlament.dao;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;

import rs.fon.parlament.database.HibernateUtil;
import rs.fon.parlament.domain.Speech;

public class SpeechDao {

	private final Logger logger = LogManager.getLogger(SpeechDao.class);
	private SimpleDateFormat sdf1 = new SimpleDateFormat("dd-MM-yyyy");
	
	public Speech getSpeech(int id) {
		Session session = HibernateUtil.getInstance().getSessionFactory().openSession();
		session.beginTransaction();

		Speech s = (Speech) session.get(Speech.class, id);

		session.close();

		return s;
	}

	public Speech insertSpeech(Speech s) {
		Session session = HibernateUtil.getInstance().getSessionFactory().openSession();
		session.beginTransaction();

		try {
			session.save(s);
			session.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
			session.getTransaction().rollback();
			session.close();
			return null;
		}

		Speech newSpeech = (Speech) session.get(Speech.class, s.getId());
		session.close();

		return newSpeech;

	}

	public boolean deleteSpeech(int id) {
		Session session = HibernateUtil.getInstance().getSessionFactory().openSession();
		session.beginTransaction();

		try {
			Speech s = (Speech) session.get(Speech.class, id);
			session.delete(s);
			session.getTransaction().commit();
			session.close();

			return true;
		} catch (Exception e) {
			e.printStackTrace();
			session.getTransaction().rollback();
			session.close();

			return false;
		}
	}
	
	public Speech updateSpeech(Speech s) {
		Session session = HibernateUtil.getInstance().getSessionFactory().openSession();
		session.beginTransaction();

		try {
			session.update(s);
			session.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
			session.getTransaction().rollback();
			session.close();
			return null;
		}

		Speech newSpeech = (Speech) session.get(Speech.class, s.getId());
		session.close();

		return newSpeech;

	}

	@SuppressWarnings("unchecked")
	public List<Speech> getMemberSpeeches(int id, int limit, int page, String qtext, String from, String to) {

		Session session = HibernateUtil.getInstance().getSessionFactory().openSession();
		session.beginTransaction();

		java.util.Date fromDate = null;
		java.util.Date toDate = null;

		try {
			if (!from.isEmpty())
				fromDate = sdf1.parse(from);
			if (!to.isEmpty())
				toDate = sdf1.parse(to);
		} catch (ParseException e) {
			logger.warn(e);
		}

		String queryString = "SELECT s " + "FROM Speech s " + "WHERE s.member.id = :memberId";

		if (!qtext.isEmpty()) {
			queryString += " AND s.text LIKE CONCAT('%', :text, '%')";
		}

		if (fromDate != null && toDate == null) {
			queryString += " AND s.sessionDate >= :fromDate";
		} else if (fromDate == null && toDate != null) {
			queryString += " AND s.sessionDate <= :toDate";
		} else if (fromDate != null && toDate != null) {
			queryString += " AND s.sessionDate BETWEEN :fromDate and :toDate";
		}

		Query query = session.createQuery(queryString);

		if (!qtext.isEmpty()) {
			query.setString("text", qtext);
		}

		if (fromDate != null && toDate == null) {
			query.setDate("fromDate", fromDate);
		} else if (fromDate == null && toDate != null) {
			query.setDate("toDate", toDate);
		} else if (fromDate != null && toDate != null) {
			query.setDate("fromDate", fromDate);
			query.setDate("toDate", toDate);
		}

		List<Speech> all = query.setParameter("memberId", id)
								.setFirstResult((page - 1) * limit)
								.setMaxResults(limit).list();

		session.close();

		return all;
	}

	@SuppressWarnings("unchecked")
	public List<Speech> getSpeeches(int limit, int page, String sort) {
		
		Session session = HibernateUtil.getInstance().getSessionFactory().openSession();
		session.beginTransaction();

		String query = "SELECT s " + "FROM Speech s " + "ORDER BY s.id " + sort;

		List<Speech> all = session.createQuery(query)
							 	  .setFirstResult((page - 1) * limit)
								  .setMaxResults(limit).list();

		session.close();

		return all;
	}

	@SuppressWarnings("unchecked")
	public List<Speech> getPlenarySessionSpeeches(int id, int limit, int page) {

		Session session = HibernateUtil.getInstance().getSessionFactory().openSession();
		session.beginTransaction();

		String query = "SELECT s " + "FROM Speech s " + "WHERE s.plenarySessionId = :plenarySessionId "
				+ "ORDER BY s.id";

		List<Speech> all = session.createQuery(query)
								  .setParameter("plenarySessionId", id)
								  .setFirstResult((page - 1) * limit)
								  .setMaxResults(limit).list();

		session.close();

		return all;
	}
	
	public Long getSpeechesTotalCount(){
		Session session = HibernateUtil.getInstance().getSessionFactory().openSession();
		session.beginTransaction();
		
		String queryString = "SELECT count (s.id) " +
							 "FROM Speech s";
		
		Query query = session.createQuery(queryString);
		
		Long countResults = (Long) query.uniqueResult();
		
		session.close();
		
		return countResults;
	}
	
	public Long getSessionSpeechesTotalCount(int id){
		Session session = HibernateUtil.getInstance().getSessionFactory().openSession();
		session.beginTransaction();
		
		String queryString = "SELECT count (s.id) " +
							 "FROM Speech s " +
							 "WHERE s.plenarySessionId = :plenarySessionId";
		
		Query query = session.createQuery(queryString).setParameter("plenarySessionId", id);
		
		Long countResults = (Long) query.uniqueResult();
		
		session.close();
		
		return countResults;
	}
	
	public Long getMemberSpeechesTotalCount(int id, String qtext, String from, String to){
		Session session = HibernateUtil.getInstance().getSessionFactory().openSession();
		session.beginTransaction();
		
		java.util.Date fromDate = null;
		java.util.Date toDate = null;

		try {
			if (!from.isEmpty())
				fromDate = sdf1.parse(from);
			if (!to.isEmpty())
				toDate = sdf1.parse(to);
		} catch (ParseException e) {
			logger.warn(e);
		}
		
		String queryString = "SELECT count (s.id) " +
							 "FROM Speech s " +
							 "WHERE s.member.id = :memberId";
		
		if (!qtext.isEmpty()) {
			queryString += " AND s.text LIKE CONCAT('%', :text, '%')";
		}

		if (fromDate != null && toDate == null) {
			queryString += " AND s.sessionDate >= :fromDate";
		} else if (fromDate == null && toDate != null) {
			queryString += " AND s.sessionDate <= :toDate";
		} else if (fromDate != null && toDate != null) {
			queryString += " AND s.sessionDate BETWEEN :fromDate and :toDate";
		}
		
		Query query = session.createQuery(queryString).setParameter("memberId", id);
		
		if (!qtext.isEmpty()) {
			query.setString("text", qtext);
		}

		if (fromDate != null && toDate == null) {
			query.setDate("fromDate", fromDate);
		} else if (fromDate == null && toDate != null) {
			query.setDate("toDate", toDate);
		} else if (fromDate != null && toDate != null) {
			query.setDate("fromDate", fromDate);
			query.setDate("toDate", toDate);
		}
		
		Long countResults = (Long) query.uniqueResult();
		
		session.close();
		
		return countResults;
	}

}
