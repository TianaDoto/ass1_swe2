package blockchain;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.security.*;
import java.security.spec.X509EncodedKeySpec;
import java.util.ArrayList;
import java.util.Base64;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class Wallet {
	public String publickey;
	private String privatekey;
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
			
			Base64.Encoder encoder = Base64.getEncoder();
			privatekey = encoder.encodeToString(pair.getPrivate().getEncoded());
			publickey = encoder.encodeToString(pair.getPublic().getEncoded());
	        
		}catch(Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	void Update(float amount) {
		this.Balance-=amount;
	}
	
	public void readwallet() {
		try {
			BufferedReader br = new BufferedReader(new FileReader(System.getProperty("user.home")+"/blockchain/wallet.json"));
			
			Gson gson = new Gson();
			ArrayList<String> walletJSON = gson.fromJson(br, ArrayList.class);
			
			this.publickey = walletJSON.get(0);
			this.privatekey = walletJSON.get(1);
			this.Balance = Float.parseFloat(walletJSON.get(2));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
	}
	
	public void writewallet() {
		Base64.Encoder encoder = Base64.getEncoder();
		String encoded_publicKey = this.publickey;
		String encoded_privateKey = this.privatekey;
		
		ArrayList<String> wallet = new ArrayList<String>();
		wallet.add(encoded_publicKey);
		wallet.add(encoded_privateKey);
		wallet.add(String.valueOf(this.Balance));
		
		try (Writer writer = new FileWriter(System.getProperty("user.home")+"/blockchain/wallet.json")) {
		    Gson gson = new GsonBuilder().create();
		    gson.toJson(wallet, writer);
		} catch (IOException e) {
			
		}
	}
	public float getBalance() {
		return this.Balance;
	}
	
	public void setBalance(float b) {
		this.Balance=b;
	}
}
