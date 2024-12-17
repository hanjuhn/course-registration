package control;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import model.Dao;
import model.MUser;
import remoteInterface.IUser;
import valueObject.VUser;

public class CUser extends CControl implements IUser {

    public CUser(Dao dao) {
        super(dao);
    }

    public VUser getUser(String userId) {
		MUser mUser = (MUser) dao.getARow(userId, userId, MUser.class);
		if (mUser != null) {
			VUser vUser = new VUser(mUser.getUserId(), mUser.getName(), mUser.getAddress());
			return vUser;
		}
		return null;
	}
    

    @Override
    public boolean registerUser(VUser newUser) {
        try {
            // 사용자 정보를 파일에 저장
            boolean result = saveUserToFile(newUser);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // 사용자 정보를 파일에 저장
    private boolean saveUserToFile(VUser newUser) {
        BufferedWriter writer = null;
        try {
            // UserId.txt 파일에 사용자 정보 추가
            writer = new BufferedWriter(new FileWriter("data/UserId.txt", true));  // 'true'로 열어서 기존 내용에 추가
            writer.write(newUser.getUserId() + " " + newUser.getPassword());  // userId와 password만 저장
            writer.newLine();  // 한 줄을 추가
            System.out.println("새로운 사용자 저장됨: " + newUser.getUserId());
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (writer != null) {
                    writer.close();  // 파일 닫기
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}