package subprotocols;

import java.util.ArrayList;
import java.util.TreeSet;

import listeners.WriteThread;
import message.DeleteMessage;
import node.Node;
import tui.TextInterface;
import dht.DHT;

public class Delete implements Runnable {

	String fileID;
	
	public Delete(String fileID) {
		super();
		this.fileID = fileID;
	}

	@Override
	public void run() {
		ArrayList<byte[]> owners = TextInterface.dht.whoHasIt(fileID);
		System.out.println(owners.size());
		for(int i = 0; i < owners.size(); i++) {
			Lookup lp = new Lookup(owners.get(i));
			TreeSet<Node> nodes = lp.run();
			Node node = nodes.first();
			DeleteMessage message = new DeleteMessage(fileID);
			TextInterface.threadManager.submit(new WriteThread(message.getMessage(), node.getIP(), node.getPort()));
			TextInterface.dht.removeFile(fileID);	
		}
	}

	
}
