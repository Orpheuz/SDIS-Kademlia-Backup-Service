package message;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketTimeoutException;

public class Write extends Thread {
	private static int timeout;
	public static void run(String msg, String ip, int port) throws IOException {

		byte[] toSend = msg.getBytes();
		timeout = 3000;
		InetAddress adress = InetAddress.getByName(ip);
		Socket socket = new Socket();
		
		try{
		socket.connect(new InetSocketAddress(adress,port), timeout);
		PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
		out.print(toSend);
		//String s = in.readLine();
		out.close();
		
		}catch(SocketTimeoutException e){
			System.out.println("Failed to connect to " + ip + " on port "+ port);
		}

		//BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));


		//in.close();
		socket.close();

	}

}
