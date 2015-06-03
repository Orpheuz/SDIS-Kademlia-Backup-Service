package routing;

import node.Node;

public class Routing {
	Node local;
	Bucket[] buckets;

	public Routing(Node local) {
		this.local = local;
		buckets = new Bucket[160];
		for (int i = 0; i < buckets.length; ++i) {
			buckets[i] = new Bucket(i);
		}
	}

	public int getbucket(Node p){
		return local.distance(p.getId())-1;
	}
	
	public void insert(Node p)
	{
		buckets[getbucket(p)].insert(p);
	}

}
