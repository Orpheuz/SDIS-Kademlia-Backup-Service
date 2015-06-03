package message;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Read extends Thread{
	int port;
	private ServerSocket srvSocket;
	private Socket readSocket;
	InputStream stream;
	ArrayList<byte[]> messages;

	public Read(int port){
		this.port = port;
		messages = new ArrayList<byte[]>();
	}

	public void run() {

		try {
			srvSocket = new ServerSocket(port);
		} catch (IOException e) {
			System.out.println("Failed to open connection on port + " + port);
			return;
		}


		while(true){
			try {
				readSocket = srvSocket.accept();
			} catch (IOException e) {
				System.out.println("Failed to accept connection");
			}

			try {
				stream = readSocket.getInputStream();
				byte[] msg = new byte[65000];
				int count = stream.read(msg);

				if(count > 0){
					if (count != 65000) {
						byte[] smallerData = new byte[count];
						System.arraycopy(msg, 0, smallerData, 0, count);
						msg = smallerData;
					}
					messages.add(msg);
				}
			} catch (IOException e) {
				System.out.println("Failed to read line");
			}


			try {
				stream.close();
			} catch (IOException e) {
				System.out.println("Failed to close buffer");
			}

		}

	}


}
