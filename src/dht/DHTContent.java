package dht;

public class DHTContent {
	private String key;
	private int ownerID;
	private String fileHash;
	
	public DHTContent(String key, int ownerID, String fileHash) {
		this.setKey(key);
		this.setOwnerID(ownerID);
		this.setFileHash(fileHash);
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

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}
}
