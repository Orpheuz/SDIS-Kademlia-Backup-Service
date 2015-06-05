package message;

import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;

import node.Node;
import node.NodeTriplet;

public class FindNodeResponse extends Message {
	public ArrayList<Node> nodes;
	String body;

	public FindNodeResponse(ArrayList<NodeTriplet> nodes) {
		for (NodeTriplet nodeTriplet : nodes) {
			nodes.add(new NodeTriplet(nodeTriplet.getId(), nodeTriplet.getPort(), nodeTriplet.getIp()));
		}
		body = "";
		for (int i = 0; i < nodes.size(); i++) {
			try {
				body += "IP:" + nodes.get(i).getIp() + ",Port:" + nodes.get(i).getPort() + ",ID:" + new String(nodes.get(i).getId(), "UTF-8");
			} catch (UnsupportedEncodingException e) {
				System.out.println("Failed to convert byte array to string");
			}

			if (i != nodes.size() - 1)
				body += "_";
		}

		message = Message.FINDNODE_RSP + Message.SEPARATOR + Message.CRLF + Message.CRLF + body;
	}

	public FindNodeResponse(String body) {
		message = Message.FINDNODE_RSP + Message.SEPARATOR + Message.CRLF + Message.CRLF + body;
		nodes = new ArrayList<Node>();
		String[] arr = body.split("_");
		for (String s : arr) {
			byte[] id;
			int port;
			InetAddress ip = null;
			String[] arr2 = s.split(",");

			try {
				ip = InetAddress.getByName(arr2[0].substring(arr2[0].indexOf(':') - 1, arr2[0].length() - 1));
			} catch (UnknownHostException e) {
				System.out.println("Failed to get ip in parser");
			}
			port = Integer.parseInt(arr2[1].substring(arr2[1].indexOf(':') - 1, arr2[1].length() - 1));
			id = arr2[0].substring(arr2[0].indexOf(':') - 1, arr2[0].length() - 1).getBytes();
			nodes.add(new Node(id, ip, port));

		}
	}
}
