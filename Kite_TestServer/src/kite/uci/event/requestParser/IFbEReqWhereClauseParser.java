package kite.uci.event.requestParser;

import kite.uci.event.query.FbQueryBuilder.WhereClause;

public interface IFbEReqWhereClauseParser 
{
	//----------------------- supported parameters-----------------------
	public static final String NAME = "name";
	public static final String DESCRIPTION = "description";
	public static final String LOCATION = "location";
	public static final String TIME = "time";
	public static final String HOST = "host";
	public static final String ATTENDING = "attending";
	public static final String EQUALITY = "equality";
	
	public WhereClause parseWhereClause(String value);
	
}
