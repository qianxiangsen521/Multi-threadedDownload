package example.com.sunshine.download.Http.entity;

public class UserInfo {
	private Long id;
	private int userId;
	private int userType;
	private String userName;
	private String userImage;
	private String userSignature;
	private String userToken;
	private String birth;
	private String area;
	private String profession;
	private String userFaour;
	private String phoneNumber;
	private String sex;
	public UserInfo(Long id, int userId, int userType, String userName,
									String userImage, String userSignature, String userToken, String birth,
									String area, String profession, String userFaour, String phoneNumber,
									String sex) {
					this.id = id;
					this.userId = userId;
					this.userType = userType;
					this.userName = userName;
					this.userImage = userImage;
					this.userSignature = userSignature;
					this.userToken = userToken;
					this.birth = birth;
					this.area = area;
					this.profession = profession;
					this.userFaour = userFaour;
					this.phoneNumber = phoneNumber;
					this.sex = sex;
	}
	public UserInfo() {
	}
	public Long getId() {
					return this.id;
	}
	public void setId(Long id) {
					this.id = id;
	}
	public int getUserId() {
					return this.userId;
	}
	public void setUserId(int userId) {
					this.userId = userId;
	}
	public int getUserType() {
					return this.userType;
	}
	public void setUserType(int userType) {
					this.userType = userType;
	}
	public String getUserName() {
					return this.userName;
	}
	public void setUserName(String userName) {
					this.userName = userName;
	}
	public String getUserImage() {
					return this.userImage;
	}
	public void setUserImage(String userImage) {
					this.userImage = userImage;
	}
	public String getUserSignature() {
					return this.userSignature;
	}
	public void setUserSignature(String userSignature) {
					this.userSignature = userSignature;
	}
	public String getUserToken() {
					return this.userToken;
	}
	public void setUserToken(String userToken) {
					this.userToken = userToken;
	}
	public String getBirth() {
					return this.birth;
	}
	public void setBirth(String birth) {
					this.birth = birth;
	}
	public String getArea() {
					return this.area;
	}
	public void setArea(String area) {
					this.area = area;
	}
	public String getProfession() {
					return this.profession;
	}
	public void setProfession(String profession) {
					this.profession = profession;
	}
	public String getUserFaour() {
					return this.userFaour;
	}
	public void setUserFaour(String userFaour) {
					this.userFaour = userFaour;
	}
	public String getPhoneNumber() {
					return this.phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
					this.phoneNumber = phoneNumber;
	}
	public String getSex() {
					return this.sex;
	}
	public void setSex(String sex) {
					this.sex = sex;
	}


}
