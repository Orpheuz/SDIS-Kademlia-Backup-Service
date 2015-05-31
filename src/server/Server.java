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
		ipid = new TreeMap<Integer, Server.PeerId>();

		DatagramSocket serverSocket = new DatagramSocket(port);
		byte[] data = new byte[1024];
		while (true) {
			DatagramPacket received = new DatagramPacket(data, data.length);
			serverSocket.receive(received);
			String message = received.getData().toString();
			String ms[] = message.split(" ");
			if (ms[0].equals("JOIN")) {
				if (ms[1].equals("NEW")) {
					
					Random r = new Random();
					
					PeerId peer = new PeerId();
					peer.id = r.nextInt();
					peer.active = true;
					peer.ip = received.getAddress();
					if (ipid.size() > 0) {
						ipid.put(peer.id, peer);
						ipid.get(peer.id).right=ipid.higherEntry(peer.id).getValue();
						ipid.get(peer.id).right=ipid.higherEntry(peer.id).getValue();
					}
					else {
						peer.left = peer;
						peer.right = peer;
					}
					
				} else {

				}
				// TODO DIZER VIZINHOS
				// INFORMAR VIZINHOS
			}
		}

	}

	
	PeerId leftActive(int id){
		int n=id;
		PeerId p;
		do {
			p = ipid.higherEntry(n).getValue();
			n=p.id;
		} while (!p.active);
		return p;
	}
}
