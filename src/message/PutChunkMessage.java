package message;

public class PutChunkMessage extends Message {
	private int chunkNo, repDeg;
	private String body, fileID;
	
	public PutChunkMessage(String fileID, int chunkNo, int repDeg) {
		super();
		this.fileID = fileID;
		this.chunkNo = chunkNo;
		this.repDeg = repDeg;
	}

	public void buildPutChunkMessage() {
		 message = (Message.PUTCHUNK_MSG + Message.SEPARATOR + fileID + Message.SEPARATOR + chunkNo + Message.SEPARATOR + repDeg + Message.SEPARATOR + Message.CRLF+ Message.CRLF + body).getBytes();
	}

	public String getFileID() {
		return fileID;
	}

	public int getChunkNo() {
		return chunkNo;
	}

	public int getRepDeg() {
		return repDeg;
	}
}
