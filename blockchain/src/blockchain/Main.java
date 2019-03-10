package blockchain;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Scanner;

import com.google.gson.Gson;
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
		
		// Wallet Retrieval
		File wallet_file = new File(System.getProperty("user.home")+"/blockchain/wallet.json");
		Wallet a = new Wallet(0);
		if (!wallet_file.exists()) {
			a = new Wallet(100); //sender
			a.generateKeyPair();
			a.writewallet();
		}
		else {
			a.readwallet();
		}
		
		//Genesis Block
		File blockchain_file = new File(System.getProperty("user.home")+"/blockchain/blockchain.json");
		if(!blockchain_file.exists()) {
			Transaction init_transaction = new Transaction(a, 100);
			Block c= new Block(init_transaction,"0");
			c.mine(4);
			Blockchain bc = new Blockchain();
			bc.addBlock(c);
			bc.write();
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
		
		//option_service();
		//System.out.println(blockchainJson);
		
		//Blockchain blc = new Gson().fromJson(blockchainJson, Blockchain.class);
		//System.out.println(blc.blockchain.get(blc.blockchain.size()-1).hash);
	}

}
