package node;

import java.math.BigInteger;
import java.util.Comparator;

public class IdComparer implements Comparator<Node> {
	private byte[] target;

	public IdComparer(byte[] target) {
		this.target = target;
	}

	@Override
	public int compare(Node n1, Node n2) {
		BigInteger b1 = new BigInteger(1, n1.getId());
		BigInteger b2 = new BigInteger(1, n2.getId());

		b1 = b1.xor(new BigInteger(1, target));
		b2 = b2.xor(new BigInteger(1, target));
		return b1.abs().compareTo(b2.abs());
	}
}
