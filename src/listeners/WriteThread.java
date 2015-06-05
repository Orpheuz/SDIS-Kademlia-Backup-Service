package listeners;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketTimeoutException;

public class WriteThread implements Runnable {

	private String message;
	private int timeout,port;
	private InetAddress ip;
	
	public WriteThread(String message ,InetAddress ip, int port) {
		this.message = message;
		this.ip = ip;
		this.port = port;
		timeout = 5000;
	}

	@Override
	public void run() {
		try {
			send();
		} catch (IOException e) {
			System.out.println("Failed to send");
		}
	}
	
	public void send() throws IOException {

		Socket socket = new Socket();
		try{
			socket.connect(new InetSocketAddress(ip,port), timeout);
			PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
			out.print(message);
			out.close();

		}catch(SocketTimeoutException e){
			System.out.println("Failed to connect to " + ip + " on port "+ port);
		}
		socket.close();

	}

}
