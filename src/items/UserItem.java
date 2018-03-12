package items;

import HibernateCode.User;


public class UserItem {
    private int userid;//鐢ㄦ埛id
    private String phonenumber;//鎵嬫満鍙�
    private String token;//璇锋眰楠岃瘉token
    private String nickname;//鏄电О
    private String headportrait;//澶村儚
    private String sex;//鎬у埆
    private String signature;//涓�х鍚�
    private String place;//鍦板尯

    public UserItem(){
    	userid= -1;
    	phonenumber = "";
    	token = "";
    	nickname = "";
    	headportrait = "";
    	sex = "";
    	signature = "";
    	place = "";
    }
    
    public UserItem(int userid, String phonenumber,
                    String token, String nickname,
                    String headportrait, String sex,
                    String signature, String place) {
        this.userid = userid;
        this.phonenumber = phonenumber;
        this.token = token;
        this.nickname = nickname;
        this.headportrait = headportrait;
        this.sex = sex;
        this.signature = signature;
        this.place = place;
    }
    
    public UserItem(User user){
    	userid = user.getUserid();
    	phonenumber = user.getPhonenumber();
    	token = user.getToken();
    	nickname = user.getNickname();
    	headportrait = user.getHeadportrait();
    	sex = user.getSex();
    	signature = user.getSignature();
    	place = user.getPlace();
    }

    public int getUserid() {
        return userid;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public String getToken() {
        return token;
    }

    public String getNickname() {
        return nickname;
    }

    public String getHeadportrait() {
        return headportrait;
    }

    public String getSex() {
        return sex;
    }

    public String getSignature() {
        return signature;
    }

    public String getPlace() {
        return place;
    }
}
