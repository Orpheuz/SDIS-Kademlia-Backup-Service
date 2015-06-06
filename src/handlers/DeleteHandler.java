package handlers;

import java.io.File;

import tui.TextInterface;

public class DeleteHandler implements Runnable {
	
	private String fileID;
	
	public DeleteHandler(String fileID) {
		this.fileID = fileID;
	}
	
	private boolean processDelete(File folder) {
		File[] files = folder.listFiles();
		if (files != null) {
			for (File f : files) {
				if (f.isDirectory()) {
					processDelete(f);
				} else {
					f.delete();
				}
			}
		}
		folder.delete();
		return true;
	}

	private void deleteFromDHT() {
		TextInterface.dht.removeFile(fileID);
	}
	
	@Override
	public void run() {
		deleteFromDHT();
		if(processDelete(new File(fileID)))
			System.out.println("File deleted");
		else 
			System.out.println("File does not exist");
			
		
	}
}
