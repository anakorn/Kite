package kite.uci.event.requestParser;

import kite.uci.event.query.FbQueryBuilder;
import kite.uci.event.query.FbQueryBuilder.WhereClause;
import kite.uci.event.query.OpCode;

public class FbEReqLocationParser implements IFbEReqWhereClauseParser
{
	private FbQueryBuilder qBuilder;
	
	public FbEReqLocationParser(FbQueryBuilder qBuilder)
	{
		this.qBuilder = qBuilder;
		
	}
	
	@Override
	public WhereClause parseWhereClause(String value) 
	{
		return qBuilder.new WhereClause(
				String.format("strpos(%s, \"%s\")", IFbEReqWhereClauseParser.LOCATION, value),
				OpCode.GREATER_EQUAL, 
				"0");
		
	}

}
