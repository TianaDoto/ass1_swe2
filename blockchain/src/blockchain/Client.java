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
import java.net.Inet4Address;
import java.net.Socket;

import com.google.gson.Gson;

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
				System.out.print(server_address+": Connected!\n");
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
				//Self Information
				System.out.println("Your IP Address: "+Inet4Address.getLocalHost().getHostAddress());
				Wallet w = new Wallet(0);
				w.readwallet();
				System.out.println("Your Balance is: "+w.getBalance());
				System.out.println("Your Public Key: "+w.publickey+"\n");
				
				//Peer's information
				System.out.println("You are currently connected to: "+client1.getRemoteSocketAddress());
				System.out.println("Requesting Public Key from "+server_address+" ...");
				client1_out.println("publickey");
				String line="";
				while((line = client1_in.readLine()) != null) {
					System.out.println(server_address+": "+line);
					client1_out.println("continue");
					break;
				}
				
				refresh(client1_out, client1_in, server_address);
				
			} catch(Exception e) {
				System.out.println("Failed to read data from server!");
			}
		}else {
			System.out.println("No peers Found!");
		}
		
	}
	
	private void refresh(PrintWriter client1_out, BufferedReader client1_in, String server_address) {
		//System.out.println("Fetching Blockcahain from "+server_address+" ...");
		client1_out.println("blockchain");
		String line="";
		try {
			while((line = client1_in.readLine()) != null) {
				Blockchain peerchain = new Gson().fromJson(line, Blockchain.class);
				Blockchain selfchain = new Blockchain().read();
				
				if(selfchain.blockchain.size() == 1 && peerchain.isValid() && peerchain.blockchain.size()>0) {
					Wallet wallet = new Wallet(0);
					wallet.readwallet();
					if(wallet.calculate_balance(peerchain) == -1.0f) {
						Transaction init_transaction = new Transaction(wallet, 100);
						String prev_hash = peerchain.blockchain.get(peerchain.blockchain.size()-1).hash;
						Block c= new Block(init_transaction, prev_hash);
						c.mine(4);
						Blockchain bc = peerchain;
						bc.addBlock(c);
						bc.write();
						wallet.Update(wallet.calculate_balance(bc));
						wallet.writewallet();
						System.out.println("Your initial balance is confirmed!\nNow your balance is: "+wallet.getBalance());
					}
				}
				else if(selfchain.blockchain.size() < peerchain.blockchain.size() && peerchain.isValid()) {
					System.out.println("New Transactions: \n");
					for(int x=selfchain.blockchain.size()-1; x<peerchain.blockchain.size(); x++) {
						System.out.println(x+" : "+peerchain.blockchain.get(x).data.amount+" => Hash: "+peerchain.blockchain.get(x).hash+" === "+peerchain.blockchain.get(x).data.sender+" to "+peerchain.blockchain.get(x).data.reciever+"---");
					}
					peerchain.write();
				}
				
				synchronized(this) {
			        try {
			            wait(3000);
			        } catch (InterruptedException e) { }
			    }
				client1_out.println("continue");
				break;
			}
		} catch(Exception e) {
			System.out.println("Failed to read data from server!");
		}
		refresh(client1_out, client1_in, server_address);
	}

}
