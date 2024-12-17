package valueObject;

public class VResult extends VValueObject {

	private static final long serialVersionUID = 1L;

	private boolean success; // 로그인 성공 여부
	private String message; // 로그인 실패/성공 메시지
	private VUser vUser; // 로그인된 사용자 정보 (VUser 객체)

	// 기본 생성자
	public VResult() {
		// 초기화 로직을 넣을 수 있습니다
	}

	// 로그인 성공 여부를 반환
	public boolean isSuccess() {
		return success;
	}

	// 로그인 성공 여부 설정
	public void setSuccess(boolean success) {
		this.success = success;
	}

	// 로그인 메시지 반환
	public String getMessage() {
		return message;
	}

	// 로그인 메시지 설정
	public void setMessage(String message) {
		this.message = message;
	}

	// VUser 객체 반환
	public VUser getVUser() {
		return vUser;
	}

	// VUser 객체 설정
	public void setVUser(VUser vUser) {
		this.vUser = vUser;
	}
}