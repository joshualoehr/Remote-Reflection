package transfer.dummy;

import transfer.client.TransferClient;

public class ClientAPI {

	public static void main(String args[]) {
		ClientAPI dummyClient = new ClientAPI(new TransferClient("localhost", 8080));
		//ClientAPI dummyClient = new ClientAPI(new TransferClient());
		dummyClient.setStuff("Leroy", 34);
		System.out.println(dummyClient.getName() + " " + dummyClient.getID());
		dummyClient.breakConnection();
	}
	
	TransferClient tc;
	
	public ClientAPI(TransferClient tc) {
		this.tc = tc;
	}
	
	public String getName() {
		return (String) tc.invoke("getName");
	}
	
	public void setName(String str) {
		tc.invoke("setName", str, 45);
	}
	
	public void setStuff(String str, int id) {
		tc.invoke("setStuff", str, id);
	}
	
	public int getID() {
		return (Integer) tc.invoke("getID");
	}
	
	public void breakConnection() {
		tc.breakConnection();
	}
}
