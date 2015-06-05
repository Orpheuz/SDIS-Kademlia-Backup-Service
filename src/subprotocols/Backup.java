package subprotocols;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.UnknownHostException;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

import dht.DHTContent;
import tui.TextInterface;
import utils.HashCalc;
import message.PutChunkMessage;
import message.Write;
import node.Node;

public class Backup implements Runnable {

	private File file;
	private int replicationDegree;
	private byte[] fileId;
	private final static int CHUNK_SIZE = 64000;
	public Backup(File file, int replicationDegree) throws UnknownHostException {
		this.file = file;
		this.replicationDegree = replicationDegree;
		try {
			byte[] buffer = new byte[CHUNK_SIZE];
			String toHash = file.getName();
			FileInputStream fs = new FileInputStream(file);
			while (fs.read(buffer) > 0) {
				toHash += new String(buffer);
			}
			fs.close();
			fileId = HashCalc.sha1Hash(toHash);
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
		try {
			FileInputStream fs = new FileInputStream(file);
			byte[] buffer = new byte[CHUNK_SIZE];
			while (fs.read(buffer) > 0) {
				for(int i = 0; i < replicationDegree; i++){
					sendPutchunk(i, new String(buffer), generateTarget());
				}
			}
			fs.close();
			System.out.println("done");

		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	public void sendPutchunk(int chunkNo, String body, byte[] target) throws IOException{
		System.out.println("Sent putchunk " + chunkNo);
		
		Lookup lp = new Lookup(target);
		Node node = lp.run();
		PutChunkMessage message = new PutChunkMessage(new String(fileId), chunkNo, replicationDegree);
		Write.send(message.getMessage(), node.getIP(), node.getPort());
		
		TextInterface.dht.put(new DHTContent(0, target, fileId));
		
	}
}