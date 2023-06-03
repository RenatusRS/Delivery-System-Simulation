package rs.etf.sab.student.utils;

import java.sql.*;

public class DB {
	private static Connection connection = null;
	
	public static void setConnection(String database, String server, int port) throws SQLException {
		connection = DriverManager.getConnection(
				"jdbc:sqlserver://" + server + ":" + port +
						";databaseName=" + database,
				"sa",
				"123"
		);
	}
	
	public static Result select(String table) {
		return select(table, (Where[][]) null);
	}
	
	public static Result select(String table, Where wheres) {
		return select(table, wheres == null ? null : new Where[][]{{wheres}});
	}
	
	public static Result select(String table, Where[] wheres) {
		return select(table, wheres == null ? null : new Where[][]{wheres});
	}
	
	public static Result select(String table, Where[][] wheres) {
		try {
			return get("SELECT * FROM [" + table + "]" + (wheres == null || wheres.length == 0 ? "" : " WHERE " + Where.toString(wheres)));
		} catch (SQLException e) {
			e.printStackTrace();
			return new Result();
		}
	}
	
	public static int insert(String table, Entry values) {
		String query = "INSERT INTO [" + table + "]";
		
		query += " (" + String.join(",", values.keySet()) + ")";
		
		query += " VALUES (" + String.join(",", values.values().stream().map(v -> {
			if (v instanceof String) {
				return "'" + v + "'";
			} else {
				return v.toString();
			}
		}).toArray(String[]::new)) + ")";
		
		try {
			return put(query);
		} catch (SQLException e) {
			e.printStackTrace();
			return -1;
		}
	}
	
	public static int update(String table, Entry values, Where wheres) {
		return update(table, values, new Where[]{wheres});
	}
	
	public static int update(String table, Entry values, Where[] wheres) {
		return update(table, values, new Where[][]{wheres});
	}
	
	public static int update(String table, Entry values, Where[][] wheres) {
		StringBuilder query = new StringBuilder("UPDATE [" + table + "] SET ");
		
		for (String key : values.keySet()) {
			Object o = values.get(key);
			
			if (o instanceof String && !((String) o).contains("+")) o = "'" + o + "'";
			
			query.append(key).append(" = ").append(o).append(",");
		}
		
		query.deleteCharAt(query.length() - 1);
		
		if (wheres != null && wheres.length > 0) {
			query.append(" WHERE ").append(Where.toString(wheres));
		}
		
		
		try {
			return set(query.toString());
		} catch (SQLException e) {
			e.printStackTrace();
			return -1;
		}
	}
	
	public static int delete(String table) {
		return delete(table, (Where[][]) null);
	}
	
	public static int delete(String table, Where wheres) {
		return delete(table, wheres == null ? null : new Where[][]{{wheres}});
	}
	
	public static int delete(String table, Where[] wheres) {
		return delete(table, wheres == null ? null : new Where[][]{wheres});
	}
	
	public static int delete(String table, Where[][] wheres) {
		String query = "DELETE FROM [" + table + "]";
		
		if (wheres != null && wheres.length > 0) {
			query += " WHERE " + Where.toString(wheres);
		}
		
		try {
			return set(query);
		} catch (SQLException e) {
			e.printStackTrace();
			return -1;
		}
	}
	
	private static Result get(String query) throws SQLException {
		System.out.println(query);
		
		try (Statement statement = connection.createStatement()) {
			ResultSet resultSet = statement.executeQuery(query);
			
			Result result = new Result();
			
			while (resultSet.next()) {
				Entry row = new Entry();
				
				ResultSetMetaData metaData = resultSet.getMetaData();
				
				for (int i = 1; i <= metaData.getColumnCount(); i++) {
					row.put(metaData.getColumnName(i), resultSet.getObject(i));
				}
				
				result.add(row);
			}
			
			System.out.println(result);
			
			return result;
		}
	}
	
	private static int set(String query) throws SQLException {
		System.out.println(query);
		
		try (Statement statement = connection.createStatement()) {
			return statement.executeUpdate(query);
		}
	}
	
	private static int put(String query) throws SQLException {
		System.out.println(query);
		
		try (Statement statement = connection.createStatement()) {
			statement.execute(query, Statement.RETURN_GENERATED_KEYS);
			
			ResultSet resultSet = statement.getGeneratedKeys();
			
			return resultSet.next() ? resultSet.getInt(1) : -1;
		}
	}
	
	
}
