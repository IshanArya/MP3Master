package xyz.aryainc;


import com.sun.media.sound.InvalidFormatException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.ByteArrayInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
/**
 * Created by ishan on 8/19/2016.
 */
public class FetchSong {
	
	/*
	fetch best song order by comparing kbps (later plan to compare time with Spotify API)
	 */
	public static Elements fetchBestSongOrder(Document doc, String fileName){
		
		//extract songs
		Elements songs = doc.select("div#song_html");
		SortElements.mergeSort(songs, fileName);
		return songs;
		
		
	}
	
	/*
	return song's URL
	 */
	public static String fetchSongURL(Element song){
		return song.select("div.download_button").select("a").attr("abs:href");
	}
	
	/*
	validate that file is an MP3
	 */
	public static void validateMP3File(byte[] song) throws InvalidFormatException, IOException {
		InputStream file = new ByteArrayInputStream(song);
		byte[] startOfFile = new byte[1024];
		file.read(startOfFile);
		String id3 = new String(startOfFile);
		String tag = id3.substring(0, 3);
		if  ( ! "ID3".equals(tag) ) {
			throw new InvalidFormatException();
		}
	}
	
	/*
	return bytes for song
	 */
	public static byte[] fetchSong(String url) throws InvalidFormatException, IOException{
		byte[] bytes = Jsoup.connect(url)
				.userAgent("Mozilla/5.0 (Macintosh; Intel Mac OS X 10.11; rv:49.0) Gecko/20100101 Firefox/49.0")
				.ignoreContentType(true)
				.maxBodySize(0)
				.timeout(100000)
				.execute()
				.bodyAsBytes();
		
		
		try{
			validateMP3File(bytes);
			
			return bytes;
		} catch(InvalidFormatException e){
			System.err.println("ERROR: File you are trying to download is not an MP3!");
			throw new InvalidFormatException();
		} catch(IOException e){
			System.err.println("ERROR: Unable to fetch song");
		}
		
		return null;
	}
	
	/*
	save song in local directory (currently named "song.mp3")
	 */
	public static void downloadSong(byte[] bytes, String fileName) throws IOException {
		try(FileOutputStream fos = new FileOutputStream(fileName.trim() + ".mp3")){
			fos.write(bytes);
			
		} catch (IOException e) {
			System.err.println("ERROR: unable to save song");
			throw new IOException();
		}
	}
	
	/*
		specify which specific element on the page it is
	 */
	public static String specifySong(Element element){
		return element.attr("class");
	}
}
