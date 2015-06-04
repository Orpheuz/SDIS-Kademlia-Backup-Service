package routing;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

import node.Node;

public class Bucket {
	TreeSet<Node> nodes;
	static int K = 20;
	static int STALL = 3;//TODO mudar stall para remover se nao responder a ping

	public Bucket(int depth) {
		nodes = new TreeSet<Node>();
	}

	public Bucket insert(Node n) {
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
			}
			else{
				//TODO split
				//encontrar 1ª dif
				//expulsar igual a local
				//criar novo bucket com novos
				//retorna lo
			}
		}
		return null;
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

	public List<Node> getNodes() {
		ArrayList<Node> l=new ArrayList<Node>();
		for(Node n:nodes)
			l.add(n);
		return l;
	}
}
