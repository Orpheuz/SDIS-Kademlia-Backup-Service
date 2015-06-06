package handlers;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import message.PutChunkMessage;

public class BackupHandler implements Runnable {
	
	private PutChunkMessage message;
	
	public BackupHandler(PutChunkMessage message) {
		this.message = message;
	}

	private boolean storeFile(String fileId, String chunkNo, String body) throws IOException {	
		File f = new File(fileId + File.separator + chunkNo);
		f.getParentFile().mkdirs();
		FileOutputStream file = new FileOutputStream(f);
		body = body.trim();
		file.write(body.getBytes());
		file.close();

		return true;	
		
	}

	@Override
	public void run() {
		try {
			storeFile(message.getFileID(), Integer.toString(message.getChunkNo()), message.getBody());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
