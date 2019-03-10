package blockchain;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientWorker implements Runnable {
	private Socket client;
	
	public ClientWorker(Socket c) {
		this.client = c;
	}
	
	@Override
	public void run() {
		PrintWriter out = null;
		BufferedReader in = null;
		try {
			out = new PrintWriter(client.getOutputStream(), true);
			in = new BufferedReader(new InputStreamReader(client.getInputStream()));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
        String line;
        try {
			while ((line = in.readLine()) != null) {
			  if(line.equals("publickey")) {
				  Wallet w = new Wallet(0);
				  w.readwallet();
				  out.println(w.publickey);
			  }
			}
			out.close(); // Flush and close the output stream
	        in.close(); // Close the input stream
	        client.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
