package remoteInterface;

import java.rmi.Remote;
import valueObject.VUser;

public interface IUser extends Remote {
	public final static String OBJECT_NAME = "CUSER";

	// 사용자 조회 메서드
	public VUser getUser(String userId) throws Exception; // 서비스 시그니처

	// 사용자 등록 메서드
	public boolean registerUser(VUser newUser) throws Exception; // 사용자 등록
}