package main;

import java.io.IOException;
import java.net.InetAddress;
import java.util.Random;

import javax.swing.plaf.TextUI;

import routing.Routing;
import subprotocols.Ping;
import tui.TextInterface;
import node.Node;
import listeners.ReceivingThread;
import listeners.WriteThread;

public class Main {
	public static int myPort;

	public static void main(String[] args) throws InterruptedException, IOException, ClassNotFoundException {
		// byte[] bid = new byte[160];
		// for (int i = 0; i < bid.length; i++)
		// bid[i] = 0;
		// Ping p = new Ping(bid, InetAddress.getLocalHost(), 8000);
		// Thread t = new Thread(p);
		// t.start();
		byte[] id = new byte[160];
		if (args[0].equals("bootstraper")) {
			for (int i = 0; i < id.length; i++)
				id[i] = 0;
			int port = Integer.parseInt(args[1]);
			Node n = new Node(id, port);
			Routing.initialize(n);
//			byte[] bid = new byte[160];
//			for (int i = 0; i < 160; i++)
//				bid[i] = 0;
//			bid[159] = 1;
//			Routing.insert(b);
			
		} else {
			byte[] bid = new byte[160];
			for (int i = 0; i < id.length; i++)
				bid[i] = 0;
			for (int i = 0; i < id.length; i++)
			{
					id[i] = 0;
			}
			id[2] = 1;
			int port = Integer.parseInt(args[0]);
			myPort = port;
			Node n = new Node(id, port);
			Routing.initialize(n);
			Node b = new Node(bid, InetAddress.getByName(args[1]), Integer.parseInt(args[2]));
			Routing.insert(b);

			Ping p = new Ping(bid, b.getIP(), b.getPort());
			Thread t = new Thread(p);
			t.start();
		}

		byte[] id1 = new byte[160];
		for (int i = 0; i < id1.length; i++)
			id1[i] = 0;
		id1[159]=1;
		
		byte[] id2 = new byte[160];
		for (int i = 0; i < id1.length; i++)
			id2[i] = 0;

		id2[158]=1;
		
		byte[] id3 = new byte[160];
		for (int i = 0; i < id1.length; i++)
			id3[i] = 0;

		id3[158]=1;
		id3[159]=1;
		
		byte[] id4 = new byte[160];
		for (int i = 0; i < id1.length; i++)
			id4[i] = 0;

		id4[157]=1;
		
		byte[] id5= new byte[160];
		for (int i = 0; i < id1.length; i++)
			id5[i] = 0;

		id5[157]=1;
		id5[159]=1;
		
		Node n1 = new Node(id1, InetAddress.getByName("192.168.2.134"), 8000);
		Node n2 = new Node(id2, InetAddress.getByName("192.168.2.146"), 8000);
		Node n4 = new Node(id4, InetAddress.getByName("192.168.34.154"), 8000);
		Node n3 = new Node(id3, InetAddress.getByName("192.168.34.146"), 8000);
		Node n5 = new Node(id5, InetAddress.getByName("192.168.34.147"), 8000);
		

		Routing.insert(n1);
//		Routing.insert(n2);
//		Routing.insert(n4);
//		Routing.insert(n3);
//		Routing.insert(n5);
		
		ReceivingThread thread;
		if (args[0].equals("bootstraper"))
			thread = new ReceivingThread(Integer.parseInt(args[1]));
		else
			thread = new ReceivingThread(Integer.parseInt(args[0]));
		Thread real = new Thread(thread);
		real.start();
		//
		TextInterface.main(null);

	}
}
