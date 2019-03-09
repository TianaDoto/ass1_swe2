package blockchain;

import java.security.MessageDigest;
import java.util.Base64;

public class Block {
	public static int index;
	public Transaction data;
	public long nonce;
	public String hash;
	public String prevHash;
	
	public Block(Transaction data, String prevHash)
	{
		Block.index++;
		this.data = data;
		this.prevHash = prevHash;
		this.nonce = 0;
		this.hash = new String();
	}
	
	String hash_block(String input)
	{
		try {
			MessageDigest digest = MessageDigest.getInstance("SHA-256");	        
			//Applies sha256 to our input, 
			byte[] hash = digest.digest(input.getBytes("UTF-8"));	        
			StringBuffer hexString = new StringBuffer();
			for (int i = 0; i < hash.length; i++) {
				String hex = Integer.toHexString(0xff & hash[i]);
				if(hex.length() == 1) hexString.append('0');
				hexString.append(hex);
			}
			return hexString.toString();
		}
		catch(Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	public String mine(int difficulty)
	{
		String b64From = data.sender;
		String b64To = data.reciever;
		String beg = new String(new char[difficulty]).replace('\0', '0');
		
		do
		{
			String input = prevHash +Long.toString(nonce) + Float.toString(data.amount) + b64From + b64To ;
			this.hash = hash_block(input);
			nonce++;
		}		while(!hash.substring( 0, difficulty).equals(beg));
		return this.hash;
				
	}
}