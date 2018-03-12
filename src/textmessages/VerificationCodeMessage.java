package textmessages;

import java.io.UnsupportedEncodingException;

import utils.SignInUtil;

public class VerificationCodeMessage {
	private String interMethod;
	private String entNo;
	private String account;
	private String pwd;
	private String[] destphone;
	private String msg;
	private String sign;
	
	public VerificationCodeMessage(String interMethod, String entNo, String account, String pwd, String[] destphone, String msg) throws UnsupportedEncodingException{
		this.interMethod = interMethod;
		this.entNo = entNo;
		this.account = account;
		this.pwd = pwd;
		this.destphone = destphone;
		this.msg = msg;
		String phonenumbers = "";
		int i = 0;
		for (i = 0; i < this.destphone.length - 1; i++){
			phonenumbers += this.destphone[i] + ",";
		}
		phonenumbers += this.destphone[i];
		String unencipher = account + phonenumbers + entNo + interMethod + this.msg + pwd;
		this.sign = SignInUtil.md5Encipher(unencipher);
	}
	
	public String getInterMethod() {
		return interMethod;
	}
	public void setInterMethod(String interMethod) {
		this.interMethod = interMethod;
	}
	public String getEntNo() {
		return entNo;
	}
	public void setEntNo(String entNo) {
		this.entNo = entNo;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getPwd() {
		return pwd;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	public String[] getDestphone() {
		return destphone;
	}
	public void setDestphone(String[] destphone) {
		this.destphone = destphone;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public String getSign() {
		return sign;
	}
	public void setSign(String sign) {
		this.sign = sign;
	}

}
