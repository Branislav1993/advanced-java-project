package rs.fon.parlament.services.impl;

import java.util.List;

import rs.fon.parlament.dao.TownDao;
import rs.fon.parlament.domain.Town;
import rs.fon.parlament.services.TownsService;

public class TownsServiceImpl implements TownsService {
	
	private TownDao td = new TownDao();

	@Override
	public Town getTown(int id) {
		return td.getTown(id);
	}

	@Override
	public boolean deleteTown(int id) {
		return td.deleteTown(id);
	}

	@Override
	public Town updateTown(Town t) {
		return td.updateTown(t);
	}

	@Override
	public Town insertTown(Town t) {
		return td.insertTown(t);
	}

	@Override
	public List<Town> getTowns() {
		return td.getTowns();
	}

}
