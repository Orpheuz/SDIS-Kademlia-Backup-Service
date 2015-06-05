package handlers;

import java.io.File;
import java.io.FileOutputStream;

import message.Message;
import message.PutChunkMessage;

public class BackupHandler implements Runnable {
	
	private PutChunkMessage message;
	
	public BackupHandler(PutChunkMessage message) {
		this.message = message;
	}

	private boolean storeFile(String fileId, String chunkNo, String body) {
		String filename = "storage/" + fileId + "_" + chunkNo + ".chunk";
		File f = new File(filename);
		body = body.trim();
		if (!f.exists()) {
			try {
				FileOutputStream out = new FileOutputStream(f);
				out.write(body.getBytes());
				out.close();
				return true;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return false; 
	}

	@Override
	public void run() {
		storeFile(message.getFileID(), Integer.toString(message.getChunkNo()), message.getBody());
	}
}
