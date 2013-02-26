package kite.uci.event.query;


public class FbEventTimeFilterQuery implements IFbEventFilterQuery
{
	public static final String CURRENT_EVENT = "current";
	public static final String PAST_EVENT = "past";
	public static final String FUTURE_EVENT = "future";
	@Override
	public String getFilteredString(String timeIndication) throws FbEventFilterQueryException 
	{
		switch (timeIndication)
		{
		case CURRENT_EVENT:
			return String.format("%s >= now() and %s <= now()", FBEventTable.END_TIME, FBEventTable.START_TIME);
		case PAST_EVENT:
			return String.format("%s < now()", FBEventTable.END_TIME);
		case FUTURE_EVENT:
			return String.format("%s > now()", FBEventTable.START_TIME);
		default:
			throw new FbEventFilterQueryException(String.format("Unsupported time-filtering-value: %s", timeIndication));
		}
	}
	
	
}
