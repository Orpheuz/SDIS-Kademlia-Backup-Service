package node;

import java.net.InetAddress;
import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Enumeration;

public class Node {

	@SuppressWarnings("unused")
	private int ID;
	private InetAddress IP;

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

}
