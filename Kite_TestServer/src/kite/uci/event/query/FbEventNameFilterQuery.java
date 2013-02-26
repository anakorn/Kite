package kite.uci.event.query;


public class FbEventNameFilterQuery implements IFbEventFilterQuery
{

	@Override
	public String getFilteredString(String value) 
	{
		StringBuilder builder = new StringBuilder();
		builder.append(String.format("strpos(%s, \"%s\") >= 0", FBEventTable.NAME, value));
		
		return builder.toString();
	}

}
