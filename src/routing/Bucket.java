package routing;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.TreeSet;

import node.Node;

public class Bucket {
	TreeSet<Node> nodes;
	static int K = 2;
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
				nodes.add(n);
				int firstDif;
				for (firstDif = 0; firstDif < 160; firstDif++) {
					byte b = 0;
					for (Node nds : nodes) {
						b ^= nds.getId()[firstDif];
					}
					if (b != 0)
						break;
				}
				Bucket newb = new Bucket(false);
				Bucket oldb = new Bucket(false);
				byte[] base = Arrays.copyOf(nodes.first().getId(), firstDif + 1);
				for (Node node : nodes) {
					if (!Arrays.equals(base, Arrays.copyOf(node.getId(), firstDif+1))) {
						newb.insert(node);
						nodes.remove(node);
					}
					else
						oldb.insert(node);
				}
				this.nodes=oldb.nodes;
				ArrayList<Object> ar = new ArrayList<Object>();
				ar.add(firstDif);
				ar.add(newb);
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

	public String toString() {
		String str = "";
		for (Node node : nodes) {
			str += node.toString() + ",";
		}
		return str;
	}
}
