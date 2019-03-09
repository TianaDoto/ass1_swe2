package blockchain;

import java.security.*;

public class Transaction {
	public String sender; //Senders address/public key.
	public String reciever;
	public String transactionId;
	public boolean state = false;
	public float amount;
	
	public Transaction(Wallet s,Wallet r,float A) {
		sender=s.publickey;
		reciever=r.publickey;
		
		amount=A;
		if(amount <= s.getBalance()) {
			s.setBalance(s.getBalance()-A);
			r.setBalance(r.getBalance()+A);
			state= true;
		}
		else state= false;
	}
	
	boolean validateTransaction() {
		return state;
	}
}
