package rs.fon.parlament.services.impl;

import rs.fon.parlament.dao.MembersDao;
import rs.fon.parlament.dao.PartyDao;
import rs.fon.parlament.domain.Member;
import rs.fon.parlament.domain.Party;
import rs.fon.parlament.services.PartiesService;
import rs.fon.parlament.services.util.ServiceResponse;

public class PartiesServiceImpl implements PartiesService {

	private PartyDao pd;
	private MembersDao md;
	
	public PartiesServiceImpl() {
		pd = new PartyDao();
		md = new MembersDao();
	}

	@Override
	public Party getParty(int id) {
		return pd.getParty(id);
	}

	@Override
	public boolean deleteParty(int id) {
		return pd.deleteParty(id);
	}

	@Override
	public Party updateParty(Party p) {
		return pd.updateParty(p);
	}

	@Override
	public Party insertParty(Party p) {
		return pd.insertParty(p);
	}

	@Override
	public ServiceResponse<Party> getParties(int limit, int page, String sort, String query) {
		ServiceResponse<Party> response = new ServiceResponse<>();
		response.setRecords(pd.getParties(page, limit, sort, query));
		response.setTotalHits(pd.getPartiesTotalCount(query));
		
		return response;
	}

	@Override
	public ServiceResponse<Member> getPartyMembers(int id, int limit, int page) {
		ServiceResponse<Member> response = new ServiceResponse<>();
		response.setRecords(md.getPartyMembers(id, limit, page));
		response.setTotalHits(md.getPartyMembersTotalCount(id));
		
		return response;
	}

}
