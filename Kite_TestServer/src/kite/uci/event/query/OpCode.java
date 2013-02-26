package kite.uci.event.query;

public enum OpCode 
{
		IN ("in"),
		EQUAL ("="),
		GREATER_THAN (">"),
		GREATER_EQUAL (">="),
		LESS_THAN ("<"),
		LESSER_EQUAL ("<=");
		
	
		OpCode(String opCode)
		{
			this.opCode = opCode;
		}
	
		private String opCode;
		
		public OpCode parseOpCode(String opCode)
		{
			
			for (OpCode code : OpCode.values())
			{
				if (code.opCode().equalsIgnoreCase(opCode))
					return code;
			}
			return null;
		}
		
		public String opCode()
		{
			return opCode;
		}
		
	
}
