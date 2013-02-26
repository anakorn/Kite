package kite.uci.event.query;

public class FbNestedQueryBuilder extends FbQueryBuilder
{

	public FbNestedQueryBuilder(String table) 
	{
		super(table);	
	}
	
	@Override
	public String buildQuery()
	{
		return String.format("(%s)", super.buildQuery());
	}

	
}
