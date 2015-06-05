package utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class HashCalc {

    public static byte[] sha1Hash(String toHash) throws NoSuchAlgorithmException {
    	
        MessageDigest md = MessageDigest.getInstance("SHA-1");

        md.update(toHash.getBytes());

        return md.digest();
    }

    public static byte[] sha1Hash(String toHash, String salt) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-1");

        md.update(toHash.getBytes());

        return md.digest(salt.getBytes());
    }
    
    public static String generateFileID(String filename) throws IOException {
		File f = new File(filename);
		Path file = f.toPath();
		BasicFileAttributes attr = Files.readAttributes(file, BasicFileAttributes.class);
		String temp = filename + attr.lastModifiedTime();
		String fileID = new String();
		MessageDigest digest;
		try {
			digest = MessageDigest.getInstance("SHA-256");
			byte[] hash = digest.digest(temp.getBytes());
			StringBuffer hexString = new StringBuffer();
			for (int i = 0; i < hash.length; i++) {
				String hex = Integer.toHexString(0xff & hash[i]);
				if (hex.length() == 1)
					hexString.append('0');
				hexString.append(hex);
			}
			fileID = hexString.toString();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return fileID;
	}
}
