package priceWatcher;

import java.io.IOException;
/*
import org.jsoup.*;
import org.jsoup.helper.*;
import org.jsoup.nodes.*;
import org.jsoup.select.*;
*/
import java.io.*;
import java.util.*;
import java.net.*;

public class priceFinder{
	
	public String priceFinder(String url)throws MalformedURLException, IOException{
		String domain = Item.getDomain(url);
		URL website = new URL(url);
		BufferedReader br = new BufferedReader(new InputStreamReader(website.openStream()));
		String current = null;
//		BufferedReader br = new BufferedReader(new InputStreamReader(getConnection(url).getInputStream()));
		String price = null;
		String[] tokens = null;
		while((current = br.readLine()) != null){
			if(domain.equals("walmart.com")){
				tokens = current.split(">|=");
			}else if(domain.equals("bestbuy.com")){
				tokens = current.split(">|=");
			}else if(domain.equals("ebay.com")){
				tokens = current.split(">|=");	
			}else if(domain.equals("dickssportinggoods.com")){
				tokens = current.split("<|>");
			}else if(domain.equals("target.com")){
				tokens = current.split(":");
			}else if(domain.equals("costco.com")){
				tokens = current.split(">");
			}else if(domain.equals("amazon.com")){
				tokens = current.split("");
			}
			for(int i = 0; i < tokens.length; i++){
//				if(domain.equals("amazon.com")){
//					System.out.println(current);
				if(domain.equals("walmart.com")){
					if(tokens[i].equals("\"price-characteristic\" itemprop")){
						price = tokens[i+2];
						price = price.replaceAll("[\"]", "");
						return price;
					}
				}else if(domain.equals("bestbuy.com")){
					if(tokens[i].equals("\"price-block priceblock-large\" data-pricing-type")){
						price = tokens[i+2];
						price = price.replaceAll("[\"]", "");
						return price;
					}
				}else if(domain.equals("ebay.com")){
					if(tokens[i].equals("\"\" content")){
						price = tokens[i+1];
						price = price.replaceAll("[\"]", "");
						return price;
					}else if(tokens[i].equals("\"mm-saleDscPrc\"")){
						price = tokens[i+1];
						price = price.replaceAll("</span","");
						price = price.replaceAll("US $","");
						return price;
					}
				}else if(domain.equals("dickssportinggoods.com")){	
					if(tokens[i].equals("span itemprop=\"price\"")){
						price = tokens[i+1];
						return price;
					}
				}else if(domain.equals("target.com")){
					if(tokens[i].equals("\"Offer\",\"price\"")){
						price = tokens[i+1];
						price = price.replaceAll(",\"priceCurrency","");
						price = price.replaceAll("[\",]","");
						return price;
					}
				}else if(domain.equals("costco.com")){
					System.out.println(tokens[i]);
					if(tokens[i].equals("<span class=\"value\"")){
						price = tokens[i+1];
						price = price.replaceAll("</span","");
						return price;
					}
				}else{
					System.out.println("Sorry, but this website is unfamiliar");
				}
			}
		}
		System.out.println("Returned null");
		return price;
	}
//original JSoup implementation, but did not fit requirements for my current class
/*	public String priceFinder(String url){
		Document document = null;
		try{
			document = Jsoup.connect(url).userAgent("Opera").get();
		}catch (Exception e){
			e.printStackTrace();
		}
		String domain = Item.getDomain(url);
		System.out.println("Domain: " + domain);
		String p = null;	
		if(domain.equals("amazon.com")){
			Elements spanElement = document.select("#priceblock_ourprice");
			p = spanElement.text();
		}else if(domain.equals("bestbuy.com")){
			Element outerDiv = document.selectFirst("div.priceView-price");
			Element innerDiv = outerDiv.selectFirst("div.priceView-customer-price");
			Element spanElement = innerDiv.selectFirst("span.sr-only");
			String span = spanElement.text();
			p = span.replaceAll("[a-zA-z ]", "");
		}else if(domain.equals("ebay.com")){
			Element span = document.selectFirst("#prcIsum");
			p = span.text();
		}else if(domain.equals("walmart.com")){
			Element div = document.selectFirst("div.prod-PriceHero");
			Element span = document.selectFirst("span.price-characteristic");
			p = span.attr("content");
		}else if(domain.equals("target.com")){
			Element div = document.selectFirst("div.h-text-red");
			Element span = document.selectFirst("span.h-text-xl");
			p = span.text();
		}else{
			System.out.println("Unfamiliar website");
		}
		return p;
	}
*/
	//this method is specifically used to connect to Amazon.com
	public URLConnection getConnection(String url)throws MalformedURLException, IOException{
		URLConnection website = new URL(url).openConnection();
		website.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko    ) Chrome/23.0.1271.95 Safari/537.11");
		return website;
	}

	public String getTitle(String url)throws MalformedURLException, IOException{
		URL website = new URL(url);
		BufferedReader br = new BufferedReader(new InputStreamReader(website.openStream()));
		String domain = Item.getDomain(url);
		String title = null;
		String current = null;
		String[] tokens = null;
		while((current = br.readLine()) != null){	
			if(domain.equals("bestbuy.com")){
				tokens = current.split("<|>");
			}else if(domain.equals("costco.com")){
				tokens = current.split("<|>");
			}else if(domain.equals("ebay.com")){
				tokens = current.split(">|=|<");
			}else if(domain.equals("dickssportinggoods.com")){
				tokens = current.split("<|>|=");
			}else if(domain.equals("target.com")){
				tokens = current.split(":");
//				tokens = current.split("=|>|<");
			}else if(domain.equals("walmart.com")){
				tokens = current.split("=|>");
			}
			for(int i = 0; i < tokens.length; i++){
				if(domain.equals("bestbuy.com")){
					if(tokens[i].equals("h1 class=\"heading-5 v-fw-regular\"")){
						title = tokens[i+1];
						title = title.replaceAll("[\"]", "");
					}
				//Not yet working, will be implemented Soon
/*
				}else if(domain.equals("costco.com")){
                         		if(tokens[i].equals("h1 itemprop=\"name\"")){
						title = tokens[i+1];
						return title;
					}
*/				}else if(domain.equals("ebay.com")){
 					if(tokens[i].equals("\"u-dspn\"")){
						title = tokens[i+1];
						return title;
					}
				}else if(domain.equals("dickssportinggoods.com")){
					if(tokens[i].equals("\"name\"")){
						title = tokens[i+1];	
						return title;
					}
	                        }else if(domain.equals("target.com")){
					if(tokens[i].equals("\"Product\",\"name\"")){
						title = tokens[i+1];
						title = title.replaceAll(",\"brand\"","");
						title = title.replaceAll("[\"]","");
						return title;
					}
        	                }else if(domain.equals("walmart.com")){
 					if(tokens[i].equals("\"name\" content")){
						title = tokens[i+1];
						title = title.replaceAll("[\"]", "");
						return title;
					}
				}else{
					System.out.println("Sorry, but this website is unfamiliar");
				}
			}
		}

		//Original implementation to grab html using Joup
/*		Document document = null;
		String title = null;
		try{
			document = Jsoup.connect(url).userAgent("Opera").get();
		}catch(Exception e){
			e.printStackTrace();
		}
		title = document.title();
*/		return title;
	}
}
