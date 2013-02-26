package kite.uci.event.requestParser;

import kite.uci.event.query.FbQueryBuilder;
import kite.uci.event.query.FbQueryBuilder.WhereClause;
import kite.uci.event.query.OpCode;

public class FbEReqNameParser implements IFbEReqWhereClauseParser
{
	private FbQueryBuilder qBuilder;
	
	public FbEReqNameParser(FbQueryBuilder qBuilder)
	{
		this.qBuilder = qBuilder;
		
	}
	
	@Override
	public WhereClause parseWhereClause(String value) 
	{
		return qBuilder.new WhereClause(
				String.format("strpos(%s, \"%s\")", IFbEReqWhereClauseParser.NAME, value),
				OpCode.GREATER_EQUAL, 
				"0");
		
	}

}
