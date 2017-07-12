package example.com.sunshine.download.Http.entity;


@SuppressWarnings("serial")
public class UserLoginResponse extends BaseResponse {
	private UserInfo userInfo;

	public UserInfo getUserInfo() {
		return userInfo;
	}

	public void setUserInfo(UserInfo userInfo) {
		this.userInfo = userInfo;
	}

}
