package message;

public class Parser {

	public static final String VERSION = "1.0";
	public static final String CHUNK_MSG = "CHUNK";
	public static final String GETCHUNK_MSG = "GETCHUNK";
	public static final String REMOVED_MSG = "REMOVED";
	public static final String DELETE_MSG = "DELETE";
	public static final String PUTCHUNK_MSG = "PUTCHUNK";
	public static final String STORED_MSG = "STORED";
	public static final String CRLF = "\r\n";
	public static final String SEPARATOR = " ";

	public static byte[] buildPutChunkMessage(String fileID, String chunkNo,
			String repDeg, String body) {
		return (PUTCHUNK_MSG + SEPARATOR + VERSION + SEPARATOR + fileID
				+ SEPARATOR + chunkNo + SEPARATOR + repDeg + SEPARATOR + CRLF
				+ CRLF + body).getBytes();
	}

	public static byte[] buildGetChunkMessage(String fileID, String chunkNo) {
		return (GETCHUNK_MSG + SEPARATOR + VERSION + SEPARATOR + fileID
				+ SEPARATOR + chunkNo + SEPARATOR + CRLF + CRLF).getBytes();
	}

	public static byte[] buildChunkMessage(String fileID, String chunkNo,
			byte[] body) {
		byte[] message = (CHUNK_MSG + SEPARATOR + VERSION + SEPARATOR + fileID
				+ SEPARATOR + chunkNo + SEPARATOR + CRLF + CRLF).getBytes();
		byte[] result = new byte[message.length + body.length];
		System.arraycopy(message, 0, result, 0, message.length);
		System.arraycopy(body, 0, result, message.length, body.length);

		return result;
	}

	public static byte[] buildStoredMessage(String fileID, String chunkNo) {
		return (STORED_MSG + SEPARATOR + VERSION + SEPARATOR + fileID
				+ SEPARATOR + chunkNo + SEPARATOR + CRLF + CRLF).getBytes();
	}

	public static byte[] buildDeletedMessage(String fileId) {
		return (DELETE_MSG + SEPARATOR + VERSION + SEPARATOR + fileId
				+ SEPARATOR + CRLF + CRLF).getBytes();
	}

}

