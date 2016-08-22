package xyz.aryainc;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * Created by ishan on 8/19/2016.
 */
public class SortElements {
	
	/*
	Will sort Elements list into order by kbps; Will also get rid of the ad-boxes
	 */
	public static void mergeSort(Elements list, String fileName){
		
		if(fileName.contains("remix") || fileName.contains("cover") || fileName.contains("instrumental")){
			for(int i = 0; i < list.size(); i++){
				if(list.get(i).attr("class").contains("song"))
					list.remove(i);
			}
		} else{
			//System.out.println(list.size());
			for(int i = 0; i < list.size(); i++){
				if(list.get(i).attr("class").contains("song")){
					list.remove(i);
				}
				//Remove file if name contains "remix" or "cover"
				else if(parseTitle(list.get(i)).contains("remix") || parseTitle(list.get(i)).contains("cover") || parseTitle(list.get(i)).contains("instrumental")){
					list.remove(i);
				}
			}
		}
		
		//System.out.println(list.size());
		mergeSortHelper(list, 0, list.size() - 1, new Element[list.size()]);
	}
	
	public static void mergeSortHelper(Elements list, int start, int end, Element[] space){
		if(end > start){
			int mid = (start + end) / 2;
			mergeSortHelper(list, start, mid, space);
			mergeSortHelper(list, mid + 1, end, space);
			merge(list, start, mid, end, space);
		}
	}
	
	public static void merge(Elements list, int start, int mid, int end, Element[] space){
		int leftFront = start, rightFront = mid + 1, index = start;
		
		while(leftFront <= mid && rightFront <= end){
			if(parseKBPS(list.get(leftFront)) > parseKBPS(list.get(rightFront))){
				space[index] = list.get(leftFront);
				leftFront++;
			}
			else{
				space[index] = list.get(rightFront);
				rightFront++;
			}
			index++;
		}
		
		while(leftFront <= mid){
			space[index] = list.get(leftFront);
			leftFront++;
			index++;
		}
		while(rightFront <= end){
			space[index] =  list.get(rightFront);
			rightFront++;
			index++;
		}
		for(int i = start; i <= end; i++){
			list.remove(i);
			list.add(i, space[i]);
		}
	}
	
	public static int parseKBPS(Element element){
		//the details about the song listed on the left
		String info = element.select("div.left").first().text().trim();
		
		//check if song lists kbps
		if(info.indexOf("k") == -1)
			return -1;
		
		//extract kbps
		//System.out.println("KBPS: " + Integer.parseInt(info.substring(0, info.indexOf("k") - 1).trim()));
		return Integer.parseInt(info.substring(0, info.indexOf("k") - 1).trim());
		
	}
	
	public static String parseTitle(Element element){
		return element.select("div.mp3_title").select("b").first().text().trim();
	}
}
