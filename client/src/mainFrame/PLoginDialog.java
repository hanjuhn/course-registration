package mainFrame;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import constants.Constants;
import constants.Constants.ELoginDialog;
import control.CLogin;
import control.CUser;
import mainFrame.Main.ActionHandler;
import valueObject.VLogin;
import valueObject.VResult;
import valueObject.VUser;

public class PLoginDialog extends JDialog {
    private static final long serialVersionUID = 1L;
    private JLabel currentTimeLabel; // 현재 시간 표시 라벨
    private JLabel nameLabel;
    private JTextField nameText;
    private JLabel passwordLabel;
    private JPasswordField passwordField; // 비밀번호 입력 필드
    private JButton enterButton; // 수강신청 진입 버튼
    private JButton exitButton; // 종료 버튼
    private JButton registerButton; // 회원가입 버튼
    private JButton chatButton; // 챗봇 버튼
    private JButton checkButton; // 수강신청 내역 확인 버튼
    private JButton preBasketButton; // 미리담기 내역 버튼

	private CLogin cLogin;
	private CUser cUser;

    public PLoginDialog(ActionHandler actionHandler) throws RemoteException, NotBoundException {
        this.setSize(450, 400); // 창 크기 설정
        this.setLocationRelativeTo(null); // 화면 중앙에 표시
        this.setResizable(false);
        this.setTitle("수강신청 로그인");

        this.setLayout(new BorderLayout(10, 10));

        // 상단에 현재 시간과 로고 표시
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS)); // 상단을 수직 정렬로 변경

        // 현재 시간 표시 라벨
        this.currentTimeLabel = new JLabel();
        updateCurrentTime(); // 현재 시간 업데이트
        this.currentTimeLabel.setAlignmentX(CENTER_ALIGNMENT); // 중앙 정렬
        topPanel.add(this.currentTimeLabel);

        // 로고 이미지 추가
        JLabel logoLabel = new JLabel();
        ImageIcon logoIcon = new ImageIcon("image/classlogo_big.png"); // 로고 경로 설정
        Image scaledImage = logoIcon.getImage().getScaledInstance(200, 50, Image.SCALE_SMOOTH); // 가로 크기 400으로 조정
        logoLabel.setIcon(new ImageIcon(scaledImage));
        logoLabel.setAlignmentX(CENTER_ALIGNMENT); // 중앙 정렬
        topPanel.add(Box.createVerticalStrut(10)); // 현재 시간과 로고 사이 간격
        topPanel.add(logoLabel);

        this.add(topPanel, BorderLayout.NORTH);

        // 사용자 입력 UI
        JPanel inputPanel = new JPanel(new GridBagLayout()); // GridBagLayout 사용
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5); // 컴포넌트 간의 간격 설정
        gbc.anchor = GridBagConstraints.CENTER; // 중앙 정렬

        // 사용자 아이디 패널
        JPanel idPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 5));
        this.nameLabel = new JLabel(ELoginDialog.nameLabel.getText());
        this.nameText = new JTextField();
        this.nameText.setPreferredSize(new Dimension(200, 25)); // 입력 필드 크기 조정
        idPanel.add(this.nameLabel);
        idPanel.add(this.nameText);

        // 비밀번호 패널
        JPanel passwordPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 5));
        this.passwordLabel = new JLabel(ELoginDialog.passwordLabel.getText());
        this.passwordField = new JPasswordField();
        this.passwordField.setPreferredSize(new Dimension(200, 25)); // 입력 필드 크기 조정
        passwordPanel.add(this.passwordLabel);
        passwordPanel.add(this.passwordField);

        // 아이디 패널 배치
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL; // 가로로 채우기
        inputPanel.add(idPanel, gbc);

        // 비밀번호 패널 배치
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL; // 가로로 채우기
        inputPanel.add(passwordPanel, gbc);

        // 중앙에 배치
        this.add(inputPanel, BorderLayout.CENTER);

        // 버튼 패널
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 50, 10, 50));

        // 첫 번째 버튼 그룹 (수강신청 진입, 종료, 회원가입)
        JPanel actionButtons = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        this.enterButton = new JButton("수강신청 진입");
        this.enterButton.addActionListener(actionHandler);
        this.getRootPane().setDefaultButton(this.enterButton);

        this.exitButton = new JButton("종료");
        this.exitButton.addActionListener(e -> this.dispose());

        this.registerButton = new JButton("회원가입");
        this.registerButton.addActionListener(actionHandler);

        actionButtons.add(this.enterButton);
        actionButtons.add(this.exitButton);
        actionButtons.add(this.registerButton);
        buttonPanel.add(actionButtons);

        // 두 번째 버튼 그룹
        JPanel additionalButtons = new JPanel(new GridLayout(3, 1, 10, 10)); // 3행 1열 배치

        this.preBasketButton = new JButton("미리담기 내역 확인");
        this.preBasketButton.setPreferredSize(new Dimension(0, 30)); // 버튼 크기 조정
        this.preBasketButton.addActionListener((ActionEvent e) -> handleFileDisplay("미리담기 내역 확인", "미리담기 내역", "M"));

        this.checkButton = new JButton("수강신청 내역 확인");
        this.checkButton.setPreferredSize(new Dimension(0, 30)); // 버튼 크기 조정
        this.checkButton.addActionListener((ActionEvent e) -> handleFileDisplay("수강신청 내역 확인", "수강신청 내역", "S"));

        this.chatButton = new JButton("수강신청 도우미 챗봇");
        this.chatButton.setPreferredSize(new Dimension(0, 0)); // 버튼 크기 조정
        this.chatButton.addActionListener((ActionEvent e) -> openChatBotDialog());

        additionalButtons.add(this.preBasketButton);
        additionalButtons.add(this.checkButton);
        additionalButtons.add(this.chatButton);

        buttonPanel.add(additionalButtons);

        this.add(buttonPanel, BorderLayout.SOUTH);

        // 현재 시간 자동 업데이트를 위한 스레드 실행
        startClockThread();

        // create control
        this.cLogin = new CLogin();
        this.cUser = new CUser();
    }
    
    public void initialize() {
        this.setVisible(true);
    }

