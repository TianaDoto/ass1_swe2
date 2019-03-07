package blockchain;

import java.security.*;

public class Wallet {
	public PublicKey publickey;
	private PrivateKey privatekey;
	private float Balance;
	
	public Wallet(float Balance) {
		this.Balance=Balance;
		
	}
	
	void Update(float amount) {
		this.Balance-=amount;
	}
	
	public Wallet readwallet() {
		Wallet w= new Wallet(0);
		//read Json process
		return w;
	}
	
	public void writewallet(Wallet w) {
		//process of writing w to Json file
	}
	public float getBalance() {
		return this.Balance;
	}
}
