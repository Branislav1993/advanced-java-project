package rs.fon.parlament.rest;

import java.util.List;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import rs.fon.parlament.domain.Town;
import rs.fon.parlament.rest.exceptions.AppException;
import rs.fon.parlament.rest.parsers.json.ParlamentJsonParser;
import rs.fon.parlament.services.TownsService;
import rs.fon.parlament.services.impl.TownsServiceImpl;
import rs.fon.parlament.util.ResourceBundleUtil;
import rs.fon.parlament.util.exceptions.KeyNotFoundInBundleException;

@Path("/towns")
public class TownRestService {

	private final Logger logger;
	private TownsService townsService;
	
	public TownRestService() {
		logger = LogManager.getLogger(TownRestService.class);
		townsService = new TownsServiceImpl();
	}

	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON + ";charset=UTF-8")
	public Response getTown(@PathParam("id") int id) {

		Town t = townsService.getTown(id);

		if (t == null) {
			try {
				throw new AppException(Status.NOT_FOUND,
						ResourceBundleUtil.getMessage("towns.not_found.noTownId", String.valueOf(id)));
			} catch (KeyNotFoundInBundleException e) {
				logger.error(e);
			}
		}

		String json = ParlamentJsonParser.gson.toJson(t);

		return Response.status(Status.OK).entity(json).build();
	}

	@DELETE
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON + ";charset=UTF-8")
	public Response deleteTown(@PathParam("id") int id) {

		boolean deleted = townsService.deleteTown(id);

		if (deleted) {
			try {
				throw new AppException(Status.BAD_REQUEST, ResourceBundleUtil.getMessage("towns.delete_error"));
			} catch (KeyNotFoundInBundleException e) {
				logger.error(e);
			}
		}

		return Response.status(Status.OK).build();
	}

	@POST
	@Produces(MediaType.APPLICATION_JSON + ";charset=UTF-8")
	public Response insertTown(Town t) {

		Town newTown = townsService.insertTown(t);

		if (newTown == null) {
			try {
				throw new AppException(Status.BAD_REQUEST, ResourceBundleUtil.getMessage("towns.insert_error"));
			} catch (KeyNotFoundInBundleException e) {
				logger.error(e);
			}
		}

		String json = ParlamentJsonParser.gson.toJson(newTown);

		return Response.status(Status.OK).entity(json).build();
	}

	@PUT
	@Produces(MediaType.APPLICATION_JSON + ";charset=UTF-8")
	public Response updateTown(Town t) {

		Town newTown = townsService.updateTown(t);

		if (newTown == null) {
			try {
				throw new AppException(Status.BAD_REQUEST, ResourceBundleUtil.getMessage("towns.update_error"));
			} catch (KeyNotFoundInBundleException e) {
				logger.error(e);
			}
		}

		String json = ParlamentJsonParser.gson.toJson(newTown);

		return Response.status(Status.OK).entity(json).build();
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON + ";charset=UTF-8")
	public Response getTowns(){
		
		List<Town> towns = townsService.getTowns();
		
		if (towns.isEmpty()) {
			try {
				throw new AppException(Status.NOT_FOUND, ResourceBundleUtil.getMessage("towns.not_found.noTowns"));
			} catch (KeyNotFoundInBundleException e) {
				logger.error(e);
			}
		}
		
		String json = ParlamentJsonParser.serialize(towns).toString();
		
		return Response.status(Status.OK).entity(json).build();
		
	}

}
