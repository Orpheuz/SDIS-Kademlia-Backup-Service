package message;

public class DeleteMessage extends Message{
	String fileID;

	public String getFileID() {
		return fileID;
	}

	public DeleteMessage(String fileID) {
		super();
		this.fileID = fileID;
	}
	
	public void buildDeleteMessage(){
		message = (Message.DELETE_MSG + Message.SEPARATOR + fileID + Message.SEPARATOR + Message.CRLF+ Message.CRLF).getBytes();
	}
	
	
}
