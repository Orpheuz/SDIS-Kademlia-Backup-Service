package message;

public class RestoreMessage extends Message{
	private int chunkNo, port;
	private String fileID;
	
	public RestoreMessage(int chunkNo, String fileID, String body, int port) {
		super();
		this.chunkNo = chunkNo;
		this.fileID = fileID;
		this.port = port;
		message = Message.RESTORE_MSG + Message.SEPARATOR + fileID + Message.SEPARATOR + chunkNo + Message.SEPARATOR + port +Message.SEPARATOR +Message.CRLF+ Message.CRLF;
	}
	
	public String getFileID() {
		return fileID;
	}
	
	public int getChunkNo() {
		return chunkNo;
	}
	
	public int getPort(){
		return port;
	}
}
