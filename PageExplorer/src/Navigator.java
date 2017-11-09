import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.jsoup.Jsoup;
import org.jsoup.nodes.*;
import org.jsoup.select.Elements;

import Interfaces.IDatabaseConnector;
import Interfaces.IWebConnector;

public class Navigator 
{
	private static String FinalPage = "Philosophy";
	private static String WikiHostEn = "en.wikipedia.org";
	
	private IDatabaseConnector _databaseConnector;
	private IWebConnector _webConnector;
	
	
	public Navigator(IDatabaseConnector databaseConnector, IWebConnector webConnector)
	{
		_databaseConnector = databaseConnector;
		_webConnector = webConnector;
	}
	
	public String Naviagte(String input) throws Exception
	{
		URL inputPage;
		
		Map<String, String> visited = new HashMap<String, String>();
		
		try 
		{			
			inputPage = new URL(input);
		} 
		catch (MalformedURLException e) 
		{
			return "Invalid URL";
		}
		
		if(!inputPage.getHost().equals(WikiHostEn))
		{
			return "Please input a valid Wikipedia URL";
		}
		
		URL wikipedia = new URL("https://www.wikipedia.org");
		
		PageDetails p;
		do
		{			
			p = ExtractPageDetails(inputPage);
			inputPage = new URL(wikipedia, p.NextRef);
					
			if(_databaseConnector.ExistsInDataBase(p.PageTitle))
			{
				System.out.println("Infinite Loop with " + p.PageTitle);
				break;
			}
			
			System.out.println(p.PageTitle);
			
			String id = UUID.randomUUID().toString();
			_databaseConnector.AddToDataBase(p.PageTitle, id);
						
		}while(!p.PageTitle.equals(FinalPage));
		
		
		
		return "hello World";
	}
	
	private PageDetails ExtractPageDetails(URL inputPage) throws Exception
	{
		_webConnector.CreateWebConnection(inputPage);
		
		String titlePage = "";
		String inputLine;
		
		String fullHtml = "";
		
		while ((inputLine = _webConnector.GetNextLineOfWebPage()) != null) {
					
			fullHtml = fullHtml + inputLine + "\n";
						
			if(inputLine.startsWith("<p>"))
            {
            	break;
            }
	    }
	    	    	    
	    Document html = Jsoup.parse(fullHtml);
	    Element firstParagraph = html.select("p").first();
	    
	    Elements links = firstParagraph.select("a");
	    
	    String firstLink = "";
	    String titleText = "";
	    for(Element link : links)
	    {
	    	String href = link.attr("href");
	    		    	
	    	if(LinkIsInNamespace(href))
	    	{
	    		continue;
	    	}
	    	
	    	if(href.startsWith("/wiki"))
	    	{
	    		firstLink = href;
	    		titleText = link.attr("title");
	    		break;
	    	}
	    }
	    	   
	    PageDetails pageDetails = new PageDetails();
	    pageDetails.PageTitle = titleText;
	    pageDetails.NextRef = firstLink;
	    
	    return pageDetails;
	}
	
	private static boolean LinkIsInNamespace(String link)
	{
		if(link.startsWith("/wiki/Media:") ||
				link.startsWith("/wiki/Special:") ||
				link.startsWith("/wiki/User:") ||
				link.startsWith("/wiki/Wikipedia:") ||
				link.startsWith("/wiki/WP:") ||
				link.startsWith("/wiki/Project:") ||
				link.startsWith("/wiki/File:") ||
				link.startsWith("/wiki/MediaWiki:") ||
				link.startsWith("/wiki/Image:") ||
				link.startsWith("/wiki/Template:") ||
				link.startsWith("/wiki/Help:") ||
				link.startsWith("/wiki/Category:") ||
				link.startsWith("/wiki/Portal:") ||
				link.startsWith("/wiki/Book:") ||
				link.startsWith("/wiki/Draft:") ||
				link.startsWith("/wiki/Education Program:") ||
				link.startsWith("/wiki/TimedText:") ||
				link.startsWith("/wiki/Module:") ||
				link.startsWith("/wiki/WT:") ||
				link.startsWith("/wiki/CAT:") ||
				link.startsWith("/wiki/H:") ||
				link.startsWith("/wiki/MOS:") ||
				link.startsWith("/wiki/P:") ||
				link.startsWith("/wiki/Project Talk:") ||
				link.startsWith("/wiki/Image Talk:") ||
				link.startsWith("/wiki/MP:") ||
				link.startsWith("/wiki/WikiProject:") ||
				link.startsWith("/wiki/Wikiproject:") ||
				link.startsWith("/wiki/MoS:") ||
				link.startsWith("/wiki/media"))
		{
			return true;
		}
		
		return false;
	}
}
