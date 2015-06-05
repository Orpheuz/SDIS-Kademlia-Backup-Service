package handlers;

import java.io.IOException;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;

import node.Node;
import node.NodeTriplet;
import routing.Routing;
import subprotocols.Lookup;
import listeners.WriteThread;
import message.FindNodeMessage;
import message.FindNodeResponse;

public class FindNodeHandler implements Runnable {

	private byte[] targetid;
	private int n, port;
	InetAddress ip;
	boolean type;
	ArrayList<Node> nodes;

	public FindNodeHandler(boolean type, FindNodeMessage message, InetAddress ip, int port) {
		this.type = type;
		if (type) {
			nodes = null;
			// TODO LER O TARGET
		} else {
			targetid = null;
			// TODO LER OS NOS
		}
	}

	@Override
	public void run() {
		if (type) {
			List<Node> l = Routing.findClosest(targetid, n);
			ArrayList<NodeTriplet> al = new ArrayList<NodeTriplet>();
			for (Node node : l) {
				al.add(new NodeTriplet(node.getId(), node.getPort(), node.getIP()));
			}
			FindNodeResponse message = new FindNodeResponse(al);
			WriteThread wt = new WriteThread(message.getMessage(), ip, port);
			Thread t = new Thread(wt);
			t.start();
		} else {
			if (Lookup.listening)
				Lookup.looked = nodes;
		}
	}
}
