package blockchain;

import java.util.Base64;
import com.google.gson.GsonBuilder;
public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Wallet a = new Wallet(100); //sender
		Wallet b = new Wallet(0); //receiver
		a.generateKeyPair();
		b.generateKeyPair();
		
		Transaction t = new Transaction(a, b, 50);
		Transaction t2 = new Transaction(a, b, 100);
		
		
		System.out.println(t2.validateTransaction());
		Block c= new Block(t,"0");
		Block c2= new Block(t2, c.hash);
		c.mine(4);
		c2.mine(4);
		Blockchain bc = new Blockchain();
		bc.addBlock(c);
		bc.addBlock(c2);
		
		String blockchainJson = new GsonBuilder().setPrettyPrinting().create().toJson(bc);
		System.out.println(blockchainJson);
	}

}
