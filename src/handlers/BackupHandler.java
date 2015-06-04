package handlers;

import java.io.File;
import java.io.FileOutputStream;

public class BackupHandler implements Runnable {
	
	private String message;
	
	public BackupHandler(String message) {
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
		//storefile
	}
}
