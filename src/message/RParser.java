package message;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

public class RParser extends Thread{
	String messageType,version,fileId;
	String[] header;
	int chunkNo, replicationDeg;

	private ArrayList<byte[]> messages;

	public RParser(ArrayList<byte[]> messages){
		this.messages = messages;
	}


	public void run(){
		while(true){
			if(messages.size()>0){
				if(messages.get(0) != null){
					String s = "";
					try {
						s = new String(messages.get(0), "UTF-8");
					} catch (UnsupportedEncodingException e) {
						e.printStackTrace();
					}
					header = new String(s).split("\\s");
					parseMessage(header);
				}
			}
		}
	}

	public void parseMessage(String[] header) {
		if(header != null){
			if(header[0] == Parser.GETCHUNK_MSG) {
				messageType = header[0];
				version = header[1];
				fileId = header[2];
				chunkNo = Integer.parseInt(header[3]);
				//TODO tratar isto
			}
		}
		else{
			String s = null;
			try {
				s = new String(messages.get(0),"UTF-8");
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("Recebi mensagem de getchunk invalida: " + s);
		}
		messages.remove(0);
	}


}

