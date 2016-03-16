package rs.fon.parlament.services.impl;

import rs.fon.parlament.dao.MembersDao;
import rs.fon.parlament.domain.Member;
import rs.fon.parlament.services.MembersService;
import rs.fon.parlament.services.util.ServiceResponse;

public class MembersServiceImpl implements MembersService {

	private MembersDao md = new MembersDao();

	@Override
	public Member getMember(int id) {
		return md.getMember(id);
	}

	@Override
	public boolean deleteMember(int id) {
		return md.deleteMember(id);
	}

	@Override
	public Member updateMember(Member m) {
		return md.updateMember(m);
	}

	@Override
	public Member insertMember(Member m) {
		return md.insertMember(m);
	}

	@Override
	public ServiceResponse<Member> getMembers(int limit, int page, String sort, String query) {
		
		ServiceResponse<Member> response = new ServiceResponse<>();
		response.setRecords(md.getMembers(limit, page, sort, query));
		response.setTotalHits(md.getTotalCount(query));
		
		return response;
	}

}
