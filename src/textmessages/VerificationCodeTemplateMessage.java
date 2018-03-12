package textmessages;

import utils.SignInUtil;

public class VerificationCodeTemplateMessage {
	private String interMethod;
	private String entNo;
	private String account;
	private String pwd;
	private String templateType;
	private String templateNo;
	private DestCntList[] destCntList;
	private String sign;
	
	public VerificationCodeTemplateMessage(String interMethod, String entNo, String account, String pwd, String templateType, String templateNo, DestCntList[] destCntLists){
		this.interMethod = interMethod;
		this.entNo = entNo;
		this.account = account;
		this.pwd = pwd;
		this.templateType = templateType;
		this.templateNo = templateNo;
		this.destCntList = destCntLists;
		String unencipher = account + destCntList[0].getMsg() + destCntList[0].getPhone() + entNo + interMethod + pwd + templateNo + templateType;
		this.sign = SignInUtil.md5Encipher(unencipher);
	}
}
