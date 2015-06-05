package listeners;

import handlers.BackupHandler;
import handlers.DeleteHandler;
import handlers.FindNodeHandler;
import handlers.PingHandler;
import handlers.RestoreHandler;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

import tui.TextInterface;
import message.DeleteMessage;
import message.FindNodeMessage;
import message.FindNodeResponse;
import message.Message;
import message.Parser;
import message.PingMessage;
import message.PutChunkMessage;
import message.RestoreMessage;
import message.RestoreResponse;

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
				System.out.println("message recieved");
				stream = readSocket.getInputStream();
				byte[] message = new byte[65000];
				int count = stream.read(message);
				if (count > 0) {
					if (count != 65000) {
						byte[] smallerData = new byte[count];
						System.arraycopy(message, 0, smallerData, 0, count);
						message = smallerData;
					}
					Parser p = new Parser(message);
					Message cMessage = p.parseMessage();
					System.out.println(readSocket.getInetAddress());
					System.out.println(readSocket.getPort());
					switch (Integer.parseInt(p.header[0])) {
					case Message.PUTCHUNK_MSG:
						System.out.println("Isto foi putchunk");
						TextInterface.threadManager.submit(new BackupHandler((PutChunkMessage) cMessage));
						break;
					case Message.RESTORE_MSG:
						TextInterface.threadManager.submit(new RestoreHandler((RestoreMessage) cMessage, readSocket.getInetAddress()));
						break;
					case Message.RESTORE_RSP:
						TextInterface.threadManager.submit(new RestoreHandler((RestoreResponse) cMessage, readSocket.getInetAddress()));
						break;
					case Message.DELETE_MSG:
						TextInterface.threadManager.submit(new DeleteHandler(((DeleteMessage) cMessage).getFileID()));
						break;
					case Message.PING_MSG:
							TextInterface.threadManager.submit(new PingHandler((PingMessage) cMessage, readSocket.getInetAddress()));
						break;
					case Message.FINDNODE_MSG:
						TextInterface.threadManager.submit(new FindNodeHandler((FindNodeMessage) cMessage, readSocket.getInetAddress()));
						break;
					case Message.FINDNODE_RSP:
						TextInterface.threadManager.submit(new FindNodeHandler((FindNodeResponse) cMessage, readSocket.getInetAddress()));
						break;
					default:
						break;
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
