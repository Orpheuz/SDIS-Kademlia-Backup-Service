package node;

import java.net.InetAddress;

public class NodeTriplet {
	byte[] id;
	int port;
	InetAddress ip;
	NodeTriplet(byte[] iD2, int port, InetAddress ip) {
		this.id = iD2;
		this.ip = ip;
		this.port = port;
	}
}
