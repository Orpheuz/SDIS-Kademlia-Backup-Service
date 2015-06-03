package node;

import java.net.InetAddress;

public class NodeTriplet {
	int id, port;
	InetAddress ip;
	NodeTriplet(int id, int port, InetAddress ip) {
		this.id = id;
		this.ip = ip;
		this.port = port;
	}
}
