package handlers;

import message.PingMessage;

public class PingHandler implements Runnable {

	PingMessage message;
	
	public PingHandler(PingMessage message) {
		this.message = message;
	}

	@Override
	public void run() {
		
	}

}
