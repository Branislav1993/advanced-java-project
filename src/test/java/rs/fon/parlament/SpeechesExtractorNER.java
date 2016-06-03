package rs.fon.parlament;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import org.hibernate.Session;

import rs.fon.parlament.database.HibernateUtil;
import rs.fon.parlament.domain.Speech;

public class SpeechesExtractorNER {

	@SuppressWarnings("unchecked")
	public static void main(String[] args) {
		Session session = HibernateUtil.getInstance().getSessionFactory().openSession();
		session.beginTransaction();

		String query = "SELECT s " + "FROM Speech s " + "WHERE s.plenarySession.id =2083";

		List<Speech> all = session.createQuery(query).list();

		session.close();
		HibernateUtil.getInstance().shutdown();

		System.out.println(all.size());

		try {
			File file = new File("C:\\Users\\Baki\\Desktop\\speeches.txt");
			// if file doesn't exists, then create it, and all missing
			// directories
			if (!file.exists()) {
				file.getParentFile().mkdirs();
				file.createNewFile();
			}

			BufferedWriter bw = new BufferedWriter(new FileWriter(file.getAbsoluteFile()));

			for (Speech s : all) {
				bw.write(s.getText().replaceAll("\\<.*?>", ""));			}
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
			System.out.println(e.getCause());
			System.err.println("Couldn't write to file");
			System.exit(1);
		}

	}

}
