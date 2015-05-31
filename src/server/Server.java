package server;

import java.awt.List;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;
import java.util.TreeMap;

public class Server {
	public class PeerId {
		public int id;
		public InetAddress ip;
		public PeerId left;
		public PeerId right;
	}

	static int HOST_PORT = 6969;
	
	TreeMap<Integer, PeerId> ipid;

	public static void main (String args[]){
		Server s = new Server();
		try {
			s.host(HOST_PORT);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void host(int port) throws IOException {

		@SuppressWarnings("resource")
		DatagramSocket serverSocket = new DatagramSocket(port);

		ipid = new TreeMap<Integer, Server.PeerId>();

		while (true) {
			byte[] data = new byte[1024];
			DatagramPacket received = new DatagramPacket(data, data.length);
			serverSocket.receive(received);
			String message = new String(received.getData()).trim();
			System.out.println("MESSAGE RECEIVED:\n"+message);
			String ms[] = message.split(" ");
			if (ms[0].equals("JOIN")) {
				PeerId peer = new PeerId();
				System.out.println("ms[1]:"+ms[1]+":");
				if (ms[1].equals("NEW")) {
					Random r = new Random();
					peer.id = r.nextInt();
				} else {
					peer.id = Integer.parseInt(ms[1]);
				}
				peer.ip = received.getAddress();

				if (ipid.size() > 0) {
					ipid.put(peer.id, peer);
					peer.right = ipid.higherEntry(peer.id).getValue();
					peer.left =  ipid.lowerEntry(peer.id).getValue();
					peer.right.left = peer;
					peer.left.right = peer;
				} else {
					peer.left = peer;
					peer.right = peer;
				}

				String response = "NEIGHBOUR " + peer.id + " " + peer.left.ip + " " + peer.right.ip;

				DatagramPacket sent = new DatagramPacket(response.getBytes(), response.getBytes().length, peer.ip, HOST_PORT);
				serverSocket.send(sent);

				response = "NEIGHBOUR " + peer.left.id + " " + peer.left.left.ip + " " + peer.left.right.ip;
				sent = new DatagramPacket(response.getBytes(), response.getBytes().length, peer.left.ip, HOST_PORT);
				serverSocket.send(sent);
				
				response = "NEIGHBOUR " + peer.right.id + " " + peer.right.left.ip + " " + peer.right.right.ip;
				sent = new DatagramPacket(response.getBytes(), response.getBytes().length, peer.right.ip, HOST_PORT);
				serverSocket.send(sent);

			}
		}

	}

	
	public void translate(int port) {

	}
}
