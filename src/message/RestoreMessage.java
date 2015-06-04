package message;

public class RestoreMessage extends Message{
	private int chunkNo;
	private String fileID, body;
	
	public RestoreMessage(int chunkNo, String fileID, String body) {
		super();
		this.chunkNo = chunkNo;
		this.fileID = fileID;
		this.body = body;
	}
	
	public void buildRestoreMessage(){
		 message = (Message.RESTORE_MSG + Message.SEPARATOR + fileID + Message.SEPARATOR + chunkNo + Message.CRLF+ Message.CRLF).getBytes();

	}
	
	public void buildRestoreResponse(){
		message = (Message.RESTORE_RSP + Message.SEPARATOR + Message.CRLF + Message.CRLF + body).getBytes();
	}
	
	public String getFileID() {
		return fileID;
	}
	
	public String getBody() {
		return body;
	}
	
	public int getChunkNo() {
		return chunkNo;
	}
}
