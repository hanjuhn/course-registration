package mainFrame;

import java.awt.AWTException;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import constants.Constants;
import constants.Constants.EFileMenu;

public class PFileMenu extends JMenu {
    private static final long serialVersionUID = 1L;

    public PFileMenu() {
        // attributes
        this.setText(Constants.EMenuBar.eFile.getText());

        // components
        for (EFileMenu eFileMenu : EFileMenu.values()) {
            JMenuItem menuItem = new JMenuItem(eFileMenu.getText());

            // Add ActionListener for each menu item
            menuItem.addActionListener(new FileMenuActionListener(eFileMenu));

            this.add(menuItem);
        }
    }

    // Inner class to handle menu item actions
    private class FileMenuActionListener implements java.awt.event.ActionListener {
        private EFileMenu menu;

        public FileMenuActionListener(EFileMenu menu) {
            this.menu = menu;
        }

        @Override
        public void actionPerformed(java.awt.event.ActionEvent e) {
            switch (menu) {
                case ePrint:
                    try {
                        handlePrintFile();
                    } catch (IOException | AWTException e1) {
                        e1.printStackTrace();
                    }
                    break;
                case eExit:
                    handleExit();
                    break;
                default:
                    throw new IllegalArgumentException("Unsupported menu: " + menu);
            }
        }

        private void handlePrintFile() throws IOException, AWTException {
            // 화면을 캡쳐하는 로직
            Robot robot = new Robot();
            Rectangle screenRect = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()); // 전체 화면 캡쳐
            BufferedImage screenFullImage = robot.createScreenCapture(screenRect);

            // 파일 저장 경로를 사용자에게 선택하게 함
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("화면을 저장할 위치를 선택하세요.");
            fileChooser.setSelectedFile(new File("captured_image_" + System.currentTimeMillis() + ".png"));

            int userSelection = fileChooser.showSaveDialog(null);
            if (userSelection == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();

                // 캡쳐한 이미지를 파일로 저장
                ImageIO.write(screenFullImage, "PNG", file);

                JOptionPane.showMessageDialog(null, "화면을 캡쳐하여 '" + file.getName() + "'로 저장했습니다.");
            }
        }

        private void handleExit() {
            System.out.println("종료 선택됨");
            int confirmed = JOptionPane.showConfirmDialog(null, 
                "프로그램을 종료하시겠습니까?", "종료 확인",
                JOptionPane.YES_NO_OPTION);

            if (confirmed == JOptionPane.YES_OPTION) {
                System.exit(0);
            }
        }
    }
}