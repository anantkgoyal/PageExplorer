package Interfaces;

import java.io.IOException;
import java.net.URL;

public interface IWebConnector 
{
	void CreateWebConnection(URL webPage) throws IOException;
	
	String GetNextLineOfWebPage() throws Exception;
}
