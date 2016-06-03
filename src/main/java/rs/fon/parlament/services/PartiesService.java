package rs.fon.parlament.services;

import rs.fon.parlament.domain.Member;
import rs.fon.parlament.domain.Party;
import rs.fon.parlament.services.util.ServiceResponse;

public interface PartiesService {
	
	public Party getParty(int id);

	public boolean deleteParty(int id);

	public Party updateParty(Party p);

	public Party insertParty(Party p);

	public ServiceResponse<Party> getParties(int limit, int page, String sort, String query);
	
	public ServiceResponse<Member> getPartyMembers(int id, int limit, int page);

}
