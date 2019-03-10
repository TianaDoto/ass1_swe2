package blockchain;

import java.io.IOException;
import java.net.ServerSocket;

public class Server implements Runnable {

	@Override
	public void run() {
		int PORT = 4321;
		ServerSocket server = null;
		try {
			server = new ServerSocket(PORT); 
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//Listining to Client
		while(true) {
			ClientWorker w;
			
			try {
				w = new ClientWorker(server.accept());
				Thread t = new Thread(w);
				t.start();
			} catch(IOException e){
				e.printStackTrace();
			}
		}
		
	}

}