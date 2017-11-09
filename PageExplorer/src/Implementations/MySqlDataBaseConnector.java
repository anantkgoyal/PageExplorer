package Implementations;

import java.sql.*;

import Interfaces.IDatabaseConnector;

public class MySqlDataBaseConnector implements IDatabaseConnector
{

	Connection mySqlConnection;
	public MySqlDataBaseConnector() throws SQLException
	{
		mySqlConnection = DriverManager.getConnection("jdbc:mysql://localhost", "user", "password");
		Statement statement = mySqlConnection.createStatement();
		
		String tableCreationSql = "CREATE TABLE IF NOT EXISTS bento.VisitedPages (PageLink nvarchar(500), id nvarchar(40) primary key)";
		String tableClearSql = "TRUNCATE TABLE bento.VisitedPages";
		
		statement.executeUpdate(tableCreationSql);
		statement.executeUpdate(tableClearSql);
	}
	
	@Override
	public void AddToDataBase(String pageName, String id) throws SQLException 
	{
		String insertSql = "INSERT INTO bento.VisitedPages VALUES (?, ?)";
		PreparedStatement statement = mySqlConnection.prepareStatement(insertSql);
		statement.setString(1, pageName);
		statement.setString(2, id);
		statement.executeUpdate();
	}

	@Override
	public Boolean ExistsInDataBase(String pageName) throws SQLException {
		
		String insertSql = "SELECT COUNT(1) as Total FROM bento.VisitedPages WHERE PageLink = ?";
		PreparedStatement statement = mySqlConnection.prepareStatement(insertSql);
		statement.setString(1, pageName);
		
		ResultSet t = statement.executeQuery();
		
		t.next();
		
		int count = t.getInt("Total");	
				
		return count > 0;
		
	}

}
