package message;

import java.io.UnsupportedEncodingException;

public class FindNodeMessage extends Message{
	byte[] key;
	int k;
	
	public byte[] getKey() {
		return key;
	}

	public int getK() {
		return k;
	}

	public FindNodeMessage(byte[] key, int k){
		try {
			this.key = key;
			this.k = k;
			message = Message.FINDNODE_MSG + Message.SEPARATOR + new String(key,"UTF-8") + Message.SEPARATOR + k + Message.SEPARATOR + Message.CRLF + Message.CRLF;
		} catch (UnsupportedEncodingException e) {
			System.out.println("Failed to convert byte array to string");
		}
		
	}
}
