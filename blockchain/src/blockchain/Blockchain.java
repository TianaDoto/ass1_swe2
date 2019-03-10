package blockchain;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Base64;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


public class Blockchain {
	public ArrayList<Block> blockchain = new ArrayList<Block>();
	private final int difficulty = 4;
	
	public boolean isValid()
	{
		if(this.blockchain.size() > 2) {
			Block cur, prev;
			for(int i = 2; i < blockchain.size(); i++)
			{
				cur = blockchain.get(i);
				prev = blockchain.get(i -1);
				if(!cur.prevHash.equals(prev.hash))
					return false;
			}
			return true;
		}
		else return true;
	}
	
	public void addBlock(Block b)
	{
		if(b.data.validateTransaction())
			blockchain.add(b);
		else
			System.out.println("invalid transaction");
	}
	
	public Blockchain read()
	{
		try {
			BufferedReader br = new BufferedReader(new FileReader(System.getProperty("user.home")+"/blockchain/blockchain.json"));
			
			Gson gson = new Gson();
			Blockchain blockchain = gson.fromJson(br, Blockchain.class);
			
			this.blockchain = blockchain.blockchain;

		} catch (FileNotFoundException e) {
			System.out.println("Unable to read Blockchain!");
		}
		return this;
	}
	
	public void write()
	{
		try (Writer writer = new FileWriter(System.getProperty("user.home")+"/blockchain/blockchain.json")) {
		    Gson gson = new GsonBuilder().create();
		    gson.toJson(this, writer);
		} catch (IOException e) {
			System.out.print("Faild to update Blockchain!");
		}
	}
	
}
