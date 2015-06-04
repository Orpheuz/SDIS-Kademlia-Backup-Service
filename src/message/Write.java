package message;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketTimeoutException;

public class Write {
	private static int timeout;

	public static String send(String msg, String ip, int port) throws IOException{
		return send(msg,ip,port,0);
	}
	public static String send(String msg, String ip, int port, int timeInterval) throws IOException {

		byte[] toSend = msg.getBytes();
		timeout = 3000;
		InetAddress adress = InetAddress.getByName(ip);
		Socket socket = new Socket();
		String response = "";
		try{
			socket.connect(new InetSocketAddress(adress,port), timeout);
			PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
			out.print(toSend);
			BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			response = in.readLine();
			out.close();
			in.close();

		}catch(SocketTimeoutException e){
			System.out.println("Failed to connect to " + ip + " on port "+ port);
		}
		socket.close();
		return response;

	}

}
