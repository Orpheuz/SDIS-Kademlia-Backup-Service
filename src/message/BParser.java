package message;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;

import protocols.Backup;

public class BParser extends Thread{
	static byte[] body;
	static String[] header;
	String messageType,version,fileId;
	int chunkNo, replicationDeg;

	private ArrayList<byte[]> messages;

	public BParser(ArrayList<byte[]> messages){
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

	public void parseMessage() {
		if(header != null){
			if(header[0] == Parser.PUTCHUNK_MSG) {
				messageType = header[0];
				version = header[1];
				fileId = header[2];
				chunkNo = Integer.parseInt(header[3]);
				replicationDeg = Integer.parseInt(header[4]);
				try {
					Backup.storeChunk(fileId, chunkNo, replicationDeg, body);
				} catch (IOException e) {
					System.out.println("Failed to store file");
				}
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
			System.out.println("Recebi mensagem de backup invalida: " + s);
		}
		messages.remove(0);
	}

	public static void separateHeader(byte buf[]){
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
				pos =  i - crlf.length;
				break;
			}
		}

		if(pos != 0){
			byte[] h;
			h = Arrays.copyOf(buf, pos);
			body = Arrays.copyOfRange(buf, pos+4, buf.length);
			try {
				String s = new String(h, "UTF-8");
				header = new String(s).split("\\s");
				for(int i = 0; i < header.length;i++){
					header[i] = header[i].trim();
				}
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}	

}
