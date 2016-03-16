package rs.fon.parlament.services;

import rs.fon.parlament.domain.PlenarySession;
import rs.fon.parlament.services.util.ServiceResponse;

public interface PlenarySessionsService {

	public PlenarySession getPlenarySession(int id);

	public boolean deletePlenarySession(int id);

	public PlenarySession updatePlenarySession(PlenarySession ps);

	public PlenarySession insertPlenarySession(PlenarySession ps);

	public ServiceResponse<PlenarySession> getPlenarySessions(int limit, int page, String sort);

}