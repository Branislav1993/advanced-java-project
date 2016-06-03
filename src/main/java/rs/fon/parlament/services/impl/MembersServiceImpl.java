package rs.fon.parlament.services.impl;

import rs.fon.parlament.dao.MembersDao;
import rs.fon.parlament.dao.SpeechDao;
import rs.fon.parlament.domain.Member;
import rs.fon.parlament.domain.Speech;
import rs.fon.parlament.services.MembersService;
import rs.fon.parlament.services.util.ServiceResponse;

public class MembersServiceImpl implements MembersService {

	private MembersDao md;
	private SpeechDao sd;
	
	public MembersServiceImpl() {
		md = new MembersDao();
		sd = new SpeechDao();
	}

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
	
	@Override
	public ServiceResponse<Speech> getMemberSpeeches(int id, int limit, int page, String qtext, String from, String to){
		ServiceResponse<Speech> response = new ServiceResponse<>();
		response.setRecords(sd.getMemberSpeeches(id, limit, page, qtext, from, to));
		response.setTotalHits(sd.getMemberSpeechesTotalCount(id, qtext, from, to));
		
		return response;
	}

}
