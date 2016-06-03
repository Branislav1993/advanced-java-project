package rs.fon.parlament.services.impl;

import rs.fon.parlament.dao.PlenarySessionDao;
import rs.fon.parlament.dao.SpeechDao;
import rs.fon.parlament.domain.PlenarySession;
import rs.fon.parlament.domain.Speech;
import rs.fon.parlament.services.PlenarySessionsService;
import rs.fon.parlament.services.util.ServiceResponse;

public class PlenarySessionsServiceImpl implements PlenarySessionsService {

	private PlenarySessionDao psd;
	private SpeechDao sd;

	public PlenarySessionsServiceImpl() {
		 psd = new PlenarySessionDao();
		 sd = new SpeechDao();
	}

	@Override
	public PlenarySession getPlenarySession(int id) {
		return psd.getPlenarySession(id);
	}

	@Override
	public boolean deletePlenarySession(int id) {
		return psd.deletePlenarySession(id);
	}

	@Override
	public PlenarySession updatePlenarySession(PlenarySession ps) {
		return psd.updatePlenarySession(ps);
	}

	@Override
	public PlenarySession insertPlenarySession(PlenarySession ps) {
		return psd.insertPlenarySession(ps);
	}

	@Override
	public ServiceResponse<PlenarySession> getPlenarySessions(int limit, int page, String sort) {
		ServiceResponse<PlenarySession> response = new ServiceResponse<>();
		response.setRecords(psd.getPlenarySessions(limit, page));
		response.setTotalHits(psd.getTotalCount());
		
		return response;
	}
	
	@Override
	public ServiceResponse<Speech> getPlenarySessionSpeeches(int id, int limit, int page){
		ServiceResponse<Speech> response = new ServiceResponse<>();
		response.setRecords(sd.getPlenarySessionSpeeches(id, limit, page));
		response.setTotalHits(sd.getSessionSpeechesTotalCount(id));
		
		return response;
	}

}
