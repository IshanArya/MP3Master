package xyz.aryainc;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import java.io.IOException;

/**
 * Created by ishan on 8/19/2016.
 */
public class FetchURL {
	
	//the active URL
	static String activeURL = null;
	
	/*
		get current active URL of mp3skull
    */
	public static void fetchActiveURL() throws IOException {
		Document doc = Jsoup.connect("https://piratestatus.com/")
				.userAgent("Mozilla/5.0 (Macintosh; Intel Mac OS X 10.11; rv:49.0) Gecko/20100101 Firefox/49.0")
				.ignoreHttpErrors(true)
				.followRedirects(true)
				.ignoreContentType(true)
				.maxBodySize(0)
				.timeout(100000)
				.get();
		
		activeURL = doc.select("a").get(1).attr("href") + "search_db.php?q=";
	}
	
	/*
		fetch FCKH, which is the temporary access code for mp3skull.vg
	*/
	public static String fetchFCKH() throws IOException{
		Document doc = Jsoup.connect(activeURL)
				.userAgent("Mozilla/5.0 (Macintosh; Intel Mac OS X 10.11; rv:49.0) Gecko/20100101 Firefox/49.0")
				.ignoreHttpErrors(true)
				.followRedirects(true)
				.ignoreContentType(true)
				.maxBodySize(0)
				.timeout(100000)
				.get();
		
		
		return doc.select("input[name=fckh]").first().attr("value");
	}
	
	/*
		fetch final URL with user query
	*/
	public static String fetchFinalURL(String searchQuery) throws IOException{
		if(activeURL == null){
			fetchActiveURL();
		}
		String[] input = searchQuery.trim().split("\\s+");
		StringBuilder statement = new StringBuilder(activeURL);
		
		for(int i = 0; i < input.length; i++){
			if(i == input.length - 1){
				statement.append(input[i]);
			}
			else{
				statement.append(input[i]).append("+");
			}
		}
		statement.append("&fckh=").append(fetchFCKH());
		
		//return final URL
		return statement.toString();
	}
}
