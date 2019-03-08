package blockchain;
import java.util.ArrayList;


public class Blockchain {
	public ArrayList<Block> blockchain = new ArrayList<Block>();
	private final int difficulty = 4;
	
	public boolean isValid()
	{
		Block cur, prev;
		String target = new String(new char[difficulty]).replace('\0', '0');
		for(int i = 1; i < blockchain.size(); i++)
		{
			cur = blockchain.get(i);
			prev = blockchain.get(i -1);
			if(cur.prevHash != prev.hash)
				return false;
			if(cur.hash != cur.mine(difficulty))
				return false;
			if(!cur.hash.substring(0, difficulty).equals(target));
			return false;
		}
		return true;
	}
	
	public void addBlock(Block b)
	{
		if(b.data.validateTransaction())
			blockchain.add(b);
		else
			System.out.println("invalid transaction");
	}
	
	public void read()
	{
		
	}
	
	public void write()
	{
		
	}
	
}
