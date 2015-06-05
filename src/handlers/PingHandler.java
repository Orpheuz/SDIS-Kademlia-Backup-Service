package handlers;

import java.net.InetAddress;

import node.Node;
import routing.Routing;
import message.PingMessage;

public class PingHandler implements Runnable {

	PingMessage message;
	InetAddress ip;
	byte[] id;

	public PingHandler(PingMessage message, InetAddress ip, String id) {
		this.message = message;
		this.ip = ip;
		this.id = id.getBytes();
	}

	@Override
	public void run() {
		Routing.insert(new Node(id, ip, message.getPort()));
	}

}
