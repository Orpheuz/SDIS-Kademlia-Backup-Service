package handlers;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import listeners.WriteThread;
import message.RestoreMessage;
import message.RestoreResponse;
import tui.TextInterface;

public class RestoreHandler implements Runnable {

	RestoreMessage cMessage;
	RestoreResponse rMessage;
	InetAddress ip;
	boolean type;

	public RestoreHandler(RestoreMessage cMessage, InetAddress ip) {
		this.ip = ip;
		type = true;
	}

	public RestoreHandler(RestoreResponse cMessage, InetAddress ip) {
		this.ip = ip;
		type = false;
	}

	@Override
	public void run() {
		if(type) {
			byte[] body = null;

			body = getChunk(cMessage.getFileID(), cMessage.getChunkNo());

			TextInterface.threadManager.submit(new WriteThread(
					(new RestoreResponse(body.toString(), cMessage.getFileID(), cMessage.getChunkNo())).getMessage(), ip, cMessage.getPort()));

		}
		else {
			storeFile(rMessage.getFileID(), Integer.toString(rMessage.getChunkNo()), rMessage.getBody());
		}
	}

	private byte[] getChunk(String fileID, int chunkNo) {

		File file = new File(fileID +File.separator + chunkNo);
		System.out.println("sgjshadgjhsaghdgahdgshaghdgashgdasgd" + fileID);
		if(!file.exists()) {
			System.out.println("File does not exist");
			return null;
		}
		else {
			Path path = Paths.get(file.getAbsolutePath()); 
			byte[] data = null;
			try {
				data = Files.readAllBytes(path);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("TAMANHO "+ data.length);
			return data;
		}
	}

	private boolean storeFile(String fileId, String chunkNo, String body) {
		String filename =fileId +File.separator + chunkNo;
		File f = new File(filename);
		body = body.trim();
		if (!f.exists()) {
			try {
				FileOutputStream out = new FileOutputStream(f);
				out.write(body.getBytes());
				out.close();
				return true;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return false; 
	}

}
