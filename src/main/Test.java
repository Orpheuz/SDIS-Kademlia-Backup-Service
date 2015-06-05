package main;

import java.io.IOException;
import java.net.InetAddress;

import listeners.WriteThread;
import message.PutChunkMessage;
import message.RestoreMessage;

public class Test {
	public static void main(String[] args) throws InterruptedException, IOException {
		
		RestoreMessage msg = new RestoreMessage(1, "file", 8000);
		InetAddress ip = InetAddress.getByName("127.0.0.1");
		System.out.println("vou enviar:"+msg.getMessage());
		WriteThread thread = new WriteThread(msg.getMessage(), ip, 8000);
		Thread real = new Thread(thread);
		real.start();
		
		
		
	}
}
