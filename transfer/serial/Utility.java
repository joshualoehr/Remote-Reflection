package transfer.serial;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;

public class Utility {

	public static void sendObject(DataOutputStream out, Object obj) throws IOException {
		byte[] b = objectToBytes(obj);
		out.writeInt(b.length);
		out.write(b);
	}
	
	public static Object receiveObject(DataInputStream in) throws IOException {
		int len = in.readInt();
		byte[] b = new byte[len];
		in.read(b);
		return objectFromBytes(b);
	}
	
	public static Object receiveObject2(DataInputStream in) throws IOException, ClassNotFoundException {
		ObjectInputStream oin = new ObjectInputStream(in);
		return oin.readObject();
	}
	
	
	public static byte[] objectToBytes(Object obj) {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		ObjectOutput oout = null;
		try {
			oout = new ObjectOutputStream(bos);
			oout.writeObject(obj);
			byte[] b = bos.toByteArray();
			return b;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				bos.close();
				if (oout != null)
					oout.close();
			} catch (IOException e) {
				// ignore IOException
			}
		}
		return null;
	}
	
	public static Object objectFromBytes(byte[] b) {
		ByteArrayInputStream bis = new ByteArrayInputStream(b);
		ObjectInput oin = null;
		try {
			oin = new ObjectInputStream(bis);
			Object obj = oin.readObject();
			return obj;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				bis.close();
				if (oin != null)
					oin.close();
			} catch (IOException e) {
				// ignore IOException
			}
		}
		return null;
	}
}
