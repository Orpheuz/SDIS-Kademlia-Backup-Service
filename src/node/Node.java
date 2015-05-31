package node;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Enumeration;

public class Node {

	private int id;
	private InetAddress IP, left, right;
	private DatagramSocket ds;

	public Node(int iD) throws UnknownHostException {
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
}
