package transfer.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.Socket;
import java.net.SocketException;

import transfer.serial.Utility;

public class TransferThread extends Thread {
	Socket clientSocket;
	DataInputStream in;
	DataOutputStream out;
	Object dummyServer = TransferServer.serverAPI;
	
	public TransferThread(Socket socket) {
		super();
		clientSocket = socket;
	}
	
	public void run() {
		try {
			in = new DataInputStream(clientSocket.getInputStream());
			out = new DataOutputStream(clientSocket.getOutputStream());
			
			String request = in.readUTF();
			while (request.startsWith("request")) {
				try {
					String methodName = request.substring(
							request.indexOf(";") + 1);
					Object[] params = (Object[]) Utility.receiveObject(in);
					
					Object result = null;
					boolean match = false;
					for (Method method : dummyServer.getClass().getMethods()) {
						if (methodNameMatches(method, methodName)) {
							try {
								result = method.invoke(dummyServer, params); 
							} catch (Exception e) {
								result = e;
							}
							match = true;
						}
					}
					if (!match) {
						result = new NoSuchMethodException(methodName);
					}
					
					Utility.sendObject(out, result);
					request = in.readUTF();
				} catch (SocketException e) {
					breakConnection();
					break;
				} catch (Exception e) {
					e.printStackTrace();
					request = in.readUTF();
				}
			}
			
			if (request.startsWith("break")) {
				breakConnection();
			}
		} catch (SocketException e) {
			breakConnection();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void breakConnection() {
		try {
			out.close();
			in.close();
			clientSocket.close();
		} catch (IOException e) {
			// Ignore IOException
		}
		
	}
	
	private boolean methodNameMatches(Method method, String str) {
		String name = method.getName();
		name = name.substring(name.lastIndexOf(".") + 1);
		return name.equals(str);
	}
}
