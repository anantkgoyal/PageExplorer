package Implementations;

import java.io.FileInputStream;
import java.sql.*;
import java.util.Properties;

import Interfaces.IDatabaseConnector;

public class MySqlDataBaseConnector implements IDatabaseConnector
{

	Connection mySqlConnection;
	public MySqlDataBaseConnector() throws Exception
	{
		Properties properties = new Properties();
		properties.load(new FileInputStream(System.getProperty("user.dir")+"\\src\\config.properties"));
		
		String connectionString = properties.getProperty("connectionString");
		String userName = properties.getProperty("username");
		String password = properties.getProperty("password");
		
		mySqlConnection = DriverManager.getConnection(connectionString, userName, password);
		Statement statement = mySqlConnection.createStatement();
		
		String tableCreationSql = "CREATE TABLE IF NOT EXISTS bento.VisitedPages (PageLink nvarchar(500), id nvarchar(40) primary key)";
		
		statement.executeUpdate(tableCreationSql);

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

	@Override
	public void ResetDataBase() throws Exception {
		
		Statement statement = mySqlConnection.createStatement();
		
		String tableClearSql = "TRUNCATE TABLE bento.VisitedPages";
		
		statement.executeUpdate(tableClearSql);
		
	}

}
