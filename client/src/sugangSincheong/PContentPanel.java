package sugangSincheong;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Vector;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import constants.Constants.EConfigurations;
import valueObject.VGangjwa;
import valueObject.VUser;

public class PContentPanel extends JPanel {
	private static final long serialVersionUID = 1L;

	private VUser vUser;

	private ListSelectionListener listSelectionHandler;
	private PSelectionPanel pSelectionPanel;

	private PResultPanel pMiridamgiPanel;
	private PResultPanel pSincheongPanel;

	private ActionHandler actionHandler;
	private PControlPanel pControlPanel1;
	private PControlPanel pControlPanel2;

	public PContentPanel() {
		this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));

		// "과목" 레이블과 PSelectionPanel 추가
		JPanel selectionContainer = new JPanel();
		selectionContainer.setLayout(new BorderLayout());
		JLabel subjectLabel = new JLabel("과목", JLabel.CENTER);
		selectionContainer.add(subjectLabel, BorderLayout.NORTH);
		this.listSelectionHandler = new ListSelectionHandler();
		this.pSelectionPanel = new PSelectionPanel(this.listSelectionHandler);
		selectionContainer.add(this.pSelectionPanel, BorderLayout.CENTER);
		this.add(selectionContainer);

		// 첫 번째 컨트롤 패널
		this.actionHandler = new ActionHandler();
		this.pControlPanel1 = new PControlPanel(this.actionHandler);
		this.add(this.pControlPanel1);

		// "미리담기" 레이블과 패널 추가
		JPanel miridamgiContainer = new JPanel();
		miridamgiContainer.setLayout(new BorderLayout());
		JLabel miridamgiLabel = new JLabel("미리담기", JLabel.CENTER);
		miridamgiContainer.add(miridamgiLabel, BorderLayout.NORTH);
		this.pMiridamgiPanel = new PResultPanel();
		miridamgiContainer.add(this.pMiridamgiPanel, BorderLayout.CENTER);
		this.add(miridamgiContainer);

		// 두 번째 컨트롤 패널
		this.pControlPanel2 = new PControlPanel(this.actionHandler);
		this.add(this.pControlPanel2);

		// "수강신청" 레이블과 패널 추가
		JPanel sincheongContainer = new JPanel();
		sincheongContainer.setLayout(new BorderLayout());
		JLabel sincheongLabel = new JLabel("수강신청", JLabel.CENTER);
		sincheongContainer.add(sincheongLabel, BorderLayout.NORTH);
		this.pSincheongPanel = new PResultPanel();
		sincheongContainer.add(this.pSincheongPanel, BorderLayout.CENTER);
		this.add(sincheongContainer);
	}

	public void intialize(VUser vUser) throws Exception {
		this.vUser = vUser;

		this.pMiridamgiPanel.initialize(this.vUser.getUserId() + EConfigurations.miridamgiFilePostfix.getText());
		this.pSincheongPanel.initialize(this.vUser.getUserId() + EConfigurations.singcheongFilePostfix.getText());

		this.pSelectionPanel.initialize(this.pMiridamgiPanel, this.pSincheongPanel);

		this.pControlPanel1.initialize();
		this.pControlPanel2.initialize();
	}

	public void finish() throws RemoteException, NotBoundException {
		this.pMiridamgiPanel.finish(this.vUser.getUserId() + EConfigurations.miridamgiFilePostfix.getText());
		this.pSincheongPanel.finish(this.vUser.getUserId() + EConfigurations.singcheongFilePostfix.getText());
	}

	/////////////////////////////////////////////////////////////
	// Selection Listener: Gangjwa Table
	////////////////////////////////////////////////////////////
	public void update(Object source) throws Exception {
		this.pSelectionPanel.update(source);
	}

	public class ListSelectionHandler implements ListSelectionListener {
		@Override
		public void valueChanged(ListSelectionEvent event) {
			try {
				update(event.getSource());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/////////////////////////////////////////////////////
	// Action Listener: Move Buttons
	/////////////////////////////////////////////////////
	private void moveGangJwas(PGangjwaContainer source, PGangjwaContainer target) throws Exception {
		Vector<VGangjwa> vSelectedGangjwas = source.removeSelectedGangjwas();
		target.addGangjwas(vSelectedGangjwas);
	}

	public class ActionHandler implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent event) {
			try {
				Object source = event.getSource();
				if (source.equals(pControlPanel1.getMoveRightButton())) {
					moveGangJwas(pSelectionPanel, pMiridamgiPanel);
				} else if (source.equals(pControlPanel1.getMoveLeftButton())) {
					moveGangJwas(pMiridamgiPanel, pSelectionPanel);
				} else if (source.equals(pControlPanel2.getMoveRightButton())) {
					moveGangJwas(pMiridamgiPanel, pSincheongPanel);
				} else if (source.equals(pControlPanel2.getMoveLeftButton())) {
					moveGangJwas(pSincheongPanel, pMiridamgiPanel);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}