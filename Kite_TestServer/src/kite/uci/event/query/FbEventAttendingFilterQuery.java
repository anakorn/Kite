package kite.uci.event.query;

import java.math.BigInteger;

import com.google.appengine.labs.repackaged.com.google.common.primitives.UnsignedLong;





public class FbEventAttendingFilterQuery implements IFbEventFilterQuery
{

	private FbEventAttendingEquality equality;

	@Override
	/**
	 * @precondition: equality must have been set before calling this method
	 */
	public String getFilteredString(String value)
			throws FbEventFilterQueryException 
	{
		// TODO Auto-generated method stub
		try
		{
			UnsignedLong.valueOf(value);
		}
		catch (NumberFormatException e)
		{
			throw new FbEventFilterQueryException(String.format("value: %s is not a valid number", value));
		}
		
		if (equality == null)
			throw new FbEventFilterQueryException(String.format("Equality value for attending has not been set"));
		
		return String.format("%s %s %s", FBEventTable.ATTENDING_COUNT, equality.equalityValue(), value);
			
	}
	
	public void setAttendingEquality (FbEventAttendingEquality equality)
	{
		this.equality = equality;
	}

	
	public class FbEventAttendingEquality implements IFbEventFilterQuery
	{

		public static final String GREATER_THAN = "greater_than";
		public static final String GREATER_EQUAL = "greater_equal";
		public static final String LESS_THAN = "less_than";
		public static final String LESSER_EQUAL = "less_than_equal";
		public static final String EQUAL = "equal";
		
		private String equalityValue;	// one of GREATER_THAN< GREATER_EQUAL, ...
		
		public FbEventAttendingEquality(String value) throws FbEventFilterQueryException
		{
			this.equalityValue = getFilteredString(value);
		}
		
		public String equalityValue()
		{
			return equalityValue;
		}
		
		@Override
		public String getFilteredString(String value)
				throws FbEventFilterQueryException 
		{
			// TODO Auto-generated method stub
			switch (value)
			{
			case GREATER_THAN:
				return ">";
			case GREATER_EQUAL:
				return ">=";
			case LESS_THAN:
				return "<";
			case LESSER_EQUAL:
				return "<=";
			case EQUAL:
				return "=";
				default:
					throw new FbEventFilterQueryException(String.format("attending_equality value: %s is unsupported"));
			}
		}
		
		
	}
	
}
