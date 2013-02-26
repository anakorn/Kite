package kite.uci.event.query;


import java.util.LinkedHashSet;
import java.util.Set;

public class FbQueryBuilder 
{
	public static final String HOST = "graph.facebook.com/";
	public static final String PATH = "fql";
	
	public Set<String> selectedColumns;
	public String queryTable;
	public Set<WhereClause> whereClauses;
	

	
	public FbQueryBuilder(String table)
	{
		selectedColumns = new LinkedHashSet<String>();
		whereClauses = new LinkedHashSet<WhereClause>();
		queryTable = table;
	}
	
	public void appendSelectColumn(String column)
	{
		selectedColumns.add(column);
	}
	
	public void appendWhereClause(WhereClause clause)
	{
		whereClauses.add(clause);
	}
	
	public String buildQuery()
	{
		return String.format("SELECT %s FROM %s WHERE %s",
				buildSelectedColumns(), queryTable, buildWhereClause());	
	}
	
	public String buildSelectedColumns()
	{
		if (selectedColumns.size() > 0)
		{
			StringBuilder cBuilder = new StringBuilder();
			for (String column : selectedColumns)
				cBuilder.append(column).append(", ");
			
			cBuilder.delete(cBuilder.lastIndexOf(","), cBuilder.length());
			return cBuilder.toString();
		}
		
		return "";
	}
	
	public String buildWhereClause()
	{
		if (whereClauses.size() > 0)
		{
			StringBuilder builder = new StringBuilder();
			for (WhereClause clause : whereClauses)
				builder.append(clause.buildWhereClause()).append(" and ");
			
			builder.delete(builder.lastIndexOf(" and"), builder.length());
	
			return builder.toString();
		}
		
		return "";
	}
	
	public class WhereClause
	{
		
		private OpCode opCode;
		private String parameter;
		private String value;
		private String whereClause;
		public WhereClause(String parameter, OpCode opCode, String value)
		{
			this.parameter = parameter;
			this.opCode = opCode;
			this.value = value;
		}
		
		public WhereClause(String whereClause)
		{
			this.whereClause = whereClause;
		}
		
		public String buildWhereClause()
		{
			if (whereClause != null)
				return whereClause;
			
			return String.format("(%s %s %s)", parameter, opCode.opCode(), value);
		}	
	}
	
}
