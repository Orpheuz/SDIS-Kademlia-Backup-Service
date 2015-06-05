package utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class Database implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private HashMap<String, FileIdChunks> database; 
	private File file;

	public Database(String fileName) throws IOException, ClassNotFoundException {
		file = new File(fileName);
		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				System.out.println("Database file " + fileName
						+ " has been created");
				e.printStackTrace();
			}
			database = new HashMap<String, FileIdChunks>();
		} else {
			database = new HashMap<String, FileIdChunks>();
			FileInputStream fis = new FileInputStream(file);
			ObjectInputStream ois = new ObjectInputStream(fis);
			try {
				database = (HashMap<String, FileIdChunks>) ois.readObject();
			} catch (IOException e) {
				System.out.println("Database read!");
			}
			ois.close();
		}
		saveDatabase();
	}
	
	public void saveDatabase() throws IOException {
		FileOutputStream fos = new FileOutputStream(file);
		ObjectOutputStream oos = new ObjectOutputStream(fos);
		oos.close();
		System.out.println("Database saved.");
	}
	
	public FileIdChunks getIdFromName(String filename) {
		return database.get(filename);
	}

	public void addFileToDB(String fileid, String name, int chunks) {
		database.put(name, new FileIdChunks(name, chunks));
		
	}

}
