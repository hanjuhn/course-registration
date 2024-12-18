package mainFrame;

import javax.swing.JButton;
import javax.swing.JToolBar;
import javax.swing.JOptionPane;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class PToolBar extends JToolBar {
    private static final long serialVersionUID = 1L;

    public PToolBar() {
        // "수강신청 내역 확인" 버튼 생성
        JButton checkButton = new JButton("수강신청 내역 확인");
        this.add(checkButton);

        // 버튼 클릭 이벤트 처리
        checkButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleCheckButtonClick();
            }
        });
    }

    private void handleCheckButtonClick() {
        // 사용자 ID 입력받기
        String userId = JOptionPane.showInputDialog(this, "사용자 ID를 입력하세요:", "수강신청 내역 확인", JOptionPane.QUESTION_MESSAGE);

        if (userId == null || userId.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "사용자 ID를 입력하세요.", "오류", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // 파일 경로 설정
        String directoryPath = "/Users/baehanjun/eclipse-workspace/Blms_server_final/data";
        String filePath = directoryPath + "/" + userId + "S.txt";

        // 파일 읽기 및 내용 출력
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

            // 파일 내용 출력
            if (fileContent.length() > 0) {
                JOptionPane.showMessageDialog(this, "파일 내용:\n" + fileContent, "수강신청 내역", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "파일 내용이 비어 있습니다.", "수강신청 내역", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (FileNotFoundException ex) {
            JOptionPane.showMessageDialog(this, "파일을 읽는 중 오류가 발생했습니다: " + ex.getMessage(), "오류", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void initialize() {
        // 필요 시 초기화 작업 추가
    }
}