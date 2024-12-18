package mainFrame;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.*;

import chatBot.GPTService;

public class PChatBotDialog extends JDialog {
    private static final long serialVersionUID = 1L;
    private JTextArea chatArea;  // 대화 내용 표시
    private JTextArea inputArea; // 사용자 입력 필드
    private JButton sendButton;  // 전송 버튼

    public PChatBotDialog() {
        super((java.awt.Frame) null, true); // 모달 설정
        this.setSize(400, 500);
        this.setTitle("수강신청 도우미 챗봇");

        this.setLayout(new BorderLayout());

        // 대화 내용을 표시할 JTextArea
        chatArea = new JTextArea();
        chatArea.setEditable(false);
        chatArea.setLineWrap(true);
        chatArea.setWrapStyleWord(true);
        JScrollPane scrollPane = new JScrollPane(chatArea);
        this.add(scrollPane, BorderLayout.CENTER);

        // 초기 메시지 설정
        chatArea.append("수강신청 도우미 챗봇: 안녕하세요, 수강신청을 도와주는 챗봇입니다. 수강신청 관련하여 궁금한 점이 있다면 물어보세요.\n");

        // 입력 필드와 버튼을 포함하는 패널
        JPanel inputPanel = new JPanel(new BorderLayout());
        inputArea = new JTextArea(3, 30);  // 3줄로 높이 지정
        inputArea.setLineWrap(true);
        inputArea.setWrapStyleWord(true);
        inputArea.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER && !e.isShiftDown()) { // Shift + Enter 제외
                    sendMessage();
                    e.consume(); // Enter 키 이벤트 소비
                }
            }
        });
        JScrollPane inputScrollPane = new JScrollPane(inputArea);
        inputPanel.add(inputScrollPane, BorderLayout.CENTER);

        // 전송 버튼
        sendButton = new JButton("전송");
        sendButton.setPreferredSize(new Dimension(60, 30)); // 버튼 크기 설정
        sendButton.addActionListener((ActionEvent e) -> sendMessage());
        inputPanel.add(sendButton, BorderLayout.EAST);

        this.add(inputPanel, BorderLayout.SOUTH);

        inputArea.requestFocusInWindow(); // 포커스 설정
    }

    // GPT에 메시지 보내기
    private void sendMessage() {
        String userMessage = inputArea.getText().trim();
        if (!userMessage.isEmpty()) {
            chatArea.append("사용자: " + userMessage + "\n");
            inputArea.setText("");  // 입력 필드 초기화

            // GPT API 호출 (비동기 실행)
            new Thread(() -> {
                GPTService gptService = new GPTService();
                String gptResponse = gptService.callGPT(userMessage);
                SwingUtilities.invokeLater(() -> chatArea.append("수강신청 도우미 챗봇: " + gptResponse + "\n"));
            }).start();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            PChatBotDialog dialog = new PChatBotDialog();
            dialog.setVisible(true);
        });
    }
}