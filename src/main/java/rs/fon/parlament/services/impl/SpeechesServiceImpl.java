package rs.fon.parlament.services.impl;

import rs.fon.parlament.dao.SpeechDao;
import rs.fon.parlament.domain.Speech;
import rs.fon.parlament.services.SpeechesService;
import rs.fon.parlament.services.util.ServiceResponse;

public class SpeechesServiceImpl implements SpeechesService {

	private SpeechDao sd = new SpeechDao();

	@Override
	public Speech getSpeech(int id) {
		return sd.getSpeech(id);
	}

	@Override
	public boolean deleteSpeech(int id) {
		return sd.deleteSpeech(id);
	}

	@Override
	public Speech updateSpeech(Speech s) {
		return sd.updateSpeech(s);
	}

	@Override
	public Speech insertSpeech(Speech s) {
		return sd.insertSpeech(s);
	}

	@Override
	public ServiceResponse<Speech> getSpeeches(int limit, int page, String sort) {
		ServiceResponse<Speech> response = new ServiceResponse<>();
		response.setRecords(sd.getSpeeches(limit, page, sort));
		response.setTotalHits(sd.getSpeechesTotalCount());
		
		return response;
	}

}
