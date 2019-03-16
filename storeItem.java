package priceWatcher;
import java.sql.*;
import java.io.*;

public class storeItem implements Serializable{
	private static String user;	
	private int serialized_id;
	private static final long serialVersionUID = 1L;
	public static void createTable(){
		String url = "jdbc:sqlite:C:\\pw.db";
		String sql = "CREATE TABLE IF NOT EXISTS items (\n"
			+ " ID integer PRIMARY KEY, \n"
			+ " Title text NOT NULL UNIQUE, \n"
			+ " Item blob NOT NULL,\n"
			+ " UNIQUE (Title, Item)\n"
			+ ");";
		
		try (Connection conn = DriverManager.getConnection(url);
			Statement stmt = conn.createStatement()){
			stmt.execute(sql);
		}catch (SQLException e){
			e.printStackTrace();
		}
	}

	private Connection connect() throws ClassNotFoundException{
		Class.forName("org.sqlite.JDBC");
		String url = "jdbc:sqlite:C:\\pw.db";
		Connection conn = null;
		try{
			conn = DriverManager.getConnection(url);
		} catch (SQLException e){
			e.printStackTrace();
		}
		return conn;
	}
		
	public void storeItem(Item item) throws ClassNotFoundException, IOException{
		createTable();
		String sql = "INSERT INTO items(Title, Item) VALUES(?,?)";
		try (Connection conn = this.connect();
			PreparedStatement pstmt = conn.prepareStatement(sql)){
			pstmt.setString(1, item.showTitle());
			pstmt.setBytes(2, getBytes(item));
			ResultSet rs = pstmt.getGeneratedKeys();
			serialized_id = -1;
			if (rs.next()){
				serialized_id = rs.getInt(1);
			}
			rs.close();
			pstmt.executeUpdate();
			System.out.println("Java object serialized to database");	
		}catch (SQLException e){
			e.printStackTrace();
		}
	}

	public Item deserialize(int id) throws ClassNotFoundException, IOException{
		Item item = new Item();
		String sql = "SELECT Item FROM items WHERE ID = ?";
		try(Connection conn = this.connect();
			PreparedStatement pstmt = conn.prepareStatement(sql)){
			pstmt.setLong(1, id);
			ResultSet rs = pstmt.executeQuery();
			rs.next();
			byte[] buf = rs.getBytes(1);
			ObjectInputStream objectIn = null;
			if(buf != null){
				objectIn = new ObjectInputStream(new ByteArrayInputStream(buf));
			}
			Object deserializedItem = objectIn.readObject();
			rs.close();
			System.out.println("Java de-serialized from database. Object: " + deserializedItem);
			item = (Item)deserializedItem;	
		}catch(SQLException e){
			e.getMessage();
		}
		return item;
	}	

	public static byte[] getBytes(Item item) throws IOException, ClassNotFoundException{
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		ObjectOutputStream oos = new ObjectOutputStream(bos);
		Object object = (Object)item;
		oos.writeObject(object);
		oos.flush();
		oos.close();
		bos.close();
		byte[] data = bos.toByteArray();
		return data;
	}
}
