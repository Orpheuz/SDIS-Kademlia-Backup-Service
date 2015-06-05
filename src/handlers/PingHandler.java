package handlers;

import java.net.InetAddress;

import node.Node;
import routing.Routing;
import message.PingMessage;

public class PingHandler implements Runnable {

	PingMessage message;
	InetAddress ip;

	public PingHandler(PingMessage message, InetAddress ip) {
		this.message = message;
		this.ip = ip;
	}

	@Override
	public void run() {
		Routing.insert(new Node(message.getId().getBytes(), ip, message.getPort()));
	}

}
