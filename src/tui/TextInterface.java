package tui;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.concurrent.ExecutorService;

public class TextInterface {

	static class BackedFile {
		public int repDeg;
		public String file;
	}
	
	public static ExecutorService threadManager;
	static ArrayList<BackedFile> files;

	public static void main(String[] args) {
		files = new ArrayList<TextInterface.BackedFile>();
		while (true) {
			System.out.println("Wellcome to Kademlia Backup Service\n1.Backup file\n2.Restore file\n3.Delete file");
			int c;
			try {
				c = Integer.parseInt(System.console().readLine());
			} catch (Exception e) {
				System.out.println("pliz do good input\n");
				continue;
			}
			switch (c) {
			case 1: {
				int rp;
				String filePathString;
				while (true) {
					System.out.println("Input filename:");
					filePathString = System.console().readLine();
					File f = new File(filePathString);
					if (f.exists() && !f.isDirectory()) {
						System.out.println("Input repdeg:");
						try {
							rp = Integer.parseInt(System.console().readLine());
						} catch (Exception e) {
							System.out.println("pliz do good input\n");
							continue;
						}
						break;
					}
					System.out.println("File no exist");
				}
				BackedFile b = new BackedFile();
				b.file = filePathString;
				b.repDeg = rp;
				files.add(b);
				// TODO backup
				break;
			}
			case 2: {
				if(files.isEmpty())
				{
					System.out.println("Can't restore, no file bacupados");
					break;
				}
				System.out.println("Which file?");
				System.out.println(printFiles());
				int n;
				try {
					n = Integer.parseInt(System.console().readLine());
				} catch (Exception e) {
					System.out.println("pliz do good input\n");
					continue;
				}
				//TODO Restore
				break;
			}
			case 3: {
				if(files.isEmpty())
				{
					System.out.println("Can't delete, no file bacupados");
					break;
				}
				System.out.println("Which file?");
				System.out.println(printFiles());
				int n;
				try {
					n = Integer.parseInt(System.console().readLine());
				} catch (Exception e) {
					System.out.println("pliz do good input\n");
					continue;
				}
				files.remove(n);
				
				//TODO Delete

				break;
			}
			default:
				System.out.println("pliz do good input\n");
				break;
			}
		}
	}

	static private String printFiles() {
		String str = "";
		for (int i = 0; i < files.size(); i++) {
			str += (i+1) + ":" + files.get(i).file + "\n";
		}
		return str;
	}
}
