package dht;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

public class DHT {

	public static List<DHTContent> DHT;

	public DHT() throws ClassNotFoundException {
		try {
			DHT = new ArrayList<DHTContent>();
			initialize();
		} catch (IOException e) {
			e.printStackTrace();

		}
	}

	@SuppressWarnings("unchecked")
	public boolean initialize() throws IOException, ClassNotFoundException {
		ObjectInputStream in;
		try {
			in = new ObjectInputStream(new FileInputStream("DHT.file"));
			DHT = (List<DHTContent>) in.readObject();
		} catch (FileNotFoundException e) {
			System.out.println("No Dht file found");
			return false;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		in.close();
		return true;
	}

	public boolean put(DHTContent content) {
		if(hasFile(content.getFileHash(), content.getOwnerID()))
			return DHT.add(content);
		else
			return false;

	}
	
	public ArrayList<byte[]> whoHasIt(String fileHash) {
		ArrayList<byte[]> ret = new ArrayList<byte[]>();
		for (int i = 0; i < DHT.size(); i++) {
			String s = DHT.get(i).getFileHash();
			String[] sArr = s.split(File.separator);
			if (sArr[0] == fileHash) {
				ret.add(DHT.get(i).getOwnerID());
			}
		}
		return ret;
	}

	public boolean hasFile(String fileHash, byte[] ownerID) {
		for (int i = 0; i < DHT.size(); i++) {
			if (DHT.get(i).getFileHash() == fileHash
					&& DHT.get(i).getOwnerID() == ownerID) {
				return true;
			}
		}
		return false;
	}

	public List<DHTContent> getDHT() {
		return DHT;
	}

	public boolean remove(String fileHash, byte[] ownerID) {
		for (int i = 0; i < DHT.size(); i++) {
			if (DHT.get(i).getFileHash() == fileHash
					&& DHT.get(i).getOwnerID() == ownerID) {
				DHT.remove(i);
				return true;
			}
		}
		return false;
	}

	public void removeFile(String fileHash) {
		for (int i = 0; i < DHT.size(); i++) {
			if (DHT.get(i).getFileHash() == fileHash) {
				DHT.remove(i);
			}
		}
	}

	public boolean removeUserFromDHT(byte[] ownerID) {
		for (int i = 0; i < DHT.size(); i++) {
			if (DHT.get(i).getOwnerID() == ownerID) {
				DHT.remove(i);
				return true;
			}
		}
		return false;
	}

	public boolean saveDHT() throws IOException {
		ObjectOutputStream out;
		try {
			out = new ObjectOutputStream(new FileOutputStream("DHT.file"));
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
