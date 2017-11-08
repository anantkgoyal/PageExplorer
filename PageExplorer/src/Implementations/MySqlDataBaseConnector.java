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
		statement.executeLargeUpdate("TRUNCATE TABLE bento.VisitedPages");
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
