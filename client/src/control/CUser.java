package control;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import remoteInterface.IUser;
import valueObject.VUser;

public class CUser extends CControl implements IUser {

    private IUser iUser;

    // 생성자
    public CUser() throws RemoteException, NotBoundException {
        super();
        this.iUser = (IUser) this.registry.lookup(IUser.OBJECT_NAME);
    }

    public void initialie() {
    }

    // 사용자 조회 메서드
    @Override
    public VUser getUser(String userId) throws Exception {
        System.out.println("*Client: " + this.getClass().getSimpleName() + " getUser started");
        VUser response = this.iUser.getUser(userId);
        return response;
    }

    // 회원가입 메서드
    public boolean registerUser(VUser newUser) throws Exception {
        System.out.println("*Client: " + this.getClass().getSimpleName() + " registerUser started");

        // 서버의 registerUser 메서드 호출
        boolean success = this.iUser.registerUser(newUser);
        return success;
    }

    public void finish() {
        // TODO Auto-generated method stub
    }
}
