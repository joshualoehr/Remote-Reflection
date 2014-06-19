package transfer.client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import transfer.serial.Utility;

public class TransferClient {
	
	Socket clientSocket;
	DataOutputStream out;
	DataInputStream in;
	
	public TransferClient(String hostAddress, int hostPort) {
		try {
			clientSocket = new Socket(hostAddress, hostPort);
	        out = new DataOutputStream(clientSocket.getOutputStream());
	        in = new DataInputStream(clientSocket.getInputStream());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public Object invoke(String methodName, Object... params) {
		String request = "request;" + methodName;
		
		try {
			out.writeUTF(request);
			Utility.sendObject(out, params);
			Object response = Utility.receiveObject(in);
			if (response instanceof Exception) {
				throw (Exception) response;
			}
			return response; 
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
	}
	
	public void breakConnection() {
		try {
			out.writeUTF("break");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				out.close();
				in.close();
				clientSocket.close();
			} catch (IOException e) {
				// Ignore IOException
			}
		}
	}
}
