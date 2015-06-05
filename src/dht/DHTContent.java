package dht;

public class DHTContent {
	private int ownerID, id;
	private String fileHash;
	
	public DHTContent(int id, int ownerID, String fileHash) {
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

	public int getOwnerID() {
		return ownerID;
	}

	public void setOwnerID(int ownerID) {
		this.ownerID = ownerID;
	}
}