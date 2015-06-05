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
			System.out.println(n);
			Routing.initialize(n);
			System.out.println(Routing.getNodes());
			Node b = new Node(bid, InetAddress.getByName(args[1]), Integer.parseInt(args[2]));
			Routing.insert(b);

			Ping p = new Ping(bid, b.getIP(), b.getPort());
			Thread t = new Thread(p);
			t.start();
		}
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
