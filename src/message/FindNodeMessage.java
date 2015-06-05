package message;

import java.io.UnsupportedEncodingException;

public class FindNodeMessage extends Message{
	byte[] key;
	
	public FindNodeMessage(byte[] key){
		try {
			this.key = key;
			message = Message.FINDNODE_MSG + Message.SEPARATOR + new String(key,"UTF-8") + Message.SEPARATOR + Message.CRLF + Message.CRLF;
		} catch (UnsupportedEncodingException e) {
			System.out.println("Failed to convert byte array to string");
		}
		
	}
}
