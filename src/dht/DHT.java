package dht;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;

public class DHT {

	public static List<DHTContent> DHT;

	public DHT() {
		try {
			initialize();
		} catch (IOException e) {
			e.printStackTrace();
			
		}
	}

	public boolean initialize() throws IOException {
		ObjectInputStream in;
		try {
			in = new ObjectInputStream(new FileInputStream("DHT.file"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return false;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		in.close();
		return true;
	}

	public boolean put(DHTContent content) {
		return DHT.add(content);

	}

	public List<DHTContent> getDHT() {
		return DHT;
	}

	public boolean remove(String fileHash, int ownerID) {
		for (int i = 0; i < DHT.size(); i++) {
			if(DHT.get(i).getFileHash() == fileHash && DHT.get(i).getOwnerID() == ownerID) {
				DHT.remove(i);
				return true;
			}
		}
		return false;
	}
	
	public void removeFile(String fileHash) {
		for (int i = 0; i < DHT.size(); i++) {
			if(DHT.get(i).getFileHash() == fileHash) {
				DHT.remove(i);
			}
		}
	}

	public boolean removeUserFromDHT(int ownerID) {
		for (int i = 0; i < DHT.size(); i++) {
			if(DHT.get(i).getOwnerID() == ownerID) {
				DHT.remove(i);
				return true;
			}
		}
		return false;
	}

	public boolean saveDHT() throws IOException {
		ObjectOutputStream out;
		try {
			out = new ObjectOutputStream(new FileOutputStream(
					"DHT.file"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return false;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		out.close();
		return true;
	}
}
