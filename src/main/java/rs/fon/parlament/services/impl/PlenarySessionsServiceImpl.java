package rs.fon.parlament.services.impl;

import rs.fon.parlament.dao.PlenarySessionDao;
import rs.fon.parlament.domain.PlenarySession;
import rs.fon.parlament.services.PlenarySessionsService;
import rs.fon.parlament.services.util.ServiceResponse;

public class PlenarySessionsServiceImpl implements PlenarySessionsService {

	private PlenarySessionDao psd = new PlenarySessionDao();

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

}
