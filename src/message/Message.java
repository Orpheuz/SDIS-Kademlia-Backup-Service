package message;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

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
	
	public void send(byte[] message, DatagramSocket socket) {
		DatagramPacket msgPckt = new DatagramPacket(message, message.length);
		try {
			socket.send(msgPckt);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
