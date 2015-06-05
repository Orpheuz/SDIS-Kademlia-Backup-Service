package message;

public class RestoreResponse extends Message{

	String body,fileID;
	int chunkNo;

	public String getBody() {
		return body;
	}

	public RestoreResponse(String body, String fileID, int chunkNo) {
		super();
		this.body = body;
		message = Message.RESTORE_RSP + Message.SEPARATOR + fileID + Message.SEPARATOR + chunkNo + Message.SEPARATOR+ Message.CRLF + Message.CRLF + body;
	}

	public String getFileID() {
		return fileID;
	}

	public int getChunkNo() {
		return chunkNo;
	}
	
}
