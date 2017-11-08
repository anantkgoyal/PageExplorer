package Implementations;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

import Interfaces.IWebConnector;

public class UrlWebConnector implements IWebConnector
{
	BufferedReader webBufferedReader;
	@Override
	public void CreateWebConnection(URL webPage) throws IOException 
	{
		URLConnection u = webPage.openConnection();
		
		webBufferedReader = new BufferedReader(
                new InputStreamReader(u.getInputStream()));
	}

	@Override
	public String GetNextLineOfWebPage() throws Exception 
	{
		if(webBufferedReader == null)
		{
			throw new Exception("Attempting to get the next line of a null buffered reader");
		}
		
		String nextLine = webBufferedReader.readLine();
		
		if(nextLine == null)
		{
			webBufferedReader.close();
			webBufferedReader = null;
		}
		
		return nextLine;
	}

}
