package handlers;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import listeners.WriteThread;
import message.Parser;
import message.RestoreMessage;
import message.RestoreResponse;
import tui.TextInterface;

public class RestoreHandler implements Runnable {

	boolean type; //true restore, false restore answer
	RestoreMessage cMessage;
	
	public RestoreHandler(boolean type, RestoreMessage cMessage) {
		this.type = type;
	}

	@Override
	public void run() {
		if(type) {
			byte[] body = null;
			try {
				body = getChunk(cMessage.getFileID(), cMessage.getChunkNo());
			} catch (IOException e) {
				e.printStackTrace();
			}
			TextInterface.threadManager.submit(new WriteThread((new RestoreResponse(body.toString()).getMessageBytes())));
			
		}
		else {
			
		}
	}
	
	private byte[] getChunk(String fileID, int chunkNo) throws IOException {
		
		File file = new File("/storage" + fileID + chunkNo + ".chunk");
		if(!file.exists()) {
			System.out.println("File does not exist");
			return null;
		}
		else {
			Path path = Paths.get(file.getAbsolutePath()); 
			byte[] data = Files.readAllBytes(path);
			return data;
		}
	}
	
}
