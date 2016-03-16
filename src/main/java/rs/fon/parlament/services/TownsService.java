package rs.fon.parlament.services;

import java.util.List;

import rs.fon.parlament.domain.Town;

public interface TownsService {
	
	public Town getTown(int id);

	public boolean deleteTown(int id);

	public Town updateTown(Town t);

	public Town insertTown(Town t);
	
	public List<Town> getTowns();

}
