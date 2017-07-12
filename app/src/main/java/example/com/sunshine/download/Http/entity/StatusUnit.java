package example.com.sunshine.download.Http.entity;

import java.io.Serializable;

@SuppressWarnings("serial")
public class StatusUnit implements Serializable {
	private int code;
	private String msgToShow;
	private String msgForDebug;

	public String getMsgToShow() {
		return msgToShow;
	}

	public void setMsgToShow(String msgToShow) {
		this.msgToShow = msgToShow;
	}

	public String getMsgForDebug() {
		return msgForDebug;
	}

	public void setMsgForDebug(String msgForDebug) {
		this.msgForDebug = msgForDebug;
	}

	public int getCode() {
		return code;
	}
	
	public void setCode(int code) {
		this.code = code;
	}

}
