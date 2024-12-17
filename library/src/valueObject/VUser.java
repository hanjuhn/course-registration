package valueObject;

public class VUser extends VValueObject {
	private static final long serialVersionUID = 1L;

	private String userId;
	private String name;
	private String address;
	private String password; // 비밀번호 필드 추가

	// 기본 생성자 추가
	public VUser() {
		// 기본 생성자에서는 기본 값을 설정할 수 있습니다 (선택 사항)
	}

	// 매개변수가 있는 생성자 (userId, name, address, password)
	public VUser(String userId, String name, String address) {
		this.userId = userId;
		this.name = name;
		this.address = address;
	}

	// Getter와 Setter 추가
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}