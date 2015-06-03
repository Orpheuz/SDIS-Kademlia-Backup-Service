package message;

import java.io.IOException;

public class Message {

	public static void main(String[] args) throws InterruptedException, IOException {
		

		Read bRead= new Read(8000);
		bRead.start();
		
		Read pingRead= new Read(8001);
		pingRead.start();

		BParser parser = new BParser(bRead.messages);
		parser.start();

	}
}
