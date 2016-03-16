package rs.fon.parlament.services;

import rs.fon.parlament.domain.Member;
import rs.fon.parlament.services.util.ServiceResponse;

public interface MembersService {
	
	public Member getMember(int id);

	public boolean deleteMember(int id);

	public Member updateMember(Member m);

	public Member insertMember(Member m);
	
	public ServiceResponse<Member> getMembers(int limit, int page, String sort, String query);
	

}
