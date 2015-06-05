package utils;

public class Chunk {
	
	private Files parent;
	private int chunkNumber;
	private byte[] data;
	
	public Chunk(Files parent, int chunkNumber, byte[]data, int repDegree) {
		this.parent = parent;
		this.chunkNumber = chunkNumber;
	}
	public int getChunkNumber() {
		return chunkNumber;
	}

	public void setChunkNumber(int chunkNumber) {
		this.chunkNumber = chunkNumber;
	}

	public byte[] getData() {
		return data;
	}

	public void setData(byte[] data) {
		this.data = data;
	}

	public Files getParent() {
		return parent;
	}

	public void setParent(Files parent) {
		this.parent = parent;
	}
	
}
