package message;

public class PingResponse extends Message{
	public PingResponse(){
		message = Message.PING_RSP + Message.SEPARATOR + Message.CRLF + Message.CRLF;
	}
}
