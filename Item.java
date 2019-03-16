package priceWatcher;

import java.net.*;
import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;
import java.util.*;
import java.io.*;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Item implements Serializable{
	private static final long serialVersionUID = 1L;
	private String price;
	private String currentPrice;
	private String title;
	private String domain;
	private String url;
	private static double priceChange;
	private String date;

	//Initializes the item with the following parameters	
	public void Item(String price, String title, String url, String date){
		this.price = price;
		this.title = title;
		this.url = url;
		this.date = date;
		this.domain = getDomain(url);
	}

	public String showPrice(){
		return price;
	}
	
	public String showCurrentPrice()throws MalformedURLException, IOException{
		currentPrice = getPrice(url);
		return currentPrice;
	}

	public String showTitle(){
		return title;
	}

	public String showDomain(){
		return domain;
	}

	public  String showDate(){
		return date;
	}

	public String showUrl(){
		return url;
	}

	private static String getPrice(String url)throws MalformedURLException, IOException{
		priceFinder p = new priceFinder();
		return p.priceFinder(url);
	}

	public double showChange()throws MalformedURLException, IOException{
		priceChange = priceChange(url, price);
		return priceChange;
	}
	
	//returns the price change of the item
	private static double priceChange(String url, String Price)throws MalformedURLException, IOException{
		String p2 = getPrice(url);
		Price = Price.replaceAll("[a-zA-z$ ]", "");
		double pr = Double.parseDouble(Price);
		p2 = p2.replaceAll("[a-zA-z$ ]", "");
		double price2 = Double.parseDouble(p2);
		priceChange = (price2 - pr) / pr;
		return priceChange;
	}

	//Finds the domain of an item
	public static String getDomain(String url){
		String d = null;
		try{
			URI uri = new URI(url);
			d = uri.getHost();
		} catch (URISyntaxException e){	
			e.printStackTrace();
		}
		String dom = d.startsWith("www.") ? d.substring(4) : d;
		return dom;
	}
//Will Implement Later to send an email to the user when there is a price change on an item in the database
/* 
	public static void notifyUser(String userEmail){
		Properties props = new Properties();

		//private information
		String username = "";
		String password = "";
		
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");
		Session session = Session.getDefaultInstance(props, new javax.mail.Authenticator(){
			protected PasswordAuthentication getPasswordAuthentication(){
				return new PasswordAuthentication(username, password);	
			}
		});

		try {
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress("sean.carnahan1@gmail.com"));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(userEmail));
			message.setSubject("");
			message.setText("");
			Transport.send(message);
			System.out.println("Sent message successfully...");
		} catch (MessagingException e){
			e.printStackTrace();
		}
	}		
*/
}
