package main;

import java.io.IOException;
import java.net.InetAddress;

import listeners.WriteThread;
import message.PutChunkMessage;

public class Test {
	public static void main(String[] args) throws InterruptedException, IOException {
		
		PutChunkMessage msg = new PutChunkMessage("1", 1, 2, "cenas");
		InetAddress ip = InetAddress.getByName("192.168.2.107");
		System.out.println("vou enviar:"+msg.getMessage());
		WriteThread thread = new WriteThread(msg.getMessage(), ip, 8000);
		Thread real = new Thread(thread);
		real.start();
		
		
		
	}
}
