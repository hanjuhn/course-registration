package mainFrame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import aspect.ExceptionManager;
import valueObject.VUser;

public class Main {
    private PLoginDialog pLoginDialog;
    private PMainFrame pMainFrame;
    private ExceptionManager exceptionManager;

    public Main() {
        this.exceptionManager = new ExceptionManager();
        try {
            this.pLoginDialog = new PLoginDialog(new ActionHandler());
        } catch (RemoteException | NotBoundException e) {
            exceptionManager.process(e);
        }
    }

    private void intialize() {
        this.pLoginDialog.initialize();
    }

    private void validateUser(Object source) throws Exception {
        try {
            // validateUser에서 vUser가 null인지 확인
            VUser vUser = this.pLoginDialog.validateUser(source);
            if (vUser != null) {
                System.out.println("로그인 성공, 사용자 정보: " + vUser.getUserId());
                // PMainFrame을 생성하고 초기화
                this.pMainFrame = new PMainFrame();
                this.pMainFrame.initialize(vUser);
                this.pLoginDialog.dispose();
            } else {
                System.out.println("로그인 실패, vUser가 null입니다.");
            }
        } catch (RemoteException e) {
            exceptionManager.process(e);
        }
    }

    // LoginDialog "OK" and "Cancel" Button Event Handler
    public class ActionHandler implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent event) {
            try {
                validateUser(event.getSource());
            } catch (Exception e) {
                exceptionManager.process(e); // 예외 처리
            }
        }
    }

    public static void main(String[] args) {
        Main main = new Main();
        main.intialize();
    }
}