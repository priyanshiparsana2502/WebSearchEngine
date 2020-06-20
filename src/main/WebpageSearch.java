package main;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashSet;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class WebpageSearch {
	private static final int MAX_DEPTH = 3;
	private static final int LIMIT = 450;
	private HashSet<String> pagelinks;
	private List<List<String>> articles;
	
	public WebpageSearch() {
		pagelinks = new HashSet<>();
		// pages = new ArrayList<>();
	}
	
	public int getNumOfPages() {
		// TODO Auto-generated method stub
		return pagelinks.size();
	}

	public void getPageLinks(String URL, int depth) {
		if ((!pagelinks.contains(URL) && URL.contains("https://www.w3schools.com/") && (depth < MAX_DEPTH))) {
			System.out.println(">> Depth: " + depth + " [" + URL + "]");
			try {
				pagelinks.add(URL);
				Document doc = Jsoup.connect(URL).get();
				Elements linksOnPage = doc.select("a[href]");
				depth++;
				for (Element page : linksOnPage) {
					if (pagelinks.size() > LIMIT) {
						break;
					}
					if (page.hasAttr("abs:href"))
						getPageLinks(page.attr("abs:href"), depth);
				}
			} catch (IOException e) {
				System.err.println("For '" + URL + "': " + e.getMessage());
			}
		}
	}

	public void Linkdownload() {
	pagelinks.forEach(x -> {
			Document doc;
			try {
				doc = Jsoup.connect(x).get();
			    
				File file = new File("HTMLfiles\\"+doc.title()+".html");
		        file.getParentFile().mkdir();
		        PrintWriter out = new PrintWriter(file);
		        
				try {
					String temp = doc.html();
					out.write(temp);
				} catch (Exception e) {
					// TODO: for handle exception
				}
				out.close();
			} catch (IOException e) {
				System.err.println();
			}
		});
	}

	public static void main(String[] args) throws IOException {
		WebpageSearch search = new WebpageSearch();
		search.getPageLinks("https://www.w3schools.com/", 0);
		System.out.println("Downloading web Pages..");
		search.Linkdownload();
		System.out.println("Downloading complete..");
	}

}
