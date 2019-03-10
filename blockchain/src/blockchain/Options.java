package blockchain;

import java.io.BufferedReader;
import java.io.FileReader;
import java.net.Inet4Address;
import java.util.Scanner;

public class Options {
	public static void main(String args[]) {
		option_service();
	}
	
	private static void option_service() {
		print_main_menu();
		Scanner in = new Scanner(System.in);
		String selection = in.nextLine();
		if(selection.equals("1")) {
			System.out.println("Please enter the Public Key of the recipient:");
			String recipient= in.nextLine();
			System.out.println("Please enter the Amount:");
			float amount=in.nextFloat();
			System.out.println("Please wait...");
			Wallet wallet= new Wallet(0);
			wallet.readwallet();
			Blockchain nbc= new Blockchain();
			nbc.read();
			Transaction t= new Transaction(wallet,recipient,amount);
			if(t.validateTransaction()) {
				Block b= new Block(t,nbc.blockchain.get(nbc.blockchain.size()-1).hash);
				b.mine(4);
				nbc.addBlock(b);
				nbc.write();
				System.out.println("Your Transaction is Completed");
			}
			else {
				System.out.println("Invalid Transaction");
			}
			System.out.println("\n");
			option_service();
		}
		else if(selection.equals("2")) {
			Wallet w = new Wallet(0);
			w.readwallet();
			System.out.println("Your Balance is :"+w.getBalance()+"\n");
			option_service();
		}
		else if(selection.equals("3")) {
			System.out.println("Your personal information: ");
			Wallet w = new Wallet(0);
			w.readwallet();
			System.out.println("Balance :"+w.getBalance()+"");
			System.out.println("Public Key :"+w.publickey+"");
			try {
				BufferedReader br = new BufferedReader(new FileReader(System.getProperty("user.home")+"/blockchain/peer.txt"));
				System.out.println("Your IP Address: "+Inet4Address.getLocalHost().getHostAddress());
				System.out.println("Current Peer: "+br.readLine()+"\n");
			} catch (Exception e) {
				System.out.println("Error in reading file and IP!");
			}
			option_service();
		}
		else if(selection.equals("4")) {
			option_service();
		}
		else if(selection.equals("0")) {
			return;
		}
		else {
			System.out.println("Invalid Input");
			option_service();
		}
		in.close();
	}
	private static void print_main_menu() {
		System.out.println("Main Menu:");
		System.out.println("1 => Send Money.");
		System.out.println("2 => Check Balance.");
		System.out.println("3 => Show Your Information");
		System.out.println("4 => Print Menu.");
		System.out.println("0 => Close Program.");
	}
}
