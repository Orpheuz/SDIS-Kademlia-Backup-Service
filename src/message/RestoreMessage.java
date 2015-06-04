package message;

public class RestoreMessage extends Message{
	private int chunkNo;
	private String fileID;
	
	public RestoreMessage(int chunkNo, String fileID, String body) {
		super();
		this.chunkNo = chunkNo;
		this.fileID = fileID;
		message = Message.RESTORE_MSG + Message.SEPARATOR + fileID + Message.SEPARATOR + chunkNo + Message.CRLF+ Message.CRLF;
	}
	
	public String getFileID() {
		return fileID;
	}
	
	public int getChunkNo() {
		return chunkNo;
	}
}
