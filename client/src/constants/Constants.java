package constants;

public class Constants {
	
	// 사용자 홈 디렉토리를 기반으로 경로 설정
    public static final String DIRECTORY_PATH;

    static {
        // 사용자 홈 디렉토리 경로 가져오기
        String userHome = System.getProperty("user.home");
        DIRECTORY_PATH = userHome + "/eclipse-workspace/Blms_server_final/data";
    }
	
	public enum EConfigurations {
		miridamgiFilePostfix("M"),
		singcheongFilePostfix("S");
		
		private String text;
		private EConfigurations(String text) {
			this.text = text;
		}
		public String getText() {
			return this.text;
		}
		public int getInt() {
			return Integer.parseInt(text);
		}
	}
	
	public enum EPResultPanel {
		gangjwaNo("강좌번호"),
		gangjwaName("강좌명");
		
		private String text;
		private EPResultPanel(String text) {
			this.text = text;
		}
		public String getText() {
			return this.text;
		}
		public int getInt() {
			return Integer.parseInt(text);
		}
	}
	
	public enum EPHeaderPanel {
		greetings(" 님 안녕하세요");
		
		private String text;
		private EPHeaderPanel(String text) {
			this.text = text;
		}
		public String getText() {
			return this.text;
		}
	}

	public enum EPGangjwaSelectionPanel {
		gangjwaNo("강좌번호"),
		gangjwaName("강좌명"),
		damdangGyosu("담당교수"),
		hakjeom("학점"),
		time("시간");
		
		private String text;
		private EPGangjwaSelectionPanel(String text) {
			this.text = text;
		}
		public String getText() {
			return this.text;
		}
		public int getInt() {
			return Integer.parseInt(text);
		}		
	}
	
	public enum EPHakgwaSelectionPanel {
		rootFileName("root"),
		campus("캠퍼스"),
		college("대학"),
		hakgwa("학과");
		
		private String text;
		private EPHakgwaSelectionPanel(String text) {
			this.text = text;
		}
		public String getText() {
			return this.text;
		}
		public int getInt() {
			return Integer.parseInt(text);
		}		
	}
	
	public enum ELoginDialog {
		width("300"),
		height("200"),
		nameLabel(" 사용자ID   "),
		sizeNameText("10"),
		passwordLabel("비밀번호"),
		sizePasswordText("10"),
		okButtonLabel("ok"),
		cancelButtonLabel("cancel"),
		loginFailed("잘못 입력하였습니다.");
		
		private String text;
		private ELoginDialog(String text) {
			this.text = text;
		}
		public String getText() {
			return this.text;
		}
		public int getInt() {
			return Integer.parseInt(text);
		}
	}

	public enum EMainFrame {
		width("1000"),
		height("600");
		
		private String text;
		private EMainFrame(String text) {
			this.text = text;
		}
		public String getText() {
			return this.text;
		}
		public int getInt() {
			return Integer.parseInt(text);
		}
	}
	
	public enum EMenuBar {
		eFile("메뉴");
	
		String text;
		EMenuBar(String text) {
			this.text = text;
		}
		public String getText() {
			return this.text;
		}
	}
	
	public enum EFileMenu {
	    ePrint("화면 캡쳐"),
	    eExit("종료");
	    
	    private final String text;

	    EFileMenu(String text) {
	        this.text = text;
	    }

	    public String getText() {
	        return this.text;
	    }
	}

}
