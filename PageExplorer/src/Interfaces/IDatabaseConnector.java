package Interfaces;

import java.sql.SQLException;

public interface IDatabaseConnector {

	void AddToDataBase(String pageName, String id) throws SQLException;
	
	Boolean ExistsInDataBase(String pageName) throws SQLException;
	
	void ResetDataBase() throws Exception;
}
