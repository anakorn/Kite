package kite.uci.event.requestParser;

import kite.uci.event.query.FbQueryBuilder;
import kite.uci.event.query.FbQueryBuilder.WhereClause;
import kite.uci.event.query.OpCode;

public class FbEReqDescriptionParser implements IFbEReqWhereClauseParser
{
	private FbQueryBuilder qBuilder;
	
	public FbEReqDescriptionParser(FbQueryBuilder qBuilder)
	{
		this.qBuilder = qBuilder;
		
	}
	
	@Override
	public WhereClause parseWhereClause(String value) 
	{
		return qBuilder.new WhereClause(
				String.format("strpos(%s, \"%s\")", IFbEReqWhereClauseParser.DESCRIPTION, value),
				OpCode.GREATER_EQUAL, 
				"0");
		
	}

}
