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
import java.util.Arrays;
import java.util.Enumeration;

public class Node implements Comparable<Node> {

	private byte id[] = new byte[160];
	private int port;
	private long lastSeen;
	private int staleCount;
	private InetAddress ip;
	private NodeTriplet nodeT;

	public Node(byte[] iD, int port) throws UnknownHostException {
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
		//
	}

	public static void main(String args[]) throws IOException {
		Node n = new Node();
		n.joinNew(InetAddress.getLocalHost(), 6969);
	}

	private boolean ping(InetAddress ip) {
		return false;
	}

	public NodeTriplet getNodeT() {
		return nodeT;
	}

	public void resetStale() {
		staleCount = 0;
	}

	public int getStale() {
		return staleCount;
	}

	public int distance(byte id[]) {
		int i;
		for (i = 0; i < id.length; i++) {
			if ((id[i] ^ this.id[i]) == 1)
				break;
		}
		return 160 - i;
	}

	@Override
	public int compareTo(Node o) {
		if (idHash() == o.hashCode())
			return 0;
		return lastSeen > o.lastSeen ? 1 : -1;
	}

	int idHash() {
		return Arrays.hashCode(id);
	}

	public byte[] getId() {
		return id;
	}

	public void setSeen() {
		lastSeen = System.currentTimeMillis() / 1000L;
	}

	public long getSeen() {
		return lastSeen;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Node) {
			Node n = (Node) obj;
			if (n == this) {
				return true;
			}
			return idHash() == n.hashCode();
		}
		return false;
	}

	public String toString() {
		int n=0;
		for (int i = 0; i < id.length; i++) {
			n+=id[i]*Math.pow(2, 159-i);
		}
		return ""+n;
	}

}