package example.com.sunshine.download.Http;

import android.content.Context;

public class UUID {
	private Context context;
	private String deviceid;     //imei
	private String simserialnum; //sim card number
	private String androidid;
	private String url;
	private String channel;
	private String netype;
	private String imsi;     //operator identify
	private String serialno; //phone s/n
	private String systemversion;
	private String productmodel;	  
	private String appversioncode;
	private String appname;
	private String guid;
	
	private String md5;
	
	private int type;   //0:md5 input num
	private int mode;	//register,download,play
	
	public UUID(){}
	
    static {
    	try {
    		System.loadLibrary("uuid");
    	}
    	catch (UnsatisfiedLinkError ule) {
			// TODO: handle exception
		}    	
    }
    
    public native String stringFromJNI(UUID uuid);
    public native String stringGuidFromJNI(UUID uuid);
    
    public String nativeCal(Context context,UUID uuid)
    {
    	this.context = context;
    	return stringFromJNI(uuid);
    	
    }
    public String nativeGuidCal(Context context,UUID uuid)
    {
    	this.context = context;
    	return stringGuidFromJNI(uuid);
    	
    }   
	private void uuidGenerate(String uuid)
	{
		this.md5 = uuid;
	}
	
	private void uuidType(int type)
	{
		this.type = type;
	}
	
	public String getDeviceid() {
		return deviceid;
	}

	public void setDeviceid(String id) {
		this.deviceid = id;
	}
	
	public String getSn() {
		return serialno;
	}

	public void setSn(String sn) {
		this.serialno = sn;
	}
	
	public String getChn() {
		return channel;
	}

	public void setChn(String chn) {
		this.channel = chn;
	}	
	public String getMD5() {
		return md5;
	}
	
	public int getType() {
		return type;
	}
	
	public void setGuid(String guid) {
		this.guid = guid;
	}
}
