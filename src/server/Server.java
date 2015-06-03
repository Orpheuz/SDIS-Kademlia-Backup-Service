package server;

import java.awt.List;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;
import java.util.TreeMap;

public class Server {
	static int HOST_PORT = 6969;
	static int TRANSLATE_PORT = 6699;

	TreeSet<Integer, InetAddress> ipid;

	public static void main(String args[]) {
		Server s = new Server();
		try {
			s.host(HOST_PORT);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void host(int port) throws IOException {
		// TODO por os peers que saem
		@SuppressWarnings("resource")
		DatagramSocket serverSocket = new DatagramSocket(port);
		ipid = new TreeSet<Integer, InetAddress>();

		while (true) {
			message.Message ms;
			
			if (ms[0].equals("JOIN")) {
				int id;
				if (ms[1].equals("NEW")) {
					Random r = new Random();
					id = r.nextInt();
				} else {
					id = Integer.parseInt(ms[1]);
				}

				ipid.put(id, received.getAddress());

				
			}
		}

	}

	public void translate(int port) throws IOException {

		@SuppressWarnings("resource")
		DatagramSocket serverSocket = new DatagramSocket(port);
		while (true) {
			byte[] data = new byte[1024];
			DatagramPacket received = new DatagramPacket(data, data.length);
			serverSocket.receive(received);
			String message = new String(received.getData()).trim();
			String ms[] = message.split(" ");
			if (ms[0].equals("TRANSLATE")) {
				String response = "TRANSLATED " + ipid.ceilingKey(Integer.parseInt(ms[1]));
				DatagramPacket sent = new DatagramPacket(response.getBytes(), response.getBytes().length, received.getAddress(), TRANSLATE_PORT);
				serverSocket.send(sent);
			}
		}
	}

	public void mia(int port) {

	}
}
