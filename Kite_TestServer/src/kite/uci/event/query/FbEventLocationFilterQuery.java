package kite.uci.event.query;

public class FbEventLocationFilterQuery implements IFbEventFilterQuery
{

	@Override
	public String getFilteredString(String value)
			throws FbEventFilterQueryException 
	{
		return String.format("strpos(%s, \"%s\") >= 0", FBEventTable.LOCATION, value);
	}

}
