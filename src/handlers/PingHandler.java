package handlers;

import java.net.InetAddress;

import node.Node;
import routing.Routing;
import message.PingMessage;

public class PingHandler implements Runnable {

	PingMessage message;
	InetAddress ip;
	byte[] id;

	public PingHandler(PingMessage message, InetAddress ip) {
		this.message = message;
		this.ip = ip;
	}

	@Override
	public void run() {
		System.out.println("asd"+message.getId().length());
		System.out.println("asd"+message.getPort());
		Routing.insert(new Node(message.getId().getBytes(), ip, message.getPort()));
	}

}
