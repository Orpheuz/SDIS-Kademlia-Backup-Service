package subprotocols;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Random;
import java.util.TreeSet;

import listeners.WriteThread;
import message.PutChunkMessage;
import node.Node;
import tui.TextInterface;
import utils.HashCalc;
import dht.DHT;
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
			String toHash = file.getName();
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
			byte[] buffer = new byte[CHUNK_SIZE];
			int read = 0;
			byte[] chunk;
			while ((read = fs.read(buffer)) > 0) {
				chunk = new byte[read];
				System.arraycopy(buffer, 0, chunk, 0, read);

				ArrayList<byte[]> ids = new ArrayList<byte[]>();
				for(int i = 0; i < replicationDegree; i++){
					
					boolean found = false;

					Lookup lp = new Lookup(generateTarget());
					TreeSet<Node> nodes = lp.run();
					Node node = null;
					while(!found){
						System.out.println(nodes.size());
						 node = nodes.first();
						if(ids.contains(node.getId())){
							nodes.remove(node);
							node = nodes.first();
						}
						else{
							ids.add(node.getId());
							found = true;
						}
					}
						sendPutchunk(counter, new String(chunk, "UTF-8"), node);
						
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

	public void sendPutchunk(int chunkNo, String body, Node node) throws IOException{


		PutChunkMessage message = new PutChunkMessage(fileId, chunkNo, replicationDegree, body);
		System.out.println("Sent putchunk " + chunkNo + " with size " + message.getMessage().length());
		TextInterface.threadManager.submit(new WriteThread(message.getMessage(), node.getIP(), node.getPort()));
		TextInterface.dht.put(new DHTContent(1, node.getId(), fileId + "_" + chunkNo));
		System.out.println(TextInterface.dht.getDHT().size());
	}
}
