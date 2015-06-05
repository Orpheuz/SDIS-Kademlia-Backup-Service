package subprotocols;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.TreeSet;

import routing.Routing;
import listeners.WriteThread;
import message.FindNodeMessage;
import node.IdComparer;
import node.Node;

public class Lookup {

	static final int K = 3;
	static public boolean listening = false;
	static public ArrayList<Node> looked = null;
	byte[] target;
	TreeSet<Node> nodes;

	public Lookup(byte[] target) {
		this.target = target;
		nodes = new TreeSet<Node>(new IdComparer(target));
	}

	Node run() {
		List<Node> ln = Routing.findClosest(target, K);
		run(new ArrayList<Node>(ln));
		return nodes.first();
	}

	private void run(ArrayList<Node> ln) {
		for (Node node : ln) {
			if (Arrays.equals(node.getId(), target)) {
				nodes.clear();
				nodes.add(node);
				return;
			}
			if (nodes.lower(node) != null) {
				nodes.add(node);
				ArrayList<Node> answ = look(node);
				if (answ != null)
					run(answ);
			}
		}
	}

	private ArrayList<Node> look(Node node) {
		FindNodeMessage m = new FindNodeMessage(target, K);
		WriteThread wt = new WriteThread(m.getMessage(), node.getIP(), node.getPort());
		Thread t = new Thread(wt);
		t.start();

		int n = 0;
		while (looked == null || n < 5) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			n++;
		}
		if (looked == null)
			return null;
		ArrayList<Node> a = (ArrayList<Node>) looked.clone();
		looked = null;
		return a;
	}
}
