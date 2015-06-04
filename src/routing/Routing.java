package routing;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.TreeMap;
import java.util.TreeSet;

import node.IdComparer;
import node.Node;

public class Routing {
	Node local;
	TreeMap<Integer, Bucket> buckets;

	public Routing(Node local) {
		this.local = local;
		buckets = new TreeMap<Integer, Bucket>();
		buckets.put(160, new Bucket(160));
	}

	public int getbucket(Node p) {
		return local.distance(p.getId()) - 1;
	}

	public void insert(Node p) {
		int n = getbucket(p);
		if (buckets.containsKey(n))
			buckets.get(n).insert(p);
		else if (buckets.firstKey() >= n)
			buckets.firstEntry().getValue().insert(p);
		else{
		buckets.put(n, new Bucket(n));
		buckets.get(n).insert(p);
		}
		// else
		// buckets.put(n, value);
	}

	public synchronized final List<Node> findClosest(byte[] target, int numNodesRequired) {
		TreeSet<Node> sortedSet = new TreeSet<>(new IdComparer(target));
		sortedSet.addAll(this.getNodes());

		List<Node> closest = new ArrayList<>(numNodesRequired);

		/* Now we have the sorted set, lets get the top numRequired */
		int count = 0;
		for (Node n : sortedSet) {
			closest.add(n);
			if (++count == numNodesRequired) {
				break;
			}
		}
		return closest;
	}

	public synchronized final List<Node> getNodes() {
		List<Node> nodes = new ArrayList<>();
		Collection<Bucket> c = buckets.values();
		for (Bucket b : c)
			for (Node n : b.getNodes())
				nodes.add(n);

		return nodes;
	}
}
