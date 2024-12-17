package model;

import java.util.Vector;

public interface Dao {

	public MModel getARow(String name, String key, Class<?> clazz);
	public Vector<MModel> getRows(String name, Class<?> clazz);
	
	public void setARow(String name, String key, Vector<MModel> mModels);
	public void setRows(String name, Vector<MModel> mModels);
}
