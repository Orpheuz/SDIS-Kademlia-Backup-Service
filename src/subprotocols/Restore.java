package subprotocols;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import main.Main;

public class Restore implements Runnable {
	String filename, fileId;

	public Restore(String filename) throws IOException {
		this.filename = filename;
	}

	@Override
	public void run() {
		//TODO ir buscar a BD
		//fileId = Main.getDatabase().getFileIdFromFilename(filename);
		fileId = "";
		if (fileId != null) {
			int currentChunk = 0;
			String body = "";
			byte[] buffer;
			File outputFile = new File(filename);
			try {
				FileOutputStream fileOuputStream = new FileOutputStream(
						outputFile, true);
				while(true) {
					sendGetchunk(currentChunk);
					
					body = ""; //TODO receber a chunk
					buffer = body.getBytes();
					if (!body.equals("")) {
						fileOuputStream.write(buffer);
					}
					System.out.println("Chunk " + currentChunk + " done " + buffer.length + "B");
					if (buffer.length < 64000/*Main.CHUNK_SIZE*/) //TODO ir buscar o chunksize, fazer uma macro algures
						break;
					
					currentChunk++;
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

	private void sendGetchunk(int n) {
		/*Main.getAnonMCSocket().setAddress(
				InetAddress.getByName(Main.CONTROL_ADDRESS),
				Main.CONTROL_PORT);
		Main.getAnonMCSocket().send(
				Message.buildGetChunkMessage(fileId, Integer.toString(n)));*/
		//TODO enviar o getchunk
		System.out.println("Sent a getchunk");
	}
}
