package subprotocols;

import java.net.InetAddress;

import listeners.WriteThread;
import message.PingMessage;


public class Ping implements Runnable{

	private InetAddress ip;
	int port, timeout;
	byte[] id;
	
	
	public Ping(byte[] id,InetAddress ip, int port, int timeout) {
		super();
		this.ip = ip;
		this.port = port;
		this.timeout = timeout;
		this.id = id;
	}



	public InetAddress getIp() {
		return ip;
	}



	public int getPort() {
		return port;
	}



	public int getTimeout() {
		return timeout;
	}



	@Override
	public void run() {
		PingMessage message = new PingMessage(new String(id), port);
		WriteThread send = new WriteThread(message.getMessage(),ip,port);
		Thread thread = new Thread(send);
		thread.start();
		
	}

	

}
