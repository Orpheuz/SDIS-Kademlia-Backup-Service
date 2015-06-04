package protocols;

import java.io.IOException;

import message.Write;
import node.NodeTriplet;

public class Kademlia {

	public static boolean Ping(/*NodeTriplet sender,*/ NodeTriplet receiver,int timeInterval) throws IOException{

		String response = Write.send("ping", receiver.getIp(), receiver.getPort(), timeInterval);
		System.out.println(response);
		return true;
	}

}
