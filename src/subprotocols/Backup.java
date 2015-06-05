package subprotocols;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.UnknownHostException;

import utils.Files;

public class Backup implements Runnable {

	private Files file;
	private final static int CHUNK_SIZE = 64000;
	public Backup(Files file) throws UnknownHostException {
		this.file = file;
	}

	@Override
	public void run() {
		System.out.println("Running backup thread");
		try {
			@SuppressWarnings("resource")
			FileInputStream fs = new FileInputStream(file.getFile());
			int counter = 0;
			byte[] buffer = new byte[CHUNK_SIZE];

			while (fs.read(buffer) > 0) {
				boolean timeout = true;
				
				sendPutchunk(counter, new String(buffer));
				
				if(timeout){
					System.out.println("timeout");
					return;
				}
				counter++;
			}
			fs.close();
			System.out.println("done");

		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	public void sendPutchunk(int chunkNo, String body) throws IOException{
		System.out.println("Sent putchunk " + chunkNo);
		/*Main.getAnonMCSocket().setAddress(InetAddress.getByName(Main.BACKUP_ADDRESS), Main.BACKUP_PORT);
		Main.getAnonMCSocket().send(Message.buildPutChunkMessage(file.getFileID(),
				String.valueOf(chunkNo),
				String.valueOf(file.getRepDegree()),
				body));*/
		//TODO enviar o PUTCHUNK para o devido peer
	}
}