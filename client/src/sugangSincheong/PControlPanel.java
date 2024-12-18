package sugangSincheong;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class PControlPanel extends JPanel {
    private static final long serialVersionUID = 1L;

    private JButton moveRightButton;
    private JButton moveLeftButton;

    public PControlPanel(ActionListener actionHandler) {
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        // "저장" 버튼
        this.moveRightButton = new JButton("과목 저장");
        this.moveRightButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int result = JOptionPane.showConfirmDialog(
                        PControlPanel.this,
                        "저장하시겠습니까?",
                        "확인",
                        JOptionPane.YES_NO_OPTION
                );
                if (result == JOptionPane.YES_OPTION) {
                    // yes 버튼을 누른 경우, 기존 actionHandler 동작 수행
                    actionHandler.actionPerformed(e);
                }
            }
        });
        this.add(this.moveRightButton);

        // "삭제" 버튼
        this.moveLeftButton = new JButton("과목 삭제");
        this.moveLeftButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int result = JOptionPane.showConfirmDialog(
                        PControlPanel.this,
                        "삭제하시겠습니까?",
                        "확인",
                        JOptionPane.YES_NO_OPTION
                );
                if (result == JOptionPane.YES_OPTION) {
                    // yes 버튼을 누른 경우, 기존 actionHandler 동작 수행
                    actionHandler.actionPerformed(e);
                }
            }
        });
        this.add(this.moveLeftButton);
    }

    public void initialize() {
    }

    public Object getMoveRightButton() {
        return this.moveRightButton;
    }

    public Object getMoveLeftButton() {
        return this.moveLeftButton;
    }
}