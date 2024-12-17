package control;

import java.util.Vector;

import model.Dao;
import model.MDirectory;
import model.MModel;
import remoteInterface.IDirectory;
import valueObject.VDirectory;

public class CDirectory extends CControl implements IDirectory{


	public CDirectory(Dao dao) {
		super(dao);
	}
	
	public Vector<VDirectory> getData(String fileName) {
		Vector<MModel> mModels = dao.getRows(fileName, MDirectory.class);
		
		Vector<VDirectory> vDrectories = new Vector<VDirectory>();
		for (MModel mModel: mModels) {
			MDirectory mDirectory = (MDirectory) mModel;
			
			VDirectory vDirectory = new VDirectory(
				mDirectory.getName(),
				mDirectory.getFileName()
			);
			vDrectories.add(vDirectory);
		}		
		return vDrectories;
	}

}
