package message;

public class PingMessage extends Message {
	int deltaTime;
	
	public PingMessage(int deltaTime){
		this.deltaTime = deltaTime;
		message = Message.PING_MSG + Message.SEPARATOR + Message.CRLF + Message.CRLF;
	}
	
}
