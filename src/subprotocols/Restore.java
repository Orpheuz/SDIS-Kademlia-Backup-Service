package subprotocols;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import message.RestoreMessage;
import node.Node;
import tui.TextInterface;

public class Restore implements Runnable {
	String filename, fileId;
	int numberOfChunks;
	public Restore(String filename) throws IOException {
		this.filename = filename;
	}

	@Override
	public void run() {
		fileId = TextInterface.database.getIdFromName(filename).getFileId();
		numberOfChunks = TextInterface.database.getIdFromName(filename).getNumberOfChunks();
		if (fileId != null) {
			int currentChunk = 0;
			String body = "";
			byte[] buffer;
			File outputFile = new File(filename);
			
			try {
				FileOutputStream fileOuputStream = new FileOutputStream(
						outputFile, true);
				for(int i = 0; i < numberOfChunks; i++){
					ArrayList<byte[]> owners = TextInterface.dht.whoHasIt(fileId);
					for(byte[] owner: owners){
						sendRestoreMessage(i, owner);
					}
				}
				System.out.println("restore done");
				fileOuputStream.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else
			System.out.println("File not backed up yet");
	}

	private void sendRestoreMessage(int n, byte[] target) {
		Lookup lp = new Lookup(target);
		Node node = lp.run();
	}
}
