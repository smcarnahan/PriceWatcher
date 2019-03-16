package priceWatcher;

import java.util.*;
import java.io.*;
import java.net.*;

public class ConsoleUI{
	private Item item;

	public ConsoleUI(Item item){
		this.item = item;
	}

	public void showWelcome(){
		System.out.println("Welcome to Price Watcher");
	}

	public void showItem()throws MalformedURLException, IOException{
		System.out.println("Here are the Item details: ");
		System.out.println("Item Title: " + item.showTitle());
		System.out.println("Item Initial Price: " + item.showPrice());
		System.out.println("Item Current Price: " + item.showCurrentPrice());
		System.out.println("Item Date added: " + item.showDate());
		System.out.println("Item Domain: " + item.showDomain());
		System.out.println("Item Price Change: " + item.showChange() +"%");
	}

	public int promptUser(){
		System.out.println("Enter 1 (to add an item), 2 (to check price), 3 (to view page), 4 (to delete an item), or -1 to quit?");
		Scanner scan = new Scanner(System.in);
		return scan.nextInt();	
	}
}
