package node;


public class NodeTriplet {
	int id, port;
	String ip;
	NodeTriplet(int id, int port, String ip) {
		this.id = id;
		this.ip = ip;
		this.port = port;
	}
	public int getId() {
		return id;
	}
	public int getPort() {
		return port;
	}
	public String getIp() {
		return ip;
	}
	
	
}
