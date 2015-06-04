package main;

import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

import message.Message;
import node.Node;

public class Main {

	public static void main(String[] args) throws UnknownHostException,
			SocketException, InterruptedException {

		// 0 sender 1 receiver
//		int choice = Integer.parseInt(args[0]);
//		Node node = new Node(1);
//		System.out.println(node.getIP());
//
//		DatagramSocket socket = new DatagramSocket(8000);
//
//		if (choice == 1) {
//			String msg = null;
//			Message message = new Message();
//			while (msg == null) {
//				msg = message.receive(socket);
//
//			}
//			System.out.println(msg);
//		} else {
//			for (int i = 0; i < 5; i++) {
//				Message message = new Message();
//				message.send("Merda".getBytes(), socket, InetAddress.getByName("188.83.241.69"), 8000);
//				System.out.println("send");
//				Thread.sleep(1000);
//			}
//		}
	}
}
