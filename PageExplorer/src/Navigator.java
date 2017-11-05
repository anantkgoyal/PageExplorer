import java.lang.Object;
import java.net.*;

public class Navigator 
{
	public static String Naviagte(String input)
	{
		URL inputPage;
		try 
		{
			inputPage = new URL(input);
		} catch (MalformedURLException e) 
		{
			return "Invalid URL";
		}
		
		
		
		System.out.println(input);
		return "hello World";
	}
}
