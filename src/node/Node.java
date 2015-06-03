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

	private InetAddress IP;

	public Node(byte[] iD) throws UnknownHostException {
		super();
		id = iD;
		IP = getMyIP();
	}

	public Node() throws UnknownHostException {
		super();
		IP = getMyIP();
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
		return IP;
	}

	public void setIP(InetAddress iP) {
		IP = iP;
	}

	public void joinNew(InetAddress ip, int port) throws IOException {
		String sdata = "JOIN NEW";
		byte[] buf = sdata.getBytes();

		String[] split = sdata.split(" ");
	}

	public static void main(String args[]) throws IOException {
		Node n = new Node();
		n.joinNew(InetAddress.getLocalHost(), 6969);
	}

	private boolean ping(InetAddress ip) {
		return false;
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
		;
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

}