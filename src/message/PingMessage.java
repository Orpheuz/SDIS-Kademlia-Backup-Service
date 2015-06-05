package message;

public class PingMessage extends Message {
	int deltaTime, port;
	
	public int getDeltaTime() {
		return deltaTime;
	}

	public int getPort() {
		return port;
	}

	public PingMessage(int deltaTime, int port){
		this.deltaTime = deltaTime;
		this.port = port;
		message = Message.PING_MSG + Message.SEPARATOR + port + Message.SEPARATOR + Message.CRLF + Message.CRLF;
	}
	
}
