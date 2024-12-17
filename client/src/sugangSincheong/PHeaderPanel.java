package sugangSincheong;

import java.awt.Image;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import constants.Constants.EPHeaderPanel;
import valueObject.VUser;

public class PHeaderPanel extends JPanel {
    private static final long serialVersionUID = 1L;

    private JLabel logoLabel;    // 로고 표시용 JLabel
    private JLabel welcomeLabel; // 환영 메시지용 JLabel

    public PHeaderPanel() {
        // 수직 정렬을 위한 레이아웃 설정
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        // 로고 설정
        this.logoLabel = new JLabel();
        ImageIcon logoIcon = new ImageIcon("image/classlogo_big.png"); // 로고 파일 경로
        Image scaledLogo = logoIcon.getImage().getScaledInstance(200, 50, Image.SCALE_SMOOTH); // 로고 크기 조정
        this.logoLabel.setIcon(new ImageIcon(scaledLogo));
        this.logoLabel.setAlignmentX(CENTER_ALIGNMENT); // 로고 중앙 정렬

        // 환영 메시지 설정
        this.welcomeLabel = new JLabel();
        this.welcomeLabel.setAlignmentX(CENTER_ALIGNMENT); // 환영 메시지 중앙 정렬

        // 로고와 환영 메시지를 패널에 추가
        this.add(this.logoLabel);
        this.add(Box.createVerticalStrut(10)); // 로고와 환영 메시지 사이 간격
        this.add(this.welcomeLabel);
    }

    public void intialize(VUser vUser) {
        this.welcomeLabel.setText(vUser.getName() + EPHeaderPanel.greetings.getText());
    }
}