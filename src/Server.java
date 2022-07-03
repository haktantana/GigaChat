import java.net.*;
import java.util.*;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.BorderLayout;
import java.awt.image.BufferedImage;
import java.io.*;
import java.lang.Thread;
public class Server{
	private String addr;
	private InetAddress iA;
    private ServerSocket sS;
    private int port;
    
public Server(String a,int p) {
	
	port=p;
	addr=a;
	System.out.println("trying to create Server");
	try {
		iA=InetAddress.getByName(addr);
		sS=new ServerSocket(port,30,iA);
		sS.setSoTimeout(100000000);
		System.out.println("--Server is created--");
	
	} catch (UnknownHostException e) {
	} catch (IOException e) {
	}	
	
	JFrame serverFrame = new JFrame("Server");
	serverFrame.setSize(150,150);
	serverFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	JLabel text = new JLabel("Server is active.");
	serverFrame.add(text, BorderLayout.CENTER);
	serverFrame.setVisible(true);
}
public void startServer() {
	List<Socket> sockets=new ArrayList<Socket>();
	while(true) {
		try {
				    Socket s=sS.accept();
					sockets.add(s);
				    System.out.println("--new client is connected--");
					ReadandSendThread rT=new ReadandSendThread(s,sockets);
					rT.start();
		} catch (IOException e) {
		}
	}
}
public static void main(String[] args) {
	String ip;
	JOptionPane startScreen=new JOptionPane();
	ip=startScreen.showInputDialog(null,"Enter the IP address:","GigaChat",JOptionPane.PLAIN_MESSAGE);
	
	Server server=new Server(ip,6868);
	server.startServer();
	
	
}
}
class ReadandSendThread extends Thread{
	List<Socket>sockets;
	private Socket socket;
	private BufferedReader in;
	private PrintWriter out;
	private BufferedImage bufferedImage;
	ReadandSendThread(Socket s,List<Socket>sck){
		socket=s;
	    sockets=sck;
	}
	public void run() {
		try {
			if(socket.isConnected()) {
				in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			String msg="";
				while(msg!="over") {
					msg=in.readLine();
					for(int i=0;i<sockets.size();i++) {
						out = new PrintWriter(sockets.get(i).getOutputStream(), true);
						out.println(msg);
					}
					System.out.println("\u001B[34m"+msg+"\u001B[0m");
					
					
			}}}
		 catch (IOException e) {
			 System.out.println(e);
		}
	}
}



/*class SendImage extends Thread{
	List<Socket> sockets;
	private Socket sockett;
	private BufferedImage bufferedImage;
	
	SendImage(Socket s,List<Socket>sck){
		sockett=s;
	    sockets=sck;
	}
	
	public void run() {
		try {
			InputStream inputStream = sockett.getInputStream();
			BufferedInputStream bis = new BufferedInputStream(inputStream);
			bufferedImage = ImageIO.read(bis);
			bis.close();
			sockett.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
}*/