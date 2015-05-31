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

	private int ID;
	private InetAddress IP;
	private DatagramSocket ds;
	
	public Node(int iD) throws UnknownHostException {
		super();
		ID = iD;
		IP = getMyIP();
	}

	private InetAddress getMyIP() throws UnknownHostException {
		try {
			Enumeration<NetworkInterface> b = NetworkInterface
					.getNetworkInterfaces();
			while (b.hasMoreElements()) {
				for (InterfaceAddress f : b.nextElement()
						.getInterfaceAddresses())
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

	
	public void join (InetAddress ip, int port) throws IOException
	{
		String sdata = "JOIN 1.0";
		DatagramPacket sent = new DatagramPacket(sdata.getBytes(), sdata.getBytes().length);
		sent.setAddress(ip);
		sent.setPort(port);
		ds.send(sent);
		
		byte[] rdata=new byte[1024];
		DatagramPacket received = new DatagramPacket(rdata, rdata.length);
		ds.receive(received);
		//TODO PARSAR
	}
}
