package priceWatcher;
import java.util.Scanner;
import java.awt.*;
import java.awt.event.*;
import java.net.*;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;
import javax.swing.*;

@SuppressWarnings("serial")
public class Main extends JFrame{
//I will be implementing the GUI soon 03/15/2019
/*
	private JLabel msgBar = new JLabel(" ");
	private JTextField text;
	private viewItem vi;
	private JPanel board;
	private final static Dimension DEFAULT_SIZE = new Dimension(400, 300);


	public Main(){
		this(DEFAULT_SIZE);
	}

	public Main(Dimension dim){
		super("Price Watcher");
		setSize(dim);
		configureUI();
	
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setVisible(true);

		showMessage("Welcome!");
	}

	public void addButtonClicked(ActionEvent event){
		String url = text.getText();	
		try{
			priceFinder p = new priceFinder();
			String price = p.priceFinder(url);
			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
			LocalDateTime now = LocalDateTime.now();
			itemManager i = new itemManager();	
			String date = dtf.format(now);
			String title = p.getTitle(url);
			Item items = new Item();
			items.Item(price,title,url,date);
                	i.add(items);
			text.setText("");
		}catch(MalformedURLException e){
		}catch(IOException e1){
		}catch(ClassNotFoundException e2){
		}

		board.revalidate();
		showMessage("You have added an Item!");
	}

	public void deleteButtonClicked(ActionEvent event){

	}

	public void refreshButtonClicked(ActionEvent event){

	}
	
	public void viewPageClicked(){
		
	}
	private void configureUI(){
		setLayout(new BorderLayout());
		JPanel control = makeControlPanel();
		control.setBorder(BorderFactory.createEmptyBorder(10,16,0,16));
		add(control, BorderLayout.NORTH);
		JPanel board = new JPanel();
		board.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createEmptyBorder(10,16,0,16),
				BorderFactory.createLineBorder(Color.GRAY)));
		board.setLayout(new GridLayout(1,1));
		Item item = new Item();
		vi = new viewItem(item);
		vi.setClickListener(this::viewPageClicked);
		board.add(vi);
		listView lv = new listView();
		board.add(lv);
		add(board, BorderLayout.CENTER);
		msgBar.setBorder(BorderFactory.createEmptyBorder(10,16,10,0));
		add(msgBar, BorderLayout.SOUTH);		
	}
*/
	public static void run() throws ClassNotFoundException, IOException{
		Item item = new Item();
		ConsoleUI ui = new ConsoleUI(item);
		ui.showWelcome();
		Scanner scan = new Scanner(System.in);
		priceFinder p = new priceFinder();
		String url = null;
		int request = -1;
		String price = null;
		Desktop d = Desktop.getDesktop();
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
		LocalDateTime now = LocalDateTime.now();
		itemManager i = new itemManager();
	
		do{
			request = ui.promptUser();
			switch(request){
				case 1:
					System.out.print("Please type in the URL: ");
					url = scan.nextLine();
					price = p.priceFinder(url);
					String date = dtf.format(now);
					String title = p.getTitle(url);
					Item items = new Item();
					items.Item(price,title,url,date);
					i.add(items);
					ConsoleUI u = new ConsoleUI(items);
					u.showItem();
					break;
				case 2: 
					System.out.print("Please type in the URL for the Item: ");
					url = scan.nextLine();
					item = i.retrieve(url);
					System.out.println("Original Price: " + item.showPrice());
					price = p.priceFinder(url);
					System.out.println("Current Price: " + price);
					break;
				case 3: 
					
					System.out.print("Please type in the URL: ");
					url = scan.nextLine();
					try{
						d.browse(new URI(url));
					} catch (IOException | URISyntaxException  e){
						e.printStackTrace();
					}
					break;
				case 4:
					System.out.print("Please type in the URL: ");
					url = scan.nextLine();
					i.delete(url);
					break;
			}
		}while(request != -1);	
	}
/*
	private JPanel makeControlPanel(){
		JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEADING));
		JButton refreshButton = new JButton("Refresh");
		refreshButton.setFocusPainted(false);
		refreshButton.addActionListener(this::refreshButtonClicked);
		panel.add(refreshButton);
		
		text = new JTextField(10);
		panel.add(text);

		JButton addButton = new JButton("Add Item");
		addButton.setFocusPainted(false);
		addButton.addActionListener(this::addButtonClicked);
		panel.add(addButton, BorderLayout.SOUTH);

		JButton deleteButton = new JButton("Delete Item");
		deleteButton.setFocusPainted(false);
		deleteButton.addActionListener(this::deleteButtonClicked);
		panel.add(deleteButton);
	
		return panel;
	}
	
	private void showMessage(String msg){
		msgBar.setText(msg);
		new Thread(() -> {
			try{
				Thread.sleep(3 * 1000);
			}catch(InterruptedException e){
			}
			if (msg.equals(msgBar.getText())){
				SwingUtilities.invokeLater(() -> msgBar.setText(" "));
			}
		}).start();
	
	}
*/
	public static void main(String[] args) throws ClassNotFoundException, IOException{
		Main.run();
	}
}
