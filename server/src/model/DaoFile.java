package model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Scanner;
import java.util.Vector;

public class DaoFile implements Dao{
	
	public DaoFile() {
		
	}
	private String read(MModel model, Scanner scanner) {
		String key = null;
		try {
			Field[] fields = model.getClass().getDeclaredFields();
			for (Field field: fields) {
				String fieldValue = scanner.next();
				field.setAccessible(true);
				field.set(model, fieldValue);
			}
			key = (String) fields[0].get(model);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return key;
	}
	
	private void save(MModel model, PrintWriter printWriter) {	
		try {
			Field[] fields = model.getClass().getDeclaredFields();
			for (Field field: fields) {
				field.setAccessible(true);
				printWriter.print(field.get(model)+" ");
			}
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
	}

	public MModel getARow(String fileName, String key, Class<?> clazz) {
		try {			
			Scanner scanner = new Scanner(new File("data/"+ fileName+".txt"));
			Constructor<?> constructor = clazz.getConstructor();
			MModel mModel = (MModel) constructor.newInstance();
			while (scanner.hasNext()) {
				String mModelKey = this.read(mModel, scanner);
				if (key.contentEquals(mModelKey)) {
					return mModel;
				} 
			}
			scanner.close();
		} catch (FileNotFoundException | NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
		}
		return null;
	}

	public Vector<MModel> getRows(String fileName, Class<?> clazz) {
		Vector<MModel> mModels = new Vector<MModel>();
		try {			
			Scanner scanner = new Scanner(new File("data/"+ fileName+".txt"));
			while (scanner.hasNext()) {
				Constructor<?> contstructor = clazz.getConstructor();
				MModel mModel = (MModel) contstructor.newInstance();
				this.read(mModel, scanner);
				mModels.add(mModel);
			}
			scanner.close();
		} catch (FileNotFoundException | NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
		}
		return mModels;
	}
	
	@Override
	public void setARow(String name, String key, Vector<MModel> mModels) {
	}

	public void setRows(String fileName, Vector<MModel> mModels) {
		try {
			PrintWriter printWriter = new PrintWriter(new File("data/"+fileName+".txt"));
			for (MModel mModel: mModels) {
				this.save(mModel, printWriter);
			}
			printWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}		
	}


}
