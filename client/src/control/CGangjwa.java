package control;

import java.rmi.RemoteException;
import java.util.Vector;

import remoteInterface.IGangjwa;
import valueObject.VGangjwa;

public class CGangjwa extends CControl implements IGangjwa{
	
	private IGangjwa iGangjwa;
	
	public CGangjwa() throws Exception {
		super();
		this.iGangjwa = (IGangjwa) this.registry.lookup(IGangjwa.OBJECT_NAME);
	}
	
	public Vector<VGangjwa> getData(String fileName) throws RemoteException {
		System.out.println("*Client: "+ this.getClass().getSimpleName() +"getData started");
		Vector<VGangjwa> vGangjwas =this.iGangjwa.getData(fileName);
		return vGangjwas;
	}

}
