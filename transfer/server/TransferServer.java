package transfer.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class TransferServer {
	
	public static Object serverAPI;
	
	public TransferServer(final int port, Object serverAPI) {
		TransferServer.serverAPI = serverAPI;
		
		ServerSocket serverSocket = null;
		try {
			serverSocket = new ServerSocket(port);
			while (true) {
				Socket clientSocket = serverSocket.accept();
				TransferThread clientThread = new TransferThread(clientSocket);
				clientThread.start();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				serverSocket.close();
			} catch (IOException e) {
				// Ignore IOException
			}
		}
	}
}
