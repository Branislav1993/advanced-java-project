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
import rs.fon.parlament.domain.PlenarySession;
import rs.fon.parlament.rest.exceptions.AppException;
import rs.fon.parlament.rest.parsers.json.ParlamentJsonParser;
import rs.fon.parlament.rest.util.ParameterChecker;
import rs.fon.parlament.services.PlenarySessionsService;
import rs.fon.parlament.services.impl.PlenarySessionsServiceImpl;
import rs.fon.parlament.services.util.ServiceResponse;
import rs.fon.parlament.util.ResourceBundleUtil;
import rs.fon.parlament.util.exceptions.KeyNotFoundInBundleException;

@Path("/sessions")
public class PlenarySessionsRestService {

	private final Logger logger;
	private PlenarySessionsService plenarySessionService;
	
	public PlenarySessionsRestService() {
		logger = LogManager.getLogger(PlenarySessionsRestService.class);
		plenarySessionService = new PlenarySessionsServiceImpl();
	}

	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON + ";charset=UTF-8")
	public Response getPlenarySession(@PathParam("id") int id) {

		PlenarySession ps = plenarySessionService.getPlenarySession(id);

		if (ps == null) {
			try {
				throw new AppException(Status.NOT_FOUND,
						ResourceBundleUtil.getMessage("sessions.not_found.noSessionId", String.valueOf(id)));
			} catch (KeyNotFoundInBundleException e) {
				logger.error(e);
			}
		}

		String json = ParlamentJsonParser.gson.toJson(ps);

		return Response.status(Status.OK).entity(json).build();
	}

	@DELETE
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON + ";charset=UTF-8")
	public Response deletePlenarySession(@PathParam("id") int id) {

		boolean deleted = plenarySessionService.deletePlenarySession(id);

		if (deleted) {
			try {
				throw new AppException(Status.BAD_REQUEST, ResourceBundleUtil.getMessage("sessions.delete_error"));
			} catch (KeyNotFoundInBundleException e) {
				logger.error(e);
			}
		}

		return Response.status(Status.OK).build();
	}

	@POST
	@Produces(MediaType.APPLICATION_JSON + ";charset=UTF-8")
	public Response insertPlenarySession(PlenarySession ps) {

		PlenarySession newPlenarySession = plenarySessionService.insertPlenarySession(ps);

		if (newPlenarySession == null) {
			try {
				throw new AppException(Status.BAD_REQUEST, ResourceBundleUtil.getMessage("sessions.insert_error"));
			} catch (KeyNotFoundInBundleException e) {
				logger.error(e);
			}
		}

		String json = ParlamentJsonParser.gson.toJson(newPlenarySession);

		return Response.status(Status.OK).entity(json).build();
	}

	@PUT
	@Produces(MediaType.APPLICATION_JSON + ";charset=UTF-8")
	public Response updatePlenarySession(PlenarySession ps) {

		PlenarySession newPlenarySession = plenarySessionService.updatePlenarySession(ps);

		if (newPlenarySession == null) {
			try {
				throw new AppException(Status.BAD_REQUEST, ResourceBundleUtil.getMessage("sessions.update_error"));
			} catch (KeyNotFoundInBundleException e) {
				logger.error(e);
			}
		}

		String json = ParlamentJsonParser.gson.toJson(newPlenarySession);

		return Response.status(Status.OK).entity(json).build();
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON + ";charset=UTF-8")
	public Response getPlenarySessions(	@QueryParam("limit") int limit,
										@QueryParam("page") int page,
										@QueryParam("sort") String sortType) {

		// validation
		int validLimit = ParameterChecker.check(limit, Settings.getInstance().config.query.limit);
		int validPage = ParameterChecker.check(page, 1);
		String validSortType = ParameterChecker.check(sortType, "ASC", new String[] { "ASC", "DESC" });
		
		// retrieving the data
		ServiceResponse<PlenarySession> response = plenarySessionService.getPlenarySessions(validLimit, validPage, validSortType);
		
		List<PlenarySession> plenarySessions = response.getRecords();
		long count = response.getTotalHits();

		if (plenarySessions.isEmpty())
			try {
				throw new AppException(Status.NOT_FOUND, ResourceBundleUtil.getMessage("sessions.not_found.noMembers"));
			} catch (KeyNotFoundInBundleException e) {
				logger.error(e);
			}

		String json = ParlamentJsonParser.serialize(plenarySessions, validLimit, validPage, count).toString();

		return Response.status(Response.Status.OK).entity(json).build();
	}
}
