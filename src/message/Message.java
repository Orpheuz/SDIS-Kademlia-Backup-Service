package message;

import java.io.IOException;

public class Message {

	public static void main(String[] args) throws InterruptedException, IOException {
		

		Read bRead= new Read(8000);
		bRead.start();
		
		Read rRead= new Read(8001);
		rRead.start();

		BParser bparser = new BParser(bRead.messages);
		bparser.start();
		
		RParser rparser = new RParser(rRead.messages);
		rparser.start();

	}
}
