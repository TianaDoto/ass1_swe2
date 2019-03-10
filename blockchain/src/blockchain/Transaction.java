package blockchain;


public class Transaction {
	public String sender; //Senders address/public key.
	public String reciever;
	public String transactionId;
	public boolean state = false;
	public float amount;
	
	public Transaction(Wallet s,String pk,float A) {
		sender=s.publickey;
		reciever=pk;
		
		amount=A;
		if(amount <= s.getBalance()) {
			s.setBalance(s.getBalance()-A);
			state= true;
		}
		else state= false;
	}
	
	public Transaction(Wallet r,float A) {
		sender="Initial Balance";
		reciever=r.publickey;
		
		amount=A;
		r.setBalance(r.getBalance()+A);
		state= true;
	}
	
	boolean validateTransaction() {
		return state;
	}
}
