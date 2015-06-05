package handlers;

import java.io.File;

import tui.TextInterface;

public class DeleteHandler implements Runnable {
	
	private String fileID;
	
	public DeleteHandler(String fileID) {
		this.fileID = fileID;
	}
	
	private boolean processDelete() {
		File[] dirFiles = new File(".").listFiles();
		for (int i = 0; i < dirFiles.length; i++)
			if (dirFiles[i].getName().startsWith(fileID, 0)) {
				new File(dirFiles[i].getName()).delete();
				System.out.println("File deleted");
				return true;
			}
		return false;
	}

	private void deleteFromDHT() {
		TextInterface.dht.removeFile(fileID);
	}
	
	@Override
	public void run() {
		deleteFromDHT();
		if(processDelete())
			System.out.println("File does not exists");
		else 
			System.out.println("File deleted");
			
		
	}
}
