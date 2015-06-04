package message;

public class RestoreResponse extends Message{

	String body;

	public String getBody() {
		return body;
	}

	public RestoreResponse(String body) {
		super();
		this.body = body;
		message = Message.RESTORE_RSP + Message.SEPARATOR + Message.CRLF + Message.CRLF + body;
	}
	
}
