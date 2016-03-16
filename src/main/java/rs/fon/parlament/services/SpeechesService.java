package rs.fon.parlament.services;

import rs.fon.parlament.domain.Speech;
import rs.fon.parlament.services.util.ServiceResponse;

public interface SpeechesService {

	public Speech getSpeech(int id);

	public boolean deleteSpeech(int id);

	public Speech updateSpeech(Speech s);

	public Speech insertSpeech(Speech s);

	public ServiceResponse<Speech> getSpeeches(int limit, int page, String sort);

}
