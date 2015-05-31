package message;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketAddress;

public class Message {

	public String receive(DatagramSocket socket) {
		
		byte[] buffer = new byte[100000];
		DatagramPacket message = new DatagramPacket(buffer, buffer.length);
		try {
			socket.receive(message);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return (new String(message.getData(), 0, message.getLength()));
	}
	
	public void send(byte[] message, DatagramSocket socket, InetAddress ip, int port) {
		DatagramPacket msgPckt = new DatagramPacket(message, message.length, ip, port);
		try {
			socket.send(msgPckt);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}