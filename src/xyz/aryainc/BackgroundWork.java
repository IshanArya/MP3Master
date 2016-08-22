package xyz.aryainc;

import javafx.scene.control.TextField;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.UnknownHostException;

/**
 * Created by ishan on 8/20/2016.
 */
public class BackgroundWork {
	
	//All the stuff to do with starting the connecting process to receiving the file and saving it
	public static void finalActivity(String fileName, String URL){
		Document doc = null;
		try {
			System.out.println("Connecting to URL...");
			
			doc = Jsoup.connect(URL)
					.userAgent("Mozilla/5.0 (Macintosh; Intel Mac OS X 10.11; rv:49.0) Gecko/20100101 Firefox/49.0")
					.ignoreHttpErrors(true)
					.ignoreContentType(true)
					.maxBodySize(0)
					.timeout(100000)
					.get();
			
			System.out.println("Successfully connected!");
		} catch (IOException e) {
			System.err.println("ERROR: Unable to connect to website.");
		}
		
		//boolean to see if download failed
		boolean success = false;
		
		//Will go through each element trying to download song.
		System.out.println("Organizing songs in best order...");
		Elements songs = FetchSong.fetchBestSongOrder(doc, fileName);
		System.out.println("Organized songs...");
		for(Element x : songs){
			
			try{
				System.out.println("Attempting to download from element: " + FetchSong.specifySong(x));
				FetchSong.downloadSong(FetchSong.fetchSong(FetchSong.fetchSongURL(x)), fileName);
				
				success = true;
				break;
			} catch (UnknownHostException e){
				System.err.println("ERROR: Link is either expired or invalid. Trying another link...");
			} catch (IOException e){
				//Empty because it will already be handled
			}
		}
		
		if(success)
			System.out.println("SUCCESS: SONG DOWNLOADED - " + fileName);
		else
			System.err.println("ERROR: None of the links for [" + fileName + "] were valid.");
	}
	
	//whatever actions happen when the button is pressed
	public static void searchAction(TextField input, String URL){
		String fileName = null;
		try {
			fileName = input.getText();
			input.clear();
			System.out.println("Fetching url...");
			URL = FetchURL.fetchFinalURL(fileName);
		} catch (IOException er) {
			System.err.println("ERROR: Unable to access website to fetch FCKH and URL; website may be down...");
		}
		finalActivity(fileName, URL);
	}
}
