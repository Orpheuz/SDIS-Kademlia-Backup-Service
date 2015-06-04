package listeners;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class ReceivingThread implements Runnable {

	private ServerSocket socket;
	private Socket readSocket;
	private InputStream stream;

	public ReceivingThread(int port) {
		try {
			socket = new ServerSocket(port);
		} catch (IOException e) {
			System.out.println("Failed to open connection on port " + port);
			return;
		}

	}

	@Override
	public void run() {

		while (true) {
			try {
				readSocket = socket.accept();
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