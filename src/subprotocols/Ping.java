package subprotocols;

import java.net.InetAddress;

import routing.Routing;
import listeners.WriteThread;
import message.PingMessage;

public class Ping implements Runnable {

	private InetAddress ip;
	int port;
	byte[] id;

	public Ping(byte[] id, InetAddress ip, int port) {
		super();
		this.ip = ip;
		this.port = port;
		this.id = id;
	}

	public InetAddress getIp() {
		return ip;
	}

	public int getPort() {
		return port;
	}

	@Override
	public void run() {
		PingMessage message = new PingMessage(new String(Routing.local.getId()), Routing.local.getPort());
		WriteThread send = new WriteThread(message.getMessage(), ip, port);
		System.out.println(":"+message.getId().length()+":");
		Thread thread = new Thread(send);
		thread.start();

	}

}
