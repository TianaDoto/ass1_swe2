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
			StringBuffer hexString = new StringBuffer(); // This will contain hash as hexidecimal
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
		byte[] encodedFrom = data.from.getEncoded();
		byte[] encodedTo = data.to.getEncoded();
		String b64From = Base64.getEncoder().encodeToString(encodedFrom);
		String b64To = Base64.getEncoder().encodeToString(encodedTo);
		String beg = new String(new char[difficulty]).replace('\0', '0');
		
		while(!hash.substring( 0, difficulty).equals(beg))
		{
			String input = prevHash +Long.toString(nonce) + Float.toString(data.amount) + b64From + b64To ;
			this.hash = hash_block(input);
			nonce++;
		}
		return this.hash;
				
	}
}