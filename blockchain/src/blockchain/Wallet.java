package blockchain;

import java.security.*;

public class Wallet {
	public PublicKey publickey;
	private PrivateKey privatekey;
	private float Balance;
	
	public Wallet(float Balance) {
		this.Balance=Balance;
		
	}
	
	public void generateKeyPair() {
		try {
			KeyPairGenerator keyGen = KeyPairGenerator.getInstance("DSA", "SUN");
			SecureRandom random = SecureRandom.getInstance("SHA1PRNG", "SUN");
			keyGen.initialize(1024, random);
			KeyPair pair = keyGen.generateKeyPair();
			privatekey = pair.getPrivate();
			publickey = pair.getPublic();
	        
		}catch(Exception e) {
			throw new RuntimeException(e);
		}
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
	
	public void setBalance(float b) {
		this.Balance=b;
	}
}
