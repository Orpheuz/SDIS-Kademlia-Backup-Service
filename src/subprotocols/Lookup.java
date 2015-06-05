package subprotocols;

import java.io.IOException;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.TreeSet;

import routing.Routing;
import listeners.WriteThread;
import message.FindNodeMessage;
import message.FindNodeResponse;
import message.Message;
import node.IdComparer;
import node.Node;

public class Lookup {

	static final int K = 3;

	byte[] target;
	TreeSet<Node> nodes;

	public Lookup(byte[] target) {
		this.target = target;
		nodes = new TreeSet<Node>(new IdComparer(target));
	}

	Node run() {
		List<Node> ln = Routing.findClosest(target, K);
		run(ln);
		return nodes.first();
	}

	private void run(List<Node> ln) {
		for (Node node : ln) {
			if (Arrays.equals(node.getId(), target)) {
				nodes.clear();
				nodes.add(node);
				return;
			}
			if (nodes.lower(node) != null) {
				nodes.add(node);
				List<Node> answ = look(node);
				if (answ != null)
					run(answ);
			}
		}
	}

	
	//TODO meter a ir buscar os nodes
	private List<Node> look(Node node) {
		FindNodeMessage m = new FindNodeMessage(target, K);
		WriteThread wt = new WriteThread(m.getMessage(), node.getIP(), node.getPort());
		Thread t = new Thread(wt);
		t.start();
		
		//falta fazer esta parte
		return null;
	}
}
