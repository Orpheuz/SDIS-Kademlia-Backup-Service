package node;


public class NodeTriplet {
<<<<<<< HEAD
	int id, port;
	String ip;
	NodeTriplet(int id, int port, String ip) {
		this.id = id;
=======
	byte[] id;
	int port;
	InetAddress ip;
	NodeTriplet(byte[] iD2, int port, InetAddress ip) {
		this.id = iD2;
>>>>>>> branch 'master' of https://github.com/Orpheuz/SDIS-Kademlia-Backup-Service.git
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
