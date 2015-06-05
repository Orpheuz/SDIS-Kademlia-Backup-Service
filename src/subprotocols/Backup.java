package subprotocols;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.UnknownHostException;

public class Backup implements Runnable {

	private File file;
	private final static int CHUNK_SIZE = 64000;
	public Backup(File file, int replicationDegree) throws UnknownHostException {
		this.file = file;
	}

	//gerar array a partir do file id
	@Override
	public void run() {
		System.out.println("Running backup thread");
		try {
			@SuppressWarnings("resource")
			FileInputStream fs = new FileInputStream(file);
			int counter = 0;
			byte[] buffer = new byte[CHUNK_SIZE];

			while (fs.read(buffer) > 0) {
				sendPutchunk(counter, new String(buffer));
			}
			fs.close();
			System.out.println("done");

		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	public void sendPutchunk(int chunkNo, String body) throws IOException{
		System.out.println("Sent putchunk " + chunkNo);
		
		Lookup lp = new Lookup(target);
		lp.run();
		/*Main.getAnonMCSocket().setAddress(InetAddress.getByName(Main.BACKUP_ADDRESS), Main.BACKUP_PORT);
		Main.getAnonMCSocket().send(Message.buildPutChunkMessage(file.getFileID(),
				String.valueOf(chunkNo),
				String.valueOf(file.getRepDegree()),
				body));*/
		//TODO enviar o PUTCHUNK para o devido peer
		
	}
}