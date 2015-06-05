package subprotocols;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.UnknownHostException;
import java.util.Random;

import listeners.WriteThread;
import main.Main;
import message.PutChunkMessage;
import node.Node;
import tui.TextInterface;
import utils.HashCalc;
import dht.DHTContent;

public class Backup implements Runnable {

	private File file;
	private int replicationDegree;
	private String fileId;
	private final static int CHUNK_SIZE = 64000;
	public Backup(File file, int replicationDegree) throws UnknownHostException {
		this.file = file;
		this.replicationDegree = replicationDegree;
		try {
			byte[] buffer = new byte[CHUNK_SIZE];
			String toHash = file.getName();
			System.out.println(file.getName());
			FileInputStream fs = new FileInputStream(file);
			while (fs.read(buffer) > 0) {
				toHash += new String(buffer);
			}
			fs.close();
			fileId = HashCalc.generateFileID(toHash);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	//gerar array a partir do file id
	public byte[] generateTarget(){
		byte[] result = new byte[160];
		Random rng = new Random();
		rng.setSeed(System.currentTimeMillis());
		for(int i = 0; i < 160; i++){
			result[i] = (byte) rng.nextInt(1);
		}
		return result;
	}
	@Override
	public void run() {
		System.out.println("Running backup thread");
		int counter = 0;
		try {
			FileInputStream fs = new FileInputStream(file);
			byte[] buffer = new byte[CHUNK_SIZE*2];
			
			while (fs.read(buffer) > 0) {
				for(int i = 0; i < replicationDegree; i++){
					sendPutchunk(i, new String(buffer, "UTF-8"), generateTarget());
				}
				counter++;
			}
			fs.close();
			System.out.println("done");
			
			TextInterface.database.addFileToDB(fileId, file.getName(), counter);
			
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	public void sendPutchunk(int chunkNo, String body, byte[] target) throws IOException{
		System.out.println("Sent putchunk " + chunkNo);
		
		Lookup lp = new Lookup(target);
		Node node = lp.run();
		//TODO impedir que saiam nodes repetidos
		PutChunkMessage message = new PutChunkMessage(fileId, chunkNo, replicationDegree, body);
		System.out.println(fileId);
		TextInterface.threadManager.submit(new WriteThread(message.getMessage(), node.getIP(), node.getPort()));
		TextInterface.dht.put(new DHTContent(0, node.getId(), fileId + "_" + chunkNo));
		
	}
}