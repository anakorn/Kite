package kite.uci.event.query;

public interface IFbEventFilterQuery 
{
	public String getFilteredString(String value) throws FbEventFilterQueryException;
}
