package utils;

import java.io.Serializable;

public class FileIdChunks implements Serializable{
	String fileId;
	int chunks;
	public String getFileId() {
		return fileId;
	}
	public void setFileId(String fileId) {
		this.fileId = fileId;
	}
	public int getChunks() {
		return chunks;
	}
	public void setChunks(int chunks) {
		this.chunks = chunks;
	}
	public FileIdChunks(String fileId, int chunks) {
		super();
		this.fileId = fileId;
		this.chunks = chunks;
	}
	
}
