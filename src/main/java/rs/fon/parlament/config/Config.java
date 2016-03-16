package rs.fon.parlament.config;

public class Config {

	public DbConfig dbConfig;

	public QueryConfig query;

	public DbConfig getDbConfig() {
		return dbConfig;
	}

	public void setDbConfig(DbConfig dbConfig) {
		this.dbConfig = dbConfig;
	}

	public QueryConfig getQuery() {
		return query;
	}

	public void setQuery(QueryConfig query) {
		this.query = query;
	}

}
