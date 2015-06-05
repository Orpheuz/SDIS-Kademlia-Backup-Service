package main;

import java.io.IOException;

import message.Message;
import message.Parser;
import message.PutChunkMessage;
import message.Write;

public class Main {

	public static void main(String[] args) throws InterruptedException, IOException {

		// 0 sender 1 receiver
		/*
		int choice = Integer.parseInt(args[0]);
		Node node = new Node(1);
		System.out.println(node.getIP());

		DatagramSocket socket = new DatagramSocket(8000);

		if (choice == 1) {
			String msg = null;
			Message message = new Message();
			while (msg == null) {
				msg = message.receive(socket);

			}
			System.out.println(msg);
		} else {
			for (int i = 0; i < 5; i++) {
				Message message = new Message();
				message.send("Merda".getBytes(), socket, InetAddress.getByName("188.83.241.69"), 8000);
				System.out.println("send");
				Thread.sleep(1000);
			}
		}*/
		
		PutChunkMessage test = new PutChunkMessage("1", 2, 3);
		byte[] tst = test.getMessage().getBytes();
		
		Parser p = new Parser(tst);
		
		PutChunkMessage msg = (PutChunkMessage) p.parseMessage();
		System.out.println(msg.getMessage());
		
		
		
	}
}
