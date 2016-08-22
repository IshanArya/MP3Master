package xyz.aryainc;

import java.io.IOException;

/**
 * Created by ishan on 8/21/2016.
 */
public class Trial {
	public static void main(String[] args) throws IOException{
		FetchSong.downloadSong(FetchSong.fetchSong("http://95.211.205.131/get.php?q=YTo0OntzOjU6InRva2VuIjtzOjg1OiJmZDJmMzU3MGZmYzJjZmEyNTU3MGNkNWM3YjMwMjk1ZjI2NWZjOTFkMDI5YzJkM2Q3Y2QwZTUxYmRiNTY1NWRiODZmMDI5NDk1NzJmZjk0ZmZjOThmIjtzOjQ6InNpdGUiO3M6NzoibXAzY2xhbiI7czozOiJrZXkiO3M6MzI6Ijc4YzgyNjk2MGU2NWZlYTkxZjY1NDA5YmZlY2QwZTgyIjtzOjM6InRpZCI7czoxOToiMzMyNDY4OTQ2XzQ1NjI0MTc0OSI7fQ==&get"), "cold water justin bieber");
	}
}
