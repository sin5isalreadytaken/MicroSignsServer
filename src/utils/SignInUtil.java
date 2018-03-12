package utils;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import com.ipi.cloud.interfaces.dto.ErrorInfo;
import com.ipi.cloud.sms.access.request.CloudSmsCore;
import com.ipi.cloud.sms.access.vo.SmsAccount;
import com.ipi.cloud.sms.access.vo.response.SmsBatchSumbitResponse;

import net.sf.json.JSONObject;
import textmessages.DestCntList;
import textmessages.VerificationCodeMessage;
import textmessages.VerificationCodeTemplateMessage;

public class SignInUtil {
	
	private static final char[] hexDigits={'0','1','2','3','4','5','6','7','8','9','a','b','c','d','e','f'}; 
	private static final String interUrl = "http://120.24.230.105:6666/interface/SendMsg";//TODO 短信通验证
	private static final String entNo = "21438243719019";
	private static final String account = "13312906128";
	private static final String password = md5Encipher("Sz123@_winnin");
	private static final String templateNo = "";
	private static final String templateType = "1";
	
	/**
	 * 
	 * @param String 待MD5加密String
	 * @return String 加密后String
	 */
	public static String md5Encipher(String s){
		try {
			byte[] strTemp = s.getBytes();
			MessageDigest mdTemp = MessageDigest.getInstance("MD5");
			mdTemp.update(strTemp);
			byte[] md = mdTemp.digest();
			int j = md.length;
			char str[] = new char[j * 2];
			int k = 0;
			for (int i = 0; i < j; i++) {
				byte byte0 = md[i];
				str[k++] = hexDigits[byte0 >>> 4 & 0xf];
				str[k++] = hexDigits[byte0 & 0xf];
			}
			return new String(str);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}
	
	/**
	 * 
	 * @return String 返回随机验证码
	 */
	public static String generateVerificationCode(){
		Random random = new Random();
		String verificationCode = "";
		for (int i = 0; i < 4; i++){
			String rand = String.valueOf(random.nextInt(10));
			verificationCode += rand;
		}
		return verificationCode;
	}
	
	/**
	 * @param String 待加密密码
	 * @return String 加密后密码
	 */
	public static String generateMd5Code(String password){
		SimpleDateFormat sdf = new SimpleDateFormat("yyMMddhhmmss");
		String sourceStr = password + sdf.format(new Date());
		String md5Code = null;
        md5Code = md5Encipher(sourceStr);
        md5Code = md5Code.substring(0, 5);
        return md5Code;  
	}
	
	/**
	 * 
	 * 产生MD5password或验证密码正确
	 * @param String password 用户密码
	 * @param String md5code md5码
	 * @return String md5password
	 */
	public static String generate_checkMd5Password(String password, String md5Code){
		String sourceStr = password + md5Code;
		String md5Password = md5Encipher(sourceStr);
		return md5Password;
	}
	
	/**
	 * 
	 * @param String md5password
	 * @return String 产生token
	 */
	public static String generateToken(String md5password){
		SimpleDateFormat sdf = new SimpleDateFormat("yyMMddhhmmss");
		String date = sdf.format(new Date());
		return md5Encipher(md5password + date);
	}
	
	/**
	 * 批量发送短消息
	 * @param phonenumber
	 * @param verificationCode
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static String sendMsg(String phonenumber, String verificationCode) throws UnsupportedEncodingException{
		String msg = "【微手语】:您的验证码为" + verificationCode + "。如非本人操作，请忽略该短信。";
//		String msg = "MicroSigns: Your identifying code is " + verificationCode + ".";
		String[] phonenumbers = new String[]{phonenumber};
		VerificationCodeMessage verificationCodeMessage = new VerificationCodeMessage("batchSendSms", entNo, account, password, phonenumbers, msg);
		JSONObject result = null;
		try {
            //创建连接
            URL url = new URL(interUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setRequestMethod("POST");
            connection.setUseCaches(false);
            connection.setInstanceFollowRedirects(true);
            connection.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
            connection.connect();

            //POST请求
            DataOutputStream out = new DataOutputStream(connection.getOutputStream());
            JSONObject obj = JSONObject.fromObject(verificationCodeMessage);
            System.out.println(obj);
            out.write(obj.toString().getBytes("UTF-8"));
            out.flush();
            out.close();

            //读取响应
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String lines;
            StringBuffer sb = new StringBuffer("");
            while ((lines = reader.readLine()) != null) {
                lines = new String(lines.getBytes(), "UTF-8");
                sb.append(lines);
            }
            result = JSONObject.fromObject(sb.toString());
            System.out.println(sb);
            reader.close();
            connection.disconnect();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
		int resultFlag = result.getInt("resultFlag");
		String resultMsg = "success";
		if (resultFlag == 0){
			String results = result.getString("results");
			JSONObject resultsjson = JSONObject.fromObject(results);
			System.out.println(resultsjson.toString());
			resultMsg = resultsjson.getString("errorMsg");
			System.out.println(resultMsg);
		}
		return resultMsg;
	}
	
	/**
	 * 批量发送模板消息
	 * @param phonenumber
	 * @param verificationCode
	 * @return
	 */
	public static String sendTemplateMsg(String phonenumber, String verificationCode){
		DestCntList[] destCntList = new DestCntList[]{new DestCntList(phonenumber, verificationCode)};
		VerificationCodeTemplateMessage verificationCodeTemplateMessage = new VerificationCodeTemplateMessage("batchTemplateSms", entNo, account, password, templateType, templateNo, destCntList);
		JSONObject result = null;
		try {
            //创建连接
            URL url = new URL(interUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setRequestMethod("POST");
            connection.setUseCaches(false);
            connection.setInstanceFollowRedirects(true);
            connection.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
            connection.connect();

            //POST请求
            DataOutputStream out = new DataOutputStream(connection.getOutputStream());
            JSONObject obj = JSONObject.fromObject(verificationCodeTemplateMessage);
            System.out.println(obj);
            out.write(obj.toString().getBytes());
            out.flush();
            out.close();

            //读取响应
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String lines;
            StringBuffer sb = new StringBuffer("");
            while ((lines = reader.readLine()) != null) {
                lines = new String(lines.getBytes(), "utf-8");
                sb.append(lines);
            }
            result = JSONObject.fromObject(sb.toString());
            System.out.println(sb);
            reader.close();
            // 断开连接
            connection.disconnect();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
		int resultFlag = result.getInt("resultFlag");
		String resultMsg = "success";
		if (resultFlag == 0){
			String results = result.getString("results");
			JSONObject resultsjson = JSONObject.fromObject(results);
			System.out.println(resultsjson.toString());
			resultMsg = resultsjson.getString("errorMsg");
			System.out.println(resultMsg);
		}
		return resultMsg;
	}
	
}
