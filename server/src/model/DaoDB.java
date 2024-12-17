package model;

import java.util.Vector;

public class DaoDB implements Dao{
	
	public DaoDB() {
	}

	public MModel getARow(String fileName, String key, Class<?> clazz) {
		return null;
	}

	public Vector<MModel> getRows(String fileName, Class<?> clazz) {
		Vector<MModel> mModels = new Vector<MModel>();
		return mModels;
	}
	
	@Override
	public void setARow(String name, String key, Vector<MModel> mModels) {
	}
	
	public void setRows(String fileName, Vector<MModel> mModels) {
	}

}