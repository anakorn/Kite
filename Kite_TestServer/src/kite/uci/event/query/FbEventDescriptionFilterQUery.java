package kite.uci.event.query;

public class FbEventDescriptionFilterQUery implements IFbEventFilterQuery
{

	@Override
	public String getFilteredString(String value)
			throws FbEventFilterQueryException 
	{
		// TODO Auto-generated method stub
		return String.format("strpos(%s, \"%s\") >= 0", FBEventTable.DESCRIPTION, value);
	}

}
