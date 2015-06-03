package routing;

import java.util.TreeSet;

import node.Node;

public class Bucket {
	TreeSet<Node> nodes;
	TreeSet<Node> cache;
	int depth;
	static int K = 20;
	static int STALL = 3;

	public Bucket(int depth) {
		this.depth = depth;
		nodes = new TreeSet<Node>();
		cache = new TreeSet<Node>();
	}

	public void insert(Node n) {
		if (nodes.contains(n)) {
			Node tmp=removeFromNodes(n);
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
			} else
				cache.add(n);
		}
	}

	private Node removeFromNodes(Node n) {
		 for (Node c : nodes)
	        {
	            if (c.equals(n))
	            {
	                nodes.remove(c);
	                return c;
	            }
	        }
		return null;
	}
}
