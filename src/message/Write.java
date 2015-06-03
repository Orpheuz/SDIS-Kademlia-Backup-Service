package message;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

public class Write extends Thread {

	public static void run(String msg, String ip, int port) throws IOException {

		byte[] toSend = msg.getBytes();
		InetAddress adress = InetAddress.getByName(ip);
		Socket socket = new Socket(adress, port);
		

		PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
		//BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));


		out.print(toSend);
		//String s = in.readLine();
		out.close();
		//in.close();
		socket.close();

	}

}
