package mainFrame;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.io.FileWriter;
import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import constants.Constants;
import control.CUser;
import valueObject.VUser;

public class PRegisterDialog extends JDialog {
    private static final long serialVersionUID = 1L;

    // Components
    private JLabel idLabel;
    private JTextField idText;
    private JLabel nameLabel;
    private JTextField nameText;
    private JLabel addressLabel;
    private JTextField addressText;
    private JLabel passwordLabel;
    private JPasswordField passwordField; // 비밀번호 필드를 JPasswordField로 변경
    private JButton registerButton;
    private JButton cancelButton;

    // Control
    private CUser cUser;

    public PRegisterDialog() throws RemoteException, NotBoundException {
        this.setSize(400, 300);  // 창 크기 설정
        this.setLocationRelativeTo(null);  // 화면 중앙에 표시
        this.setResizable(false);  // 창 크기 변경 불가

        this.setLayout(new FlowLayout());

        // 아이디 입력 필드
        JPanel line1 = new JPanel();
        this.idLabel = new JLabel("사용자ID");
        line1.add(this.idLabel);
        this.idText = new JTextField();
        this.idText.setColumns(15);
        line1.add(this.idText);
        this.add(line1, BorderLayout.NORTH);

        // 이름 입력 필드
        JPanel line2 = new JPanel();
        this.nameLabel = new JLabel("이름");
        line2.add(this.nameLabel);
        this.nameText = new JTextField();
        this.nameText.setColumns(15);
        line2.add(this.nameText);
        this.add(line2, BorderLayout.CENTER);

        // 주소 입력 필드
        JPanel line3 = new JPanel();
        this.addressLabel = new JLabel("주소");
        line3.add(this.addressLabel);
        this.addressText = new JTextField();
        this.addressText.setColumns(15);
        line3.add(this.addressText);
        this.add(line3, BorderLayout.CENTER);

        // 비밀번호 입력 필드
        JPanel line4 = new JPanel();
        this.passwordLabel = new JLabel("비밀번호");
        line4.add(this.passwordLabel);
        this.passwordField = new JPasswordField(); // JPasswordField 생성
        this.passwordField.setColumns(15);
        line4.add(this.passwordField);
        this.add(line4, BorderLayout.CENTER);

        // 버튼 추가
        JPanel buttonPanel = new JPanel();
        this.registerButton = new JButton("회원가입");
        this.registerButton.addActionListener(e -> handleRegister());
        buttonPanel.add(this.registerButton);

        this.cancelButton = new JButton("취소");
        this.cancelButton.addActionListener(e -> this.dispose());
        buttonPanel.add(this.cancelButton);

        this.add(buttonPanel, BorderLayout.SOUTH);

        // 컨트롤러 생성
        this.cUser = new CUser();
    }

    private void handleRegister() {
        String userId = this.idText.getText().trim();
        String userName = this.nameText.getText().trim();
        String address = this.addressText.getText().trim();
        String password = new String(this.passwordField.getPassword()).trim(); // 비밀번호 가져오기

        // 모든 필드가 입력되었는지 확인
        if (userId.isEmpty() || userName.isEmpty() || address.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "모든 필드를 입력하세요.");
            return;
        }

        // 비밀번호 길이 확인
        if (password.length() < 8) {
            JOptionPane.showMessageDialog(this, "비밀번호는 8자리 이상으로 입력해주세요.", "비밀번호 오류", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // 비밀번호 암호화
        String encryptedPassword = encryptPassword(password);
        if (encryptedPassword == null) {
            JOptionPane.showMessageDialog(this, "비밀번호 암호화 중 오류가 발생했습니다.", "오류", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            // 회원가입 처리 로직
            VUser newUser = new VUser();
            newUser.setUserId(userId);
            newUser.setName(userName);  // 이름 설정
            newUser.setAddress(address);  // 주소 설정
            newUser.setPassword(encryptedPassword); // 암호화된 비밀번호 설정

            // 서버의 data 파일에 저장
            saveUserDataToFile(newUser);

            boolean success = this.cUser.registerUser(newUser);
            if (success) {
                JOptionPane.showMessageDialog(this, "회원가입이 완료되었습니다.");
                this.dispose();  // 다이얼로그 닫기
            } else {
                JOptionPane.showMessageDialog(this, "회원가입에 실패했습니다.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "회원가입 처리 중 오류가 발생했습니다.");
        }
    }

    private void saveUserDataToFile(VUser user) throws IOException {
        // 절대 경로 설정
    	String directoryPath = Constants.DIRECTORY_PATH;

        // 기본 파일 경로
        String mainFilePath = directoryPath + "/" + user.getUserId() + ".txt";

        // 추가 파일 경로
        String maleFilePath = directoryPath + "/" + user.getUserId() + "M.txt";
        String femaleFilePath = directoryPath + "/" + user.getUserId() + "S.txt";

        // 디렉토리 확인 및 생성
        java.io.File directory = new java.io.File(directoryPath);
        if (!directory.exists()) {
            directory.mkdirs(); // 디렉토리 생성
        }

        // 요청한 형식으로 기본 파일 저장: 아이디 이름 주소
        try (FileWriter writer = new FileWriter(mainFilePath)) {
            writer.write(user.getUserId() + " " + user.getName() + " " + user.getAddress() + " " + user.getPassword() + "\n");
        }

        // 아이디M.txt 파일 생성 (내용 없음)
        try (FileWriter writer = new FileWriter(maleFilePath)) {
            // 아무 내용도 쓰지 않음
        }

        // 아이디S.txt 파일 생성 (내용 없음)
        try (FileWriter writer = new FileWriter(femaleFilePath)) {
            // 아무 내용도 쓰지 않음
        }

        // 디버깅 로그
        System.out.println("파일 저장 완료: " + mainFilePath);
        System.out.println("빈 파일 저장 완료: " + maleFilePath + ", " + femaleFilePath);
    }

    private String encryptPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hash = md.digest(password.getBytes());
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }
}