package protocols;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;


public class Backup {
	public static void storeChunk(String fileID, int chunkN, int repDegree, byte[] body) throws IOException {
		File f = new File(fileID + File.separator + chunkN);
		f.getParentFile().mkdirs();
		FileOutputStream file = new FileOutputStream(f);
		file.write(body);
		file.close();
		//sendSTMsg(fileID, chunkN);

	}
}
