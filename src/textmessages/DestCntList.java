package textmessages;

public class DestCntList {
	private String phone;
	private String msg;
	
	public DestCntList(String p, String m){
		this.phone = p;
		this.msg = m;
	}
	
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	

}
