package blockchain;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.Writer;
import java.net.Socket;

public class Client implements Runnable {

	@Override
	public void run() {
		Socket client1 = null;
		String server_address = "";
		boolean connected = false;
		
		// Connection from peer file.
		File peer_file = new File(System.getProperty("user.home")+"/blockchain/peer.txt");
		if (peer_file.exists()) {
			try {
				BufferedReader br = new BufferedReader(new FileReader(System.getProperty("user.home")+"/blockchain/peer.txt"));
				server_address = br.readLine();
				
				client1 = new Socket(server_address, 4321);
				System.out.print(" "+server_address+": Connected!\n");
				connected = true;
				
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		// Connection by IP Scanning.
		else {
			for(int x=0; x<256; x++) {
				for(int y=0; y<256; y++) {
					server_address = "127.0."+String.valueOf(x)+"."+String.valueOf(y);
					System.out.print("Scanning IP...");
					try {
						client1 = new Socket(server_address, 4321);
						System.out.print(" "+server_address+": Connected!\n");
						connected = true;
						Writer writer = new FileWriter(System.getProperty("user.home")+"/blockchain/peer.txt");
						writer.write(server_address+"\n");
						writer.close();
						break;
					} catch (IOException e) {
						System.out.print(" "+server_address+": failed.\n");
						continue;
					}
				}
				if(connected) break;
			}
		}
		
		if(connected) {
			try {
				PrintWriter client1_out = new PrintWriter(client1.getOutputStream(), true);
				BufferedReader client1_in = new BufferedReader(new InputStreamReader(client1.getInputStream())); 
				client1_out.println("publickey");
				while(true) {
					String line = client1_in.readLine();
					//if(line.length() == 0) break;
					System.out.println("Line Received from "+server_address+": "+line);
				}
			} catch(Exception e) {
				System.out.println("Failed to read data from server!");
			}
		}else {
			System.out.println("No peers Found!");
		}
		
	}

}
