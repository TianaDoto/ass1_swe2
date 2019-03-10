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
			if(!cur.prevHash.equals(prev.hash))
				return false;
			if(!cur.hash.equals(cur.mine(difficulty)))
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
	
	public boolean equal(Blockchain b)
	{
		if(b.blockchain.size() == blockchain.size())
		{
			int i = 0;
			for(Block block : b.blockchain)
			{
				if(!block.equal(blockchain.get(i)))
					return false;
			}
			return true;
		}
		return false;
	}
	
	public void update(Blockchain b)
	{
		if(!(b.equal(this)))
		{
			if(b.blockchain.size() < blockchain.size())
			{
				for(int i = b.blockchain.size(); (i < blockchain.size()) && (b.isValid() == true); i++)
				{
					b.addBlock(blockchain.get(i - 1));
				}
				b.write();
			}
			else
			{
				for(int i = blockchain.size(); (i < b.blockchain.size()) && (this.isValid() == true); i++)
				{
					addBlock(b.blockchain.get(i - 1));
				}	
				write();
			}
			
		}
	}
	public void read()
	{
		
	}
	
	public void write()
	{
		
	}
	
}
