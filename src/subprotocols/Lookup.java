package subprotocols;

import java.io.IOException;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.TreeSet;

import routing.Routing;
import message.FindNodeMessage;
import message.Message;
import message.Write;
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

	private List<Node> look(Node node) {
		FindNodeMessage m = new FindNodeMessage(target, K);
		try {
			Write.send(m.getMessage(), node.getIP(), node.getPort());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
