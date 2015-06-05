package message;

public class PingMessage extends Message {
	int port;
	String id;

	public int getPort() {
		return port;
	}

	public PingMessage(String id, int port){
		this.id = id;
		this.port = port;
		message = Message.PING_MSG + Message.SEPARATOR + id +Message.SEPARATOR + port + Message.SEPARATOR + Message.CRLF + Message.CRLF;
	}

	public String getId() {
		return id;
	}
	
}
