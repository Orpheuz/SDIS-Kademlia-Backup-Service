package utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.util.ArrayList;

public class Files {

	private File file;

	private ArrayList<Chunk> chunks;
	private String fileName;
	private String path;
	private int repDegree;
	private String fileID;
	private static final int CHUNK_SIZE = 64000;
	private static final long MAX_SIZE = CHUNK_SIZE * 10000;
	
	public Files(String fileName, String path, int repDegree) throws IOException, FileTooLargeException {
		file = new File(path+fileName);
		if(file.length() > MAX_SIZE) {
			throw new FileTooLargeException();
		}
		this.fileName = fileName;
		this.path = path;
		this.setRepDegree(repDegree);
		chunks = new ArrayList<Chunk>();
		calculateHash();
	}
	
	public String getFileName() {
		return fileName;
	}
	
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}

	public ArrayList<Chunk> getChunks() {
		return chunks;
	}
	public void setChunks(ArrayList<Chunk> chunks) {
		this.chunks = chunks;
	}
	
	public long getFileSize() {
		return file.length();
	}
	
	public int getRepDegree() {
		return repDegree;
	}
	
	public void setRepDegree(int repDegree) {
		this.repDegree = repDegree;
	}
	
	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}
	
	public void calculateHash() {
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-256");

			FileInputStream inputStream = new FileInputStream(file);

			byte[] bytesBuffer = new byte[1024];
			int bytesRead = -1;

			while ((bytesRead = inputStream.read(bytesBuffer)) != -1) {
				md.update(bytesBuffer, 0, bytesRead);
			}

			md.update(fileName.getBytes());
			byte[] hashedBytes = md.digest();

			StringBuffer sb = new StringBuffer();

			for (int i = 0; i < hashedBytes.length; i++) {
				sb.append(Integer.toString((hashedBytes[i] & 0xff) + 0x100, 16)
						.substring(1));
			}

			fileID = sb.toString();
			inputStream.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void splitInChunks() throws IOException {
		
		int chunkN = 0;
		byte[] buffer = new byte[CHUNK_SIZE];
		FileInputStream in = new FileInputStream(file);
		while((in.read(buffer,  0, CHUNK_SIZE)) != -1) {
			chunks.add(new Chunk(this, chunkN, buffer, repDegree));
		}
		if(getFileSize() % CHUNK_SIZE == 0) {
			chunks.add(new Chunk(this, chunkN, new byte[0], repDegree));
		}
		in.close();
		//for(int i = 0; i < chunks.size(); i++) {
			System.out.println(chunks.get(0));
	//	}
	}
	
	public String getFileID() {
		return fileID;
	}

	public void setFileID(String fileID) {
		this.fileID = fileID;
	}

	
	public class FileTooLargeException extends Exception {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		
	}
}
