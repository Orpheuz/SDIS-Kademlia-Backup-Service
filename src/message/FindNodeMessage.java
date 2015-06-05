package message;

import java.io.UnsupportedEncodingException;

public class FindNodeMessage extends Message{
	byte[] key;
	int k,port;
	
	public byte[] getKey() {
		return key;
	}

	public int getK() {
		return k;
	}

	public int getPort() {
		return port;
	}

	
	public FindNodeMessage(byte[] key, int k, int port){
		try {
			this.key = key;
			this.k = k;
			this.port = port;
			message = Message.FINDNODE_MSG + Message.SEPARATOR + new String(key,"UTF-8") + Message.SEPARATOR + k + Message.SEPARATOR +port + Message.SEPARATOR + Message.CRLF + Message.CRLF;
		} catch (UnsupportedEncodingException e) {
			System.out.println("Failed to convert byte array to string");
		}
		
	}
}
