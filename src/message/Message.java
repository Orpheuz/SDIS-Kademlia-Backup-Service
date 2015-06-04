package message;


public class Message {

	public static final int PING_MSG = 0x20;
	public static final int PING_RSP = 0x21;
	public static final int FINDNODE_MSG = 0x22;
	public static final int FINDNODE_RSP = 0x23;
	public static final int FINDVALUE_MSG = 0x24;
	public static final int FINDVALUE_RSP = 0x25;
	public static final int PUTCHUNK_MSG = 0x26;
	public static final int RESTORE_MSG = 0x27;
	public static final int RESTORE_RSP = 0x28;
	public static final int DELETE_MSG = 0x29;

	public static final String CRLF = "\r\n";
	public static final String SEPARATOR = " ";
	int code;
	byte[] message;

	public byte[] getMessage() {
		return message;
	}
	
	
}
