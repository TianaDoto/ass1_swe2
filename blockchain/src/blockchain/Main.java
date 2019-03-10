package blockchain;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Base64;
import com.google.gson.GsonBuilder;

import blockchain.Client;
import blockchain.Server;

public class Main {

	public static void main(String[] args) {
		//Initial Setup
		File dir = new File(System.getProperty("user.home")+"/blockchain");
		if (!dir.exists() || !dir.isDirectory()) {
		   dir.setWritable(true);
		   dir.mkdir();
		}
		
		
		//P2P Server
		Server server = new Server();
		Thread srv = new Thread(server);
		srv.start();
		
		//P2P Client
		Client  client = new Client();
		Thread cln = new Thread(client);
		cln.start();
		
		System.out.println("P2P Connection Started!");
		
		
		// Wallet Retrieval
		File wallet_file = new File(System.getProperty("user.home")+"/blockchain/wallet.json");
		Wallet a = new Wallet(0);
		if (!wallet_file.exists()) {
			a = new Wallet(0); //sender
			a.generateKeyPair();
			a.writewallet();
		}
		else {
			a.readwallet();
		}
		/*
		//System.out.println(a.publickey);
		Wallet a = new Wallet(300);
		Wallet b = new Wallet(0); //receiver
		a.generateKeyPair();
		b.generateKeyPair();
		
		Transaction t = new Transaction(a, b, 50);
		Transaction t2 = new Transaction(a, b, 100);
		
		
		//System.out.println(t2.validateTransaction());
		Block c= new Block(t,"0");
		Block c2= new Block(t2, c.hash);
		c.mine(4);
		c2.mine(4);
		
		Blockchain bc = new Blockchain();
		Blockchain bc1 = new Blockchain();
		bc.addBlock(c);
		bc.addBlock(c2);
		
		bc1.addBlock(c);
		
		System.out.println(bc.equal(bc1));
		
		bc1.update(bc);
		
		System.out.println(bc.equal(bc1));
		String blockchainJson = new GsonBuilder().setPrettyPrinting().create().toJson(bc);
		System.out.println(blockchainJson);*/
		
	}

}
