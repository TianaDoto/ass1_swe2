package blockchain;

import java.security.*;
import java.security.spec.ECGenParameterSpec;

public class Wallet {
	public PublicKey publickey;
	private PrivateKey privatekey;
	private float Balance;
	
	public Wallet(float Balance) {
		this.Balance=Balance;
		
	}
	
	public void generateKeyPair() {
		try {
			KeyPairGenerator keyGen = KeyPairGenerator.getInstance("ECDSA","BC");
			SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
			ECGenParameterSpec ecSpec = new ECGenParameterSpec("prime192v1");
			// Initialize the key generator and generate a KeyPair
			keyGen.initialize(ecSpec, random); //256
	        KeyPair keyPair = keyGen.generateKeyPair();
	        //Set the public and private keys from the keyPair
	        this.privatekey = keyPair.getPrivate();
	        this.publickey = keyPair.getPublic();
	        
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
	
	public PrivateKey getPrivateKey() {
		return this.privatekey;
	}
	public float getBalance() {
		return this.Balance;
	}
}
