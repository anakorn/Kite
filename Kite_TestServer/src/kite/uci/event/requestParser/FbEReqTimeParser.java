package kite.uci.event.requestParser;

import kite.uci.event.query.FBEventTable;
import kite.uci.event.query.FbQueryBuilder;
import kite.uci.event.query.FbQueryBuilder.WhereClause;
import kite.uci.event.query.OpCode;

public class FbEReqTimeParser implements IFbEReqWhereClauseParser
{
	private FbQueryBuilder qBuilder;
	
	public FbEReqTimeParser(FbQueryBuilder qBuilder)
	{
		this.qBuilder = qBuilder;
		
	}
	
	@Override
	public WhereClause parseWhereClause(String value) 
	{
		switch (value.toLowerCase())
		{
		case "now":
			return qBuilder.new WhereClause(
					FBEventTable.START_TIME, 
					OpCode.GREATER_EQUAL, 
					"0");
		}
		return qBuilder.new WhereClause(
				String.format("strpos(%s, \"%s\")", IFbEReqWhereClauseParser.LOCATION, value),
				OpCode.GREATER_EQUAL, 
				"0");
		
	}

}
