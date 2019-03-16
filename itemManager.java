package priceWatcher;

import java.sql.*;
import java.io.*;

public class itemManager{
	
	public void delete(String Url)throws ClassNotFoundException, IOException{
		String sql = "DELETE FROM items WHERE ID = ?";
		String sqlItems = "SELECT ID, Item FROM items";
		int id = 0;
		ResultSet rs = null;
		Statement stmt = null;
		storeItem i = new storeItem();
		try(Connection conn = this.connect();
			PreparedStatement pstmt = conn.prepareStatement(sql)){
			stmt = conn.createStatement();
			Item item = new Item();
			rs = stmt.executeQuery(sqlItems);
			while(rs.next()){
				id = rs.getInt(1);
				item = i.deserialize(id);
				System.out.println("ID: " + id);
				System.out.println(item.showTitle());
				String url = item.showUrl();
				if(url.equals(Url)){
					System.out.println();
					System.out.println("Item being deleted: " + item.showTitle());
					System.out.println("URL FOUND: " + item.showUrl());
					System.out.println("Price: " + item.showPrice());
					System.out.println("ID: " + id);
					pstmt.setInt(1, id);
					pstmt.executeUpdate();
					rs.close();
				}
			}
			rs.close();
		} catch (SQLException e){
			System.out.println(e.getMessage());
		}
	}

	public static void add(Item item) throws ClassNotFoundException, IOException{
		storeItem i = new storeItem();
		i.storeItem(item);
	}
	
	//returns an item with the given url
	public Item retrieve(String url)throws ClassNotFoundException, IOException{
		String sql = "SELECT ID, Item FROM items";
		int id = 0;
		ResultSet rs = null;
		storeItem i = new storeItem();
		try(Connection conn = this.connect();
			Statement stmt = conn.createStatement()){
			Item item = new Item();
			rs = stmt.executeQuery(sql);
			while(rs.next()){
				id = rs.getInt(1);
				item = i.deserialize(id);
				if(item.showUrl().equals(url)){
					rs.close();
					return item;
				}
			}
		}catch(SQLException e){
			e.printStackTrace();
		}
		return null;
	}
	
	//Returns an item given the item title. This method will be utilized with the GUI
	public Item retrieveItem(String title)throws ClassNotFoundException, IOException{
		String sql = "SELECT ID, Item FROM items";
		ResultSet rs = null;
		int id = -1;
		storeItem i = new storeItem();
		try(Connection conn = this.connect();
			Statement stmt = conn.createStatement()){
			Item item = new Item();
			rs = stmt.executeQuery(sql);
			while(rs.next()){
				id = rs.getInt(1);
				item = i.deserialize(id);
				if(title.equals(item.showTitle())){
					rs.close();
					return item;
				}
			}
		}catch(SQLException e){
			e.printStackTrace();
		}
		return null;
	}	
	
	public Connection connect(){
		String url = "jdbc:sqlite:C:\\pw.db";
		Connection conn = null;
		try{
			conn = DriverManager.getConnection(url);
		} catch (SQLException e){
			System.out.println(e.getMessage());
		}
		return conn;
	}
}
