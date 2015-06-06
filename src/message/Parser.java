package message;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;

public class Parser {
	public String body;
	public String[] header;

	public Parser(byte[] message) {
		separateHeader(message);
	}

	public Message parseMessage() {
		if (header != null) {
			switch (Integer.parseInt(header[0])) {
			case Message.PUTCHUNK_MSG:
				System.out.println("Received PUTCHUNK");
				return new PutChunkMessage(header[1], Integer.parseInt(header[2]), Integer.parseInt(header[3]), body);
			case Message.RESTORE_MSG:
				System.out.println("Received RESTORE");
				return new RestoreMessage(Integer.parseInt(header[2]), header[1], Integer.parseInt(header[3]));
			case Message.RESTORE_RSP:
				System.out.println("Received RESTORE RESPONSE");
				return new RestoreResponse(body, header[1], Integer.parseInt(header[2]));
			case Message.DELETE_MSG:
				System.out.println("Received DELETE");
				return new DeleteMessage(header[1]);
			case Message.PING_MSG:
				System.out.println("Received PING");
				return new PingMessage(header[1], Integer.parseInt(header[2]));
			case Message.PING_RSP:
				System.out.println("Received PING RESPONSE");
				return new PingResponse();
			case Message.FINDNODE_MSG:
				System.out.println("Received FINDNODE");
				return new FindNodeMessage(header[1].getBytes(), Integer.parseInt(header[2]), Integer.parseInt(header[3]));
			case Message.FINDNODE_RSP:
				System.out.println("Received FINDNODE RESPONSE");
				return new FindNodeResponse(body);
			default:
				break;
			}
		}
		return null;
	}
	
	public void separateHeader(byte buf[]){
		byte crlf[] = new byte[4];
		crlf[0] = 0xD;
		crlf[1] = 0xA;
		crlf[2] = 0xD;
		crlf[3] = 0xA;

		int pos = 0;
		int j = 0;

		for (int i = 0; i < buf.length; i++) {
			if (crlf[j] == buf[i])
				j++;

			if (j == crlf.length) {
				pos = i - crlf.length;
				break;
			}
		}

		if(pos != 0){
			byte[] h;
			h = Arrays.copyOf(buf, pos);
			if(buf.length > pos + 4){
				try {
					body = new String(Arrays.copyOfRange(buf, pos+4, buf.length),"UTF-8");
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
			}
			try {
				String s = new String(h, "UTF-8");
				header = new String(s).split("\\s");
				for(int i = 0; i < header.length;i++){
					header[i] = header[i].trim();
				}
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}

		}

	}

}
