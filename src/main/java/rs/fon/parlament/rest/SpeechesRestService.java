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
import rs.fon.parlament.domain.Speech;
import rs.fon.parlament.rest.exceptions.AppException;
import rs.fon.parlament.rest.parsers.json.ParlamentJsonParser;
import rs.fon.parlament.rest.util.ParameterChecker;
import rs.fon.parlament.services.SpeechesService;
import rs.fon.parlament.services.impl.SpeechesServiceImpl;
import rs.fon.parlament.services.util.ServiceResponse;
import rs.fon.parlament.util.ResourceBundleUtil;
import rs.fon.parlament.util.exceptions.KeyNotFoundInBundleException;

@Path("/speeches")
public class SpeechesRestService {

	private final Logger logger;
	private SpeechesService speechesService;
	
	public SpeechesRestService() {
		logger = LogManager.getLogger(SpeechesRestService.class);
		speechesService = new SpeechesServiceImpl();
	}

	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON + ";charset=UTF-8")
	public Response getSpeech(@PathParam("id") int id) {

		Speech s = speechesService.getSpeech(id);

		if (s == null) {
			try {
				throw new AppException(Status.NOT_FOUND,
						ResourceBundleUtil.getMessage("speeches.not_found.noSpeechId", String.valueOf(id)));
			} catch (KeyNotFoundInBundleException e) {
				logger.error(e);
				throw new AppException(Status.NOT_FOUND, e.getMessage());
			}
		}

		String json = ParlamentJsonParser.gson.toJson(s);

		return Response.status(Status.OK).entity(json).build();
	}

	@DELETE
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON + ";charset=UTF-8")
	public Response deleteSpeech(@PathParam("id") int id) {

		boolean deleted = speechesService.deleteSpeech(id);

		if (deleted) {
			try {
				throw new AppException(Status.BAD_REQUEST, ResourceBundleUtil.getMessage("speeches.delete_error"));
			} catch (KeyNotFoundInBundleException e) {
				logger.error(e);
				throw new AppException(Status.NOT_FOUND, e.getMessage());
			}
		}

		return Response.status(Status.OK).build();
	}

	@POST
	@Produces(MediaType.APPLICATION_JSON + ";charset=UTF-8")
	public Response insertSpeech(Speech s) {

		Speech newSpeech = speechesService.insertSpeech(s);

		if (newSpeech == null) {
			try {
				throw new AppException(Status.BAD_REQUEST, ResourceBundleUtil.getMessage("speeches.insert_error"));
			} catch (KeyNotFoundInBundleException e) {
				logger.error(e);
				throw new AppException(Status.NOT_FOUND, e.getMessage());
			}
		}

		String json = ParlamentJsonParser.gson.toJson(newSpeech);

		return Response.status(Status.OK).entity(json).build();
	}

	@PUT
	@Produces(MediaType.APPLICATION_JSON + ";charset=UTF-8")
	public Response updateSpeech(Speech s) {

		Speech newSpeech = speechesService.updateSpeech(s);

		if (newSpeech == null) {
			try {
				throw new AppException(Status.BAD_REQUEST, ResourceBundleUtil.getMessage("speeches.update_error"));
			} catch (KeyNotFoundInBundleException e) {
				logger.error(e);
				throw new AppException(Status.NOT_FOUND, e.getMessage());
			}
		}

		String json = ParlamentJsonParser.gson.toJson(newSpeech);

		return Response.status(Status.OK).entity(json).build();
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON + ";charset=UTF-8")
	public Response getSpeeches(	@QueryParam("limit") int limit,
									@QueryParam("page") int page,
									@QueryParam("sort") String sortType) {

		// validation
		int validLimit = ParameterChecker.check(limit, Settings.getInstance().config.query.limit);
		int validPage = ParameterChecker.check(page, 1);
		String validSortType = ParameterChecker.check(sortType, "ASC", new String[] { "ASC", "DESC" });
		
		// retrieving the data
		ServiceResponse<Speech> response = speechesService.getSpeeches(validLimit, validPage, validSortType);
		
		List<Speech> speeches = response.getRecords();
		long count = response.getTotalHits();

		if (speeches.isEmpty())
			try {
				throw new AppException(Status.NOT_FOUND, ResourceBundleUtil.getMessage("speeches.not_found.noMembers"));
			} catch (KeyNotFoundInBundleException e) {
				logger.error(e);
				throw new AppException(Status.NOT_FOUND, e.getMessage());
			}

		String json = ParlamentJsonParser.serialize(speeches, validLimit, validPage, count).toString();

		return Response.status(Response.Status.OK).entity(json).build();
	}

}
