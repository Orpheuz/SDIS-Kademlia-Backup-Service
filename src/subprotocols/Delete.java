package subprotocols;

import java.util.ArrayList;

import tui.TextInterface;

public class Delete implements Runnable {

	String fileID;
	
	public Delete(String fileID) {
		super();
		this.fileID = fileID;
	}

	@Override
	public void run() {
		ArrayList<byte[]> owners = TextInterface.dht.whoHasIt(fileID);
		for(int i = 0; i < owners.size(); i++) {
			Lookup lp = new Lookup(owners.get(i));
		}
		//TextInterface.threadManager.submit(new WriteThread());
	}

	
}
