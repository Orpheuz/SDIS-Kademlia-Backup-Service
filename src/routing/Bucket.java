package routing;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.TreeSet;

import node.Node;

public class Bucket {
	TreeSet<Node> nodes;
	static int K = 20;
	boolean spiltable;
	static int STALL = 3;// TODO mudar stall para remover se nao responder a
							// ping

	public Bucket(boolean split) {
		nodes = new TreeSet<Node>();
		spiltable = split;
	}

	public ArrayList<Object> insert(Node n) {
		if (nodes.contains(n)) {
			Node tmp = removeFromNodes(n);
			tmp.setSeen();
			tmp.resetStale();
			nodes.add(tmp);
		} else if (nodes.size() < K)
			nodes.add(n);
		else {
			Node stalest = null;
			for (Node tmp : nodes) {
				if (tmp.getStale() >= STALL) {
					if (stalest == null) {
						stalest = tmp;
					} else if (tmp.getStale() > stalest.getStale()) {
						stalest = tmp;
					}
				}
			}
			if (stalest != null) {
				nodes.remove(stalest);
				nodes.add(n);
			} else if (spiltable) {
				int firstDif;
				for (firstDif = 0; firstDif < 160; firstDif++) {
					byte b = 0;
					for (Node nds : nodes) {
						b ^= nds.getId()[firstDif];
					}
					if (b != 0)
						break;
				}
				Bucket b = new Bucket(false);
				byte[] base = Arrays.copyOf(nodes.first().getId(), firstDif);
				for (Node node : nodes) {
					for (int i = 0; i < base.length; i++) {
						if (!Arrays.equals(base, Arrays.copyOf(node.getId(), firstDif))) {
							b.insert(node);
							nodes.remove(node);
						}
					}
				}
				ArrayList<Object> ar= new ArrayList<Object>();
				ar.add(firstDif);
				ar.add(b);
				return ar;
			}
		}
		return null;
	}

	private Node removeFromNodes(Node n) {
		for (Node c : nodes) {
			if (c.equals(n)) {
				nodes.remove(c);
				return c;
			}
		}
		return null;
	}

	public List<Node> getNodes() {
		ArrayList<Node> l = new ArrayList<Node>();
		for (Node n : nodes)
			l.add(n);
		return l;
	}
}
