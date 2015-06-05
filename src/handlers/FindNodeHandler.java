package handlers;

import java.io.IOException;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;

import node.Node;
import node.NodeTriplet;
import routing.Routing;
import message.FindNodeMessage;
import message.FindNodeResponse;
import message.Write;

public class FindNodeHandler implements Runnable {

	boolean type;
	private byte[] targetid;
	private int n, port;
	InetAddress ip;

	public FindNodeHandler(boolean type, FindNodeMessage message, InetAddress ip, int port) {
		// TODO PARSAR A MENSAGEM E POR O TARGET NO SITIO
	}

	@Override
	public void run() {
		List<Node> l = Routing.findClosest(targetid, n);
		ArrayList<NodeTriplet> al = new ArrayList<NodeTriplet>();
		for (Node node : l) {
			al.add(new NodeTriplet(node.getId(), node.getPort(), node.getIP()));
		}
		FindNodeResponse message = new FindNodeResponse(al);
		try {
			Write.send(message.getMessage(), ip, port);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
