package message;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

public class Parser {
	static String body;
	static String[] header;
	String messageType,version,fileId;
	int chunkNo, replicationDeg;

	private ArrayList<byte[]> messages;

	public Parser(ArrayList<byte[]> messages){
		this.messages = messages;
	}


	public void run(){
		while(true){
			if(messages.size()>0){
				if(messages.get(0) != null){
					separateHeader(messages.get(0));
					parseMessage();
				}
			}
		}
	}

	public Message parseMessage() {
		if(header != null){
			switch (Integer.parseInt(header[0])) {
			case Message.PUTCHUNK_MSG:
				return new PutChunkMessage(header[1], Integer.parseInt(header[2]), Integer.parseInt(header[3]));
			case Message.RESTORE_MSG:
				return new RestoreMessage(Integer.parseInt(header[2]), header[1], body);
			case Message.DELETE_MSG:
				return new DeleteMessage(header[1]);
				
			default:
				break;
			}
		}
		messages.remove(0);
		return null;
	}
	
	
	
	public void separateHeader(byte buf[]){
		String msg = "";
		try {
			msg = new String(buf,"UTF-8");
		} catch (UnsupportedEncodingException e) {
			System.out.println("Failed to convert byte array to string");
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

