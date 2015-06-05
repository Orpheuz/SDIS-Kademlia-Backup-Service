package handlers;

import java.net.InetAddress;

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
		
	}

}