//    public VUser validateUser(Object eventSource) throws Exception {
//        VUser vUser = null;
//        if (eventSource.equals(this.enterButton)) {
//            String userId = this.nameText.getText().trim();
//            String password = new String(this.passwordField.getPassword()).trim();
//
//            if (userId.isEmpty() || password.isEmpty()) {
//                JOptionPane.showMessageDialog(this, "사용자 ID와 비밀번호를 입력하세요.", "오류", JOptionPane.ERROR_MESSAGE);
//                return null;
//            }
//
//            // 입력된 비밀번호를 암호화
//            String encryptedPassword = encryptPassword(password);
//
//            if (encryptedPassword == null) {
//                JOptionPane.showMessageDialog(this, "비밀번호 암호화 중 오류가 발생했습니다.", "오류", JOptionPane.ERROR_MESSAGE);
//                return null;
//            }
//
//            // 사용자 정보 파일 경로
//            String directoryPath = Constants.DIRECTORY_PATH;
//            String userFilePath = directoryPath + "/" + userId + ".txt";
//
//            File userFile = new File(userFilePath);
//            if (!userFile.exists()) {
//                JOptionPane.showMessageDialog(this, "사용자 ID가 존재하지 않습니다.", "오류", JOptionPane.ERROR_MESSAGE);
//                return null;
//            }
//
//            try (Scanner scanner = new Scanner(userFile)) {
//                if (scanner.hasNextLine()) {
//                    String[] userInfo = scanner.nextLine().split(" ");
//
//                    // 배열 길이 검사
//                    if (userInfo.length < 4) {
//                        JOptionPane.showMessageDialog(this, "사용자 정보가 올바르지 않습니다.", "오류", JOptionPane.ERROR_MESSAGE);
//                        return null;
//                    }
//
//                    String storedUserId = userInfo[0];
//                    String storedPassword = userInfo[3]; // 암호화된 비밀번호
//
//                    // 사용자 ID와 비밀번호 확인
//                    if (storedUserId.equals(userId) && storedPassword.equals(encryptedPassword)) {
//                        vUser = new VUser();
//                        vUser.setUserId(storedUserId);
//                        vUser.setName(userInfo[1]);
//                        vUser.setAddress(userInfo[2]);
//
//                        JOptionPane.showMessageDialog(this, "로그인 성공", "성공", JOptionPane.INFORMATION_MESSAGE);
//                    } else {
//                        JOptionPane.showMessageDialog(this, "ID 또는 비밀번호가 일치하지 않습니다.", "오류", JOptionPane.ERROR_MESSAGE);
//                    }
//                } else {
//                    JOptionPane.showMessageDialog(this, "사용자 정보가 비어 있습니다.", "오류", JOptionPane.ERROR_MESSAGE);
//                }
//            } catch (FileNotFoundException ex) {
//                JOptionPane.showMessageDialog(this, "사용자 정보를 읽는 중 오류가 발생했습니다: " + ex.getMessage(), "오류", JOptionPane.ERROR_MESSAGE);
//            }
//        } else if (eventSource.equals(this.registerButton)) {
//            openRegisterDialog();
//        }
//        return vUser;
//    }
    
    
    public VUser validateUser(Object eventSource) throws Exception {
        VUser vUser = null;

        if (eventSource.equals(this.enterButton)) {
            String userId = this.nameText.getText();
            @SuppressWarnings("deprecation")
			String password = this.passwordField.getText(); // 비밀번호 필드에서 입력값 가져오기

            // 입력값을 VLogin 객체에 설정
            VLogin vLogin = new VLogin();

            // 비밀번호 암호화 처리
            String encryptedPassword = encryptPassword(password);
            if (encryptedPassword == null) {
                JOptionPane.showMessageDialog(this, "비밀번호 암호화 중 오류가 발생했습니다.", "오류", JOptionPane.ERROR_MESSAGE);
                return null;
            }

            vLogin.set(userId, encryptedPassword); // 암호화된 비밀번호를 VLogin에 설정

            // 로그인 검증
            VResult vResult = this.cLogin.login(vLogin);
            if (vResult != null) {
                // 로그인 성공 시 사용자 정보 가져오기
                vUser = this.cUser.getUser(userId);
            } else {
                // 로그인 실패 처리
                JOptionPane.showMessageDialog(this, ELoginDialog.loginFailed.getText());
            }
        } else if (eventSource.equals(this.exitButton)) {
            System.exit(0); // 애플리케이션 종료
        } else if (eventSource.equals(this.registerButton)) {
            // 회원가입 다이얼로그 열기
            openRegisterDialog();
        }
        return vUser;
    }

    // 비밀번호 암호화 메서드
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

    private void openRegisterDialog() throws RemoteException, NotBoundException {
        PRegisterDialog registerDialog = new PRegisterDialog();
        registerDialog.setLocationRelativeTo(this);
        registerDialog.setVisible(true);
    }

    private void openChatBotDialog() {
        PChatBotDialog chatBotDialog = new PChatBotDialog();
        chatBotDialog.setLocation(0, 0); // 창의 위치를 왼쪽 상단으로 설정
        chatBotDialog.setVisible(true);
    }

    private void handleFileDisplay(String title, String label, String fileType) {
        String userId = JOptionPane.showInputDialog(this, "사용자 ID를 입력하세요:", title, JOptionPane.QUESTION_MESSAGE);

        if (userId == null || userId.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "사용자 ID를 입력하세요.", "오류", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String directoryPath = Constants.DIRECTORY_PATH;
        String filePath = directoryPath + "/" + userId + fileType + ".txt";

        File file = new File(filePath);
        if (!file.exists()) {
            JOptionPane.showMessageDialog(this, "파일이 존재하지 않습니다: " + filePath, "오류", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try (Scanner scanner = new Scanner(file)) {
            StringBuilder fileContent = new StringBuilder();
            while (scanner.hasNextLine()) {
                fileContent.append(scanner.nextLine()).append("\n");
            }

            if (fileContent.length() > 0) {
                JOptionPane.showMessageDialog(this, label + ":\n" + fileContent, title, JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, label + " 내용이 비어 있습니다.", title, JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (FileNotFoundException ex) {
            JOptionPane.showMessageDialog(this, "파일을 읽는 중 오류가 발생했습니다: " + ex.getMessage(), "오류", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void updateCurrentTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        this.currentTimeLabel.setText("현재 시간: " + sdf.format(new Date()));
    }

    private void startClockThread() {
        Thread clockThread = new Thread(() -> {
            while (true) {
                updateCurrentTime();
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        clockThread.setDaemon(true);
        clockThread.start();
    }
}