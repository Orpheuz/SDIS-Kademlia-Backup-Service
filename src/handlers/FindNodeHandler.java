package handlers;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;

import listeners.WriteThread;
import message.FindNodeMessage;
import message.FindNodeResponse;
import node.Node;
import node.NodeTriplet;
import routing.Routing;

public class FindNodeHandler implements Runnable {

	private byte[] targetid;
	private int n, port;
	InetAddress ip;
	boolean type;
	List<Node> nodes;

	public FindNodeHandler(FindNodeMessage message, InetAddress ip, int port) {
		this.type=type;
		if(type){
			nodes=null;
			//TODO LER O TARGET
		}
		else{
			targetid=null;
			//TODO LER OS NOS
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
		}
		else{
			//TODO fazer a parte de ver os lookupes
		}
	}

}
