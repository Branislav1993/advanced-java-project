package rs.fon.parlament.rest;

import java.util.List;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import rs.fon.parlament.config.Settings;
import rs.fon.parlament.domain.Member;
import rs.fon.parlament.domain.Speech;
import rs.fon.parlament.rest.exceptions.AppException;
import rs.fon.parlament.rest.parsers.json.ParlamentJsonParser;
import rs.fon.parlament.rest.util.ParameterChecker;
import rs.fon.parlament.services.MembersService;
import rs.fon.parlament.services.impl.MembersServiceImpl;
import rs.fon.parlament.services.util.ServiceResponse;
import rs.fon.parlament.util.ResourceBundleUtil;
import rs.fon.parlament.util.exceptions.KeyNotFoundInBundleException;

@Path("/members")
public class MembersRestService {
	
	private final Logger logger;
	private MembersService membersService;
	
	public MembersRestService() {
		logger = LogManager.getLogger(MembersRestService.class);
		membersService = new MembersServiceImpl();
	}

	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON + ";charset=UTF-8")
	public Response getMember(@PathParam("id") int id) {

		Member m = membersService.getMember(id);

		if (m == null) {
			try {
				throw new AppException(Status.NOT_FOUND,
						ResourceBundleUtil.getMessage("members.not_found.noMemberId", String.valueOf(id)));
			} catch (KeyNotFoundInBundleException e) {
				logger.error(e);
				throw new AppException(Status.NOT_FOUND, e.getMessage());
			}
		}

		String json = ParlamentJsonParser.gson.toJson(m);

		return Response.status(Status.OK).entity(json).build();
	}
	
	@DELETE
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON + ";charset=UTF-8")
	public Response deleteMember(@PathParam("id") int id) {
		
		boolean deleted = membersService.deleteMember(id);

		if (!deleted) {
			try {
				throw new AppException(Status.BAD_REQUEST, ResourceBundleUtil.getMessage("members.delete_error"));
			} catch (KeyNotFoundInBundleException e) {
				logger.error(e);
				throw new AppException(Status.NOT_FOUND, e.getMessage());
			}
		}

		return Response.status(Status.OK).build();
	}
	
	@POST
	@Produces(MediaType.APPLICATION_JSON + ";charset=UTF-8")
	public Response insertMember(Member m) {

		Member newMember = membersService.insertMember(m);

		if (newMember == null) {
			try {
				throw new AppException(Status.BAD_REQUEST, ResourceBundleUtil.getMessage("members.insert_error"));
			} catch (KeyNotFoundInBundleException e) {
				logger.error(e);
				throw new AppException(Status.NOT_FOUND, e.getMessage());
			}
		}

		String json = ParlamentJsonParser.gson.toJson(newMember);

		return Response.status(Status.OK).entity(json).build();
	}
	

	@PUT
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON + ";charset=UTF-8")
	public Response updateMember(Member m) {

		Member newMember = membersService.updateMember(m);

		if (newMember == null) {
			try {
				throw new AppException(Status.BAD_REQUEST, ResourceBundleUtil.getMessage("members.update_error"));
			} catch (KeyNotFoundInBundleException e) {
				logger.error(e);
				throw new AppException(Status.NOT_FOUND, e.getMessage());
			}
		}

		String json = ParlamentJsonParser.gson.toJson(newMember);

		return Response.status(Status.OK).entity(json).build();
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON + ";charset=UTF-8")
	public Response getMembers(	@QueryParam("limit") int limit,
								@QueryParam("page") int page,
								@QueryParam("sort") String sortType, 
								@QueryParam("query") String query) {

		// validation
		int validLimit = ParameterChecker.check(limit, Settings.getInstance().config.query.limit);
		int validPage = ParameterChecker.check(page, 1);
		String validSortType = ParameterChecker.check(sortType, "ASC", new String[] { "ASC", "DESC" });
		String validQuery = query != null ? query : "";
		
		// retrieving the data
		ServiceResponse<Member> response = membersService.getMembers(validLimit, validPage, validSortType, validQuery);
		
		List<Member> members = response.getRecords();
		long count = response.getTotalHits();

		if (members.isEmpty())
			try {
				throw new AppException(Status.NOT_FOUND, ResourceBundleUtil.getMessage("members.not_found.noMembers"));
			} catch (KeyNotFoundInBundleException e) {
				logger.error(e);
				throw new AppException(Status.NOT_FOUND, e.getMessage());
			}

		String json = ParlamentJsonParser.serialize(members, validLimit, validPage, count).toString();

		return Response.status(Response.Status.OK).entity(json).build();
	}
	
	@GET
	@Path("/{id}/speeches")
	@Produces(MediaType.APPLICATION_JSON + ";charset=UTF-8")
	public Response getMemberSpeeches(	@PathParam("id") int id,
										@QueryParam("limit") int limit,
										@QueryParam("page") int page,
										@QueryParam("query") String query,
										@QueryParam("from") String from,
										@QueryParam("to") String to) {

		// validation
		int validLimit = ParameterChecker.check(limit, Settings.getInstance().config.query.limit);
		int validPage = ParameterChecker.check(page, 1);
		String validQuery = query != null ? query : "";
		String validFromDate = ParameterChecker.check(from, "");
		String validToDate = ParameterChecker.check(to, "");
				
		// retrieving the data
		ServiceResponse<Speech> response = membersService.getMemberSpeeches(id, validLimit, validPage, validQuery, validFromDate, validToDate);

		List<Speech> speeches = response.getRecords();
		Long count = response.getTotalHits();
		
		if (speeches.isEmpty()) {
			try {
				throw new AppException(Status.NOT_FOUND,
						ResourceBundleUtil.getMessage("members.no_content.noSpeeches", String.valueOf(id)));
			} catch (KeyNotFoundInBundleException e) {
				logger.error(e);
				throw new AppException(Status.NOT_FOUND, e.getMessage());
			}
		}

		String json = ParlamentJsonParser.serialize(speeches, validLimit, validPage, count).toString();

		return Response.status(Response.Status.OK).entity(json).build();
	}
	
}
