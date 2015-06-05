package main;

import java.io.IOException;
import java.net.InetAddress;
import java.util.Random;

import javax.swing.plaf.TextUI;

import routing.Routing;
import tui.TextInterface;
import node.Node;
import listeners.ReceivingThread;

public class Main {
	public static int myPort;
	public static void main(String[] args) throws InterruptedException, IOException, ClassNotFoundException {

		byte[] id = new byte[160];
		Random r = new Random();
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
				if (r.nextBoolean())
					id[i] = 1;
				else
					id[i] = 0;
			int port = Integer.parseInt(args[0]);
			myPort = port;
			Node n = new Node(id, port);
			Routing.initialize(n);
			Node b = new Node(bid, InetAddress.getByName(args[2]), Integer.parseInt(args[3]));
			Routing.insert(b);
		}

		 ReceivingThread thread = new ReceivingThread(Integer.parseInt(args[1]));
		 Thread real = new Thread(thread);
		 real.start();
		//
		TextInterface.main(null);

	}
}
