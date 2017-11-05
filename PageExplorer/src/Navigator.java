import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.jsoup.Jsoup;
import org.jsoup.nodes.*;
import org.jsoup.select.Elements;

public class Navigator 
{
	static String FinalPage = "Philosophy - Wikipedia";
	
	public static String Naviagte(String input) throws IOException, URISyntaxException
	{
		URL inputPage;
		
		Map<String, String> visited = new HashMap<String, String>();
		
		try 
		{
			
			inputPage = new URL(input);
		} catch (MalformedURLException e) 
		{
			return "Invalid URL";
		}
		
		URL wikipedia = new URL("https://www.wikipedia.org");
		
		PageDetails p;
		do
		{
			p = ExtractPageDetails(inputPage);
			inputPage = new URL(wikipedia, p.NextRef);
			
			if(visited.containsKey(p.PageTitle))
			{
				System.out.println("Infinite Loop");
				break;
			}
			
			String id = UUID.randomUUID().toString();
			visited.put(p.PageTitle, id);
			
			System.out.println(p.PageTitle);
			
		}while(!p.PageTitle.equals(FinalPage));
		
		
		
		return "hello World";
	}
	
	private static PageDetails ExtractPageDetails(URL inputPage) throws IOException
	{
		URLConnection u = inputPage.openConnection();
		
		BufferedReader br = new BufferedReader(
                new InputStreamReader(u.getInputStream()));
		
		String titlePage = "";
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
	    	    
	    Document title = Jsoup.parse(titlePage);
	    String titleText = title.text();
	    
	    Document firstParagraph = Jsoup.parse(inputLine);
	    Elements links = firstParagraph.select("a");
	    
	    String firstLink = "";
	    for(Element link : links){
	    	
	    	String href = link.attr("href");
	    	
	    	if(href.contains(":"))
	    	{
	    		continue;
	    	}
	    	
	    	if(href.startsWith("/wiki"))
	    	{
	    		firstLink = href;
	    		break;
	    	}
	    }
	    	   
	    PageDetails pageDetails = new PageDetails();
	    pageDetails.PageTitle = titleText;
	    pageDetails.NextRef = firstLink;
	    
	    return pageDetails;
	}
}
