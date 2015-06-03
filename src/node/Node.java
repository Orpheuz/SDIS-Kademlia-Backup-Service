package node;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Enumeration;


public class Node {

	class Subnet {
		public InetAddress left, right;
		public String fileID;
		public int chunkNo;
		public int repdeg;
		public boolean last;
		public InetAddress client;
	}

	ArrayList<Subnet> subnets;

	private int id, port;
	private InetAddress ip, left, right;
	private DatagramSocket ds;
	private NodeTriplet nodeT;
	

	public Node(int iD, int port) throws UnknownHostException {
		super();
		id = iD;
		ip = getMyIP();
		this.port = port;
		this.nodeT = new NodeTriplet(iD, port, ip);
	}

	public Node() throws UnknownHostException {
		super();
		ip = getMyIP();
	}

	private InetAddress getMyIP() throws UnknownHostException {
		try {
			Enumeration<NetworkInterface> b = NetworkInterface.getNetworkInterfaces();
			while (b.hasMoreElements()) {
				for (InterfaceAddress f : b.nextElement().getInterfaceAddresses())
					if (f.getAddress().isSiteLocalAddress())
						return f.getAddress();
			}
		} catch (SocketException e) {
			e.printStackTrace();
		}
		return null;

	}

	public InetAddress getIP() {
		return ip;
	}

	public void setIP(InetAddress iP) {
		ip = iP;
	}

	public void joinNew(InetAddress ip, int port) throws IOException {
		ds = new DatagramSocket(6969);
		String sdata = "JOIN NEW";
		byte[] buf = sdata.getBytes();
		DatagramPacket sent = new DatagramPacket(buf, buf.length);
		sent.setAddress(ip);
		sent.setPort(port);
		ds.send(sent);
		System.out.println("sent " + new String(sent.getData()) + " to " + ip + " on port " + port);

		byte[] rdata = new byte[1024];
		DatagramPacket received = new DatagramPacket(rdata, rdata.length);
		ds.receive(received);
		sdata = new String(received.getData()).trim();

		System.out.println("received " + sdata + " from " + ip + " on port " + port);

		String[] split = sdata.split(" ");

		id = Integer.parseInt(split[1]);
		left = InetAddress.getByName(split[2]);
		right = InetAddress.getByName(split[3]);
	}

	public static void main(String args[]) throws IOException {
		Node n = new Node();
		n.joinNew(InetAddress.getLocalHost(), 6969);
	}

	public void checkSubNets() {
		for (int i = 0; i < subnets.size(); i++) {
			// check for mia's
			if (ping(subnets.get(i).right)) {
				//MIA
				/*
				 * 1ST SEND MIA TO SERVER
				 * 
				 * 2ND GET NODE RIGHT AND MAKE HIM A PEER
				 */
			}

			// optimize
		}
	}

	private boolean ping(InetAddress ip) {
		return false;
	}

	public NodeTriplet getNodeT() {
		return nodeT;
	}

}