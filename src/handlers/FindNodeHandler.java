package handlers;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;

import node.Node;
import node.NodeTriplet;
import routing.Routing;
import subprotocols.Lookup;
import listeners.WriteThread;
import message.FindNodeMessage;
import message.FindNodeResponse;

public class FindNodeHandler implements Runnable {

  private byte[] targetid;
  private int n;
  InetAddress ip;
  boolean type;
  ArrayList<Node> nodes;
  FindNodeMessage cMessage;
  FindNodeResponse rMessage;

  public FindNodeHandler(FindNodeMessage cMessage, InetAddress ip) {
    type = true;
    this.cMessage = cMessage;
    process();
  }

  public FindNodeHandler(FindNodeResponse rMessage, InetAddress ip) {
    type = false;
    this.rMessage = rMessage;
    process();
  }

  private void process() {
    if (type) {
      nodes = null;
      targetid=cMessage.getKey();
      // TODO LER O TARGET
    } else {
      targetid = null;
      nodes=rMessage.nodes;
    }
  }
  
  @Override
  public void run() {
    if (type) {
      List<Node> l = Routing.findClosest(targetid, n);
      ArrayList<NodeTriplet> al = new ArrayList<NodeTriplet>();
      for (Node node : l) {
        al.add(new NodeTriplet(node.getId(), node.getPort(), node
            .getIP()));
      }
      FindNodeResponse message = new FindNodeResponse(al);
      WriteThread wt = new WriteThread(message.getMessage(), ip, cMessage.getPort());
      Thread t = new Thread(wt);
      t.start();
    } else {
      if (Lookup.listening)
        Lookup.looked = nodes;
    }
  }
}
