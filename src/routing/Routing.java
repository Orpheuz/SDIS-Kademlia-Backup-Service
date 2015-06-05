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
	static Node local; 
	static TreeMap<Integer, Bucket> buckets;

	static public void initialize(Node local) {
		Routing.local = local;
		buckets = new TreeMap<Integer, Bucket>();
		buckets.put(160, new Bucket(true));
		buckets.get(160).insert(local);
	}

	
	static public int getbucket(Node p) {
		return local.distance(p.getId()) - 1;
	}

	static public void insert(Node p) {
		int n = getbucket(p);
		ArrayList<Object> ar = null;
		Bucket b = null;
		if (buckets.containsKey(n)) {
			b = buckets.get(n);
			ar = buckets.get(n).insert(p);
		} else if (buckets.firstKey() >= n) {
			b = buckets.firstEntry().getValue();
			ar = buckets.firstEntry().getValue().insert(p);
		} else {
			buckets.put(n, new Bucket(false));
			buckets.get(n).insert(p);
		}
		if (ar != null) {
			buckets.put((Integer) ar.get(0), (Bucket) ar.get(1));
			if(!b.nodes.contains(local))
			{
				b.spiltable=false;
				buckets.get((Integer) ar.get(0)).spiltable=true;
			}
		}
	}

	static public synchronized final List<Node> findClosest(byte[] target, int numNodesRequired) {
		TreeSet<Node> sortedSet = new TreeSet<>(new IdComparer(target));
		sortedSet.addAll(getNodes());

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
 
	static public synchronized final List<Node> getNodes() {
		List<Node> nodes = new ArrayList<>();
		Collection<Bucket> c = buckets.values();
		for (Bucket b : c)
			for (Node n : b.getNodes())
				nodes.add(n);

		return nodes;
	}
	
}
