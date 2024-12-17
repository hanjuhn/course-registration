package remoteInterface;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Vector;
import valueObject.VDirectory;

public interface IDirectory extends Remote {
	public final static String OBJECT_NAME = "CDIRECTORY";

	public Vector<VDirectory> getData(String fileName) throws RemoteException;
}
