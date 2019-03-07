package blockchain;

import java.security.*;

public class Transaction {
	public PublicKey sender; //Senders address/public key.
	public PublicKey reciever;
	public String transactionId;
	public float amount;
	
	public Transaction(PublicKey s,PublicKey r,float A) {
		sender=s;
		reciever=r;
		amount=A;
	}
	
	boolean validateTransaction(Wallet w) {
		if(amount>= w.getBalance()) {
			return true;
		}
		else return false;
	}
}
