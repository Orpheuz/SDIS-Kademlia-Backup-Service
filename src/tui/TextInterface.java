package tui;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import routing.Routing;
import subprotocols.Backup;
import subprotocols.Delete;
import subprotocols.Restore;
import utils.Database;
import utils.HashCalc;
import dht.DHT;

public class TextInterface {

	static class BackedFile {
		public int repDeg;
		public String file;
	}
	
	public static ExecutorService threadManager;
	public static DHT dht;
	static ArrayList<BackedFile> files;
	public static Database database;


	public static void main(String[] args) throws IOException, ClassNotFoundException {
		threadManager = Executors.newCachedThreadPool();
		dht = new DHT();
		database = new Database("database.db");
		files = new ArrayList<TextInterface.BackedFile>();
		while (true) {
			System.out.println("table:"+Routing.getNodes());
			System.out.println("Wellcome to Kademlia Backup Service\n1.Backup file\n2.Restore file\n3.Delete file");
			int c;
			try {
				c = Integer.parseInt(System.console().readLine());
			} catch (Exception e) {
				System.out.println("please do good input\n");
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
							System.out.println("please do good input\n");
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
				threadManager.submit(new Backup(new File(filePathString), rp));
				break;
			}
			case 2: {
				if(files.isEmpty())
				{
					System.out.println("Can't restore, no file backed up");
					break;
				}
				System.out.println("Which file?");
				System.out.println(printFiles());
				int n;
				try {
					n = Integer.parseInt(System.console().readLine()) -1 ;
				} catch (Exception e) {
					System.out.println("please do good input\n");
					continue;
				}
				//TODO Restore
				threadManager.submit(new Restore(files.get(n).file));
				
				
				break;
			}
			case 3: {
				if(files.isEmpty())
				{
					System.out.println("Can't delete, no file backed up");
					break;
				}
				System.out.println("Which file?");
				System.out.println(printFiles());
				int n;
				try {
					n = Integer.parseInt(System.console().readLine()) -1;
				} catch (Exception e) {
					System.out.println("please do good input\n");
					continue;
				}
				String s = HashCalc.generateFileID(files.get(n).file);
				files.remove(n);
				
				threadManager.submit(new Delete(s));
				break;
			}
			default:
				System.out.println("please do good input\n");
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
