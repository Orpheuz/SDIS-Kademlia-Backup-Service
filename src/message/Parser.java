package message;

import java.io.UnsupportedEncodingException;

public class Parser {
	public String body;
	public String[] header;

	public Parser(byte[] message){
		separateHeader(message);
	}


	public Message parseMessage() {
		if(header != null){
			switch (Integer.parseInt(header[0])) {
			case Message.PUTCHUNK_MSG:
				return new PutChunkMessage(header[1], Integer.parseInt(header[2]), Integer.parseInt(header[3]),body);
			case Message.RESTORE_MSG:
				return new RestoreMessage(Integer.parseInt(header[2]), header[1], Integer.parseInt(header[3]));
			case Message.RESTORE_RSP:
				return new RestoreResponse(body,header[1],Integer.parseInt(header[2]));
			case Message.DELETE_MSG:
				return new DeleteMessage(header[1]);
			case Message.PING_MSG:
				return new PingMessage(Integer.parseInt(header[1]),Integer.parseInt(header[2]));
			case Message.PING_RSP:
				return new PingResponse();
			case Message.FINDNODE_MSG:
				return new FindNodeMessage(header[1].getBytes(),Integer.parseInt(header[2]),Integer.parseInt(header[3]));
			case Message.FINDNODE_RSP:
				return new FindNodeResponse(body);
			default:
				break;
			}
		}
		return null;
	}
	
	
	
	public void separateHeader(byte buf[]){

		String msg = "";
		try {
			msg = new String(buf,"UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		msg = msg.trim();
		if(msg != ""){
			
			String[] dataArr = msg.split(Message.CRLF + Message.CRLF);
			for (int i = 0; i < dataArr.length; i++) {
				dataArr[i] = dataArr[i].trim();
			}

			if (dataArr.length > 1)
				body = dataArr[1];

			header = dataArr[0].split("\\s+");

			for (int i = 0; i < header.length; i++) {
				header[i] = header[i].trim();
			}
		}
		
		
	}

	
//	public static void separateHeader(byte buf[]){
//		byte crlf[] = new byte[4];
//		crlf[0] = 0xD;
//		crlf[1] = 0xA;
//		crlf[2] = 0xD;
//		crlf[3] = 0xA;
//
//		int pos = 0;
//		int j = 0;
//
//		for (int i = 0; i < buf.length; i++) {
//			if (crlf[j] == buf[i]) 
//				j++;
//
//			if (j == crlf.length) {
//				pos =  i - crlf.length;
//				break;
//			}
//		}
//
//		if(pos != 0){
//			byte[] h;
//			h = Arrays.copyOf(buf, pos);
//			body = Arrays.copyOfRange(buf, pos+4, buf.length);
//			try {
//				String s = new String(h, "UTF-8");
//				header = new String(s).split("\\s");
//				for(int i = 0; i < header.length;i++){
//					header[i] = header[i].trim();
//				}
//			} catch (UnsupportedEncodingException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		}
//
//	}	

}

