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
import rs.fon.parlament.domain.Party;
import rs.fon.parlament.rest.exceptions.AppException;
import rs.fon.parlament.rest.parsers.json.ParlamentJsonParser;
import rs.fon.parlament.rest.util.ParameterChecker;
import rs.fon.parlament.services.PartiesService;
import rs.fon.parlament.services.impl.PartiesServiceImpl;
import rs.fon.parlament.services.util.ServiceResponse;
import rs.fon.parlament.util.ResourceBundleUtil;
import rs.fon.parlament.util.exceptions.KeyNotFoundInBundleException;

@Path("/parties")
public class PartiesRestService {

	private final Logger logger;
	private PartiesService partiesService;
	
	public PartiesRestService() {
		logger = LogManager.getLogger(PartiesRestService.class);
		partiesService = new PartiesServiceImpl();
	}

	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON + ";charset=UTF-8")
	public Response getParty(@PathParam("id") int id) {

		Party p = partiesService.getParty(id);

		if (p == null) {
			try {
				throw new AppException(Status.NOT_FOUND,
						ResourceBundleUtil.getMessage("parties.not_found.noPartyId", String.valueOf(id)));
			} catch (KeyNotFoundInBundleException e) {
				logger.error(e);
				throw new AppException(Status.NOT_FOUND, e.getMessage());
			}
		}

		String json = ParlamentJsonParser.gson.toJson(p);

		return Response.status(Status.OK).entity(json).build();
	}

	@DELETE
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON + ";charset=UTF-8")
	public Response deleteParty(@PathParam("id") int id) {

		boolean deleted = partiesService.deleteParty(id);

		if (!deleted) {
			try {
				throw new AppException(Status.BAD_REQUEST, ResourceBundleUtil.getMessage("parties.delete_error"));
			} catch (KeyNotFoundInBundleException e) {
				logger.error(e);
				throw new AppException(Status.NOT_FOUND, e.getMessage());
			}
		}

		return Response.status(Status.OK).build();
	}

	@POST
	@Produces(MediaType.APPLICATION_JSON + ";charset=UTF-8")
	public Response insertParty(Party p) {

		Party newParty = partiesService.insertParty(p);

		if (newParty == null) {
			try {
				throw new AppException(Status.BAD_REQUEST, ResourceBundleUtil.getMessage("parties.insert_error"));
			} catch (KeyNotFoundInBundleException e) {
				logger.error(e);
				throw new AppException(Status.NOT_FOUND, e.getMessage());
			}
		}

		String json = ParlamentJsonParser.gson.toJson(newParty);

		return Response.status(Status.OK).entity(json).build();
	}

	@PUT
	@Produces(MediaType.APPLICATION_JSON + ";charset=UTF-8")
	public Response updateParty(Party p) {

		Party newParty = partiesService.updateParty(p);

		if (newParty == null) {
			try {
				throw new AppException(Status.BAD_REQUEST, ResourceBundleUtil.getMessage("parties.update_error"));
			} catch (KeyNotFoundInBundleException e) {
				logger.error(e);
				throw new AppException(Status.NOT_FOUND, e.getMessage());
			}
		}

		String json = ParlamentJsonParser.gson.toJson(newParty);

		return Response.status(Status.OK).entity(json).build();
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON + ";charset=UTF-8")
	public Response getParties(	@QueryParam("limit") int limit,
								@QueryParam("page") int page,
								@QueryParam("sort") String sortType, 
								@QueryParam("query") String query) {

		// validation
		int validLimit = ParameterChecker.check(limit, Settings.getInstance().config.query.limit);
		int validPage = ParameterChecker.check(page, 1);
		String validSortType = ParameterChecker.check(sortType, "ASC", new String[] { "ASC", "DESC" });
		String validQuery = query != null ? query : "";
		
		// retrieving the data
		ServiceResponse<Party> response = partiesService.getParties(validLimit, validPage, validSortType, validQuery);
		
		List<Party> parties = response.getRecords();
		long count = response.getTotalHits();

		if (parties.isEmpty())
			try {
				throw new AppException(Status.NOT_FOUND, ResourceBundleUtil.getMessage("parties.not_found.noMembers"));
			} catch (KeyNotFoundInBundleException e) {
				logger.error(e);
				throw new AppException(Status.NOT_FOUND, e.getMessage());
			}

		String json = ParlamentJsonParser.serialize(parties, validLimit, validPage, count).toString();

		return Response.status(Response.Status.OK).entity(json).build();
	}
	
	@GET
	@Path("/{id}/members")
	@Produces(MediaType.APPLICATION_JSON + ";charset=UTF-8")
	public Response getPartyMembers(@PathParam("id") int id, @QueryParam("limit") int limit,
			@QueryParam("page") int page) {

		// validation
		int validLimit = ParameterChecker.check(limit, Settings.getInstance().config.query.limit);
		int validPage = ParameterChecker.check(page, 1);

		// retrieving the data
		ServiceResponse<Member> response = partiesService.getPartyMembers(id, validLimit, validPage);
		List<Member> members = response.getRecords();
		long count = response.getTotalHits();

		if (members == null || members.isEmpty())
			try {
				throw new AppException(Status.NO_CONTENT,
						ResourceBundleUtil.getMessage("parties.no_content.noMembers", String.valueOf(id)));
			} catch (KeyNotFoundInBundleException e) {
				logger.error(e);
				throw new AppException(Status.NOT_FOUND, e.getMessage());
			}

		String json = ParlamentJsonParser.serialize(members, validLimit, validPage, count).toString();

		return Response.status(Status.OK).entity(json).build();
	}

}
