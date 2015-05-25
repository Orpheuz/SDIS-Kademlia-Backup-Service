package main;

import java.net.DatagramSocket;
import java.net.UnknownHostException;

import message.Message;
import node.Node;

public class Main {

	public static void main(String[] args) throws UnknownHostException {

		// 0 sender 1 receiver
		int choice = Integer.parseInt(args[1]);
		Node node = new Node(1);
		System.out.println(node.getIP());

		// DatagramSocket socket = new DatagramSocket(port, laddr);

		if (choice == 1) {
			String msg = null;
			while (msg == null) {
				Message message = new Message();
				// message.receive(socket);
			}

		} else {
			for (int i = 0; i < 5; i++) {
				Message message = new Message();
				//message.send("Merda".getBytes(), socket);
			}
		}
	}
}
