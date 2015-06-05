package dht;

public class DHTContent {
	private byte[] ownerID;
	private int id;
	private String fileHash;
	
	public DHTContent(int id, byte[] ownerID, String fileHash) {
		this.setOwnerID(ownerID);
		this.setFileHash(fileHash);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFileHash() {
		return fileHash;
	}

	public void setFileHash(String fileHash) {
		this.fileHash = fileHash;
	}

	public byte[] getOwnerID() {
		return ownerID;
	}

	public void setOwnerID(byte[] ownerID) {
		this.ownerID = ownerID;
	}
}