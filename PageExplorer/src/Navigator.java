import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;

import org.jsoup.Jsoup;
import org.jsoup.nodes.*;

public class Navigator 
{
	String FinalPage = "Philosophy - Wikipedia";
	
	public static String Naviagte(String input) throws IOException, URISyntaxException
	{
		URL inputPage;
		try 
		{
			
			inputPage = new URL(input);
		} catch (MalformedURLException e) 
		{
			return "Invalid URL";
		}
		
		URL wikipedia = new URL("http://www.wikipedia.org");
		
		URLConnection u = inputPage.openConnection();
			
		BufferedReader br = new BufferedReader(
                new InputStreamReader(u.getInputStream()));
		
		String titlePage;
		String inputLine;
		while ((inputLine = br.readLine()) != null) {
			
			if(inputLine.startsWith("<title>"))
            {
            	titlePage = inputLine;
            }
			
			if(inputLine.startsWith("<p>"))
            {
            	break;
            }
	    }
	    br.close();
	    
	    
	    System.out.println(inputLine);
	    
	    Document d = Jsoup.parse(inputLine);
	    Element link = d.select("a").first();
	    
	    System.out.println(link.attr("href"));
	    
	    
	    
	    
	    
		return "hello World";
	}
}
