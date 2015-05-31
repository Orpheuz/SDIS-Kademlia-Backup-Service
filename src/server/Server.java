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
		public boolean active;
		public PeerId left;
		public PeerId right;
	}

	TreeMap<Integer, PeerId> ipid;

	public void host(int port) throws IOException {

		@SuppressWarnings("resource")
		DatagramSocket serverSocket = new DatagramSocket(port);
		
		ipid = new TreeMap<Integer, Server.PeerId>();
		
		while (true) {
			byte[] data = new byte[1024];
			DatagramPacket received = new DatagramPacket(data, data.length);
			serverSocket.receive(received);
			String message = received.getData().toString();
			String ms[] = message.split(" ");
			if (ms[0].equals("JOIN")) {
				PeerId peer = new PeerId();
				if (ms[1].equals("NEW")) {
					Random r = new Random();
					peer.id = r.nextInt();
					peer.active = true;
					peer.ip = received.getAddress();
				}

				if (ipid.size() > 0) {
					ipid.put(peer.id, peer);
					peer.right = rightActive(peer.id);
					peer.left = leftActive(peer.id);
					peer.right.left = ipid.get(peer.id);
					peer.left.right = ipid.get(peer.id);
				} else {
					peer.left = peer;
					peer.right = peer;
				}
				
				//TODO responder
				
			}
		}

	}

	PeerId leftActive(int id) {
		int n = id;
		PeerId p;
		do {
			p = ipid.higherEntry(n).getValue();
			n = p.id;
		} while (!p.active);
		return p;
	}

	PeerId rightActive(int id) {
		int n = id;
		PeerId p;
		do {
			p = ipid.lowerEntry(n).getValue();
			n = p.id;
		} while (!p.active);
		return p;
	}
}
