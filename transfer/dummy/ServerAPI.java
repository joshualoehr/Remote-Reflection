package transfer.dummy;

import transfer.server.TransferServer;

public class ServerAPI {
	
	public static void main(String args[]) {
		new ServerAPI(8080);
	}
	
	public ServerAPI(int port) {
		new TransferServer(port, this);
	}
	
	private String name = "Josh";
	private int id = 0;

	public String getName() {
		return name;
	}
	
	public void setName(String str) {
		name = str;
	}
	
	public int getID() {
		return id;
	}
	
	public void setID(int id) {
		this.id = id;
	}
	
	public void setStuff(String str, int id) {
		setName(str);
		setID(id);
	}
}
