package servlet;

import items.UserItem;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;

import net.sf.json.JSONObject;
import operators.UserOperator;
import results.UserRegistRlt;
import HibernateCode.User;
import utils.CommonUtil;
import utils.PrintWriterHelper;
import utils.SignInUtil;

/**
 * Servlet implementation class User_Regist
 */
@WebServlet("/User_Regist")
public class User_Regist extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public User_Regist() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 * @return RegistRlt in json
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("User_Regist doGet()");
		CommonUtil.logger.info("User_Regist doGet()");
		PrintWriter out = PrintWriterHelper.getPrintWriter(request, response);
		String phonenumber = "";
		phonenumber = request.getParameter("phonenumber");
		String password = "";
		password = request.getParameter("password");
		JSONObject userRegistRltArray = null;
		UserOperator userOperator = new UserOperator();
		
		if (userOperator.ifPhonenumberExists(phonenumber)){
			UserRegistRlt userRegistRlt = new UserRegistRlt(1, "手机号已注册", new UserItem());
			System.out.println(phonenumber + "手机号已注册");
			CommonUtil.logger.info(phonenumber + "手机号已注册");
			userRegistRltArray = JSONObject.fromObject(userRegistRlt);
		}
		else{
			String md5code = SignInUtil.generateMd5Code(password);
			String md5password = SignInUtil.generate_checkMd5Password(password, md5code);
			String token = "TODO";//TODO token
			String nickname = phonenumber;
			String headportrait = "头像";
			String sex = "保密";
			String signature= "个性签名";
			String place = "北京市海淀区";			
			User user = new User(0, phonenumber, md5code, md5password, token, nickname, headportrait, sex, signature, place, new Date());
			System.out.println("成功生成user");
			userOperator.insert(user);
			System.out.println("成功写入user");
			int userid = userOperator.getUseridByPhonenumber(phonenumber);
			user.setUserid(userid);	
			UserRegistRlt userRegistRlt = new UserRegistRlt(0, "success", new UserItem(user));
			System.out.println(phonenumber + "注册成功");
			CommonUtil.logger.info(phonenumber + "注册成功");
			userRegistRltArray = JSONObject.fromObject(userRegistRlt);
		}
		out.write(userRegistRltArray.toString());
		out.flush();
		out.close();
		userOperator.closeSession();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("User_Regist doPost()");
		CommonUtil.logger.info("User_Regist doPost()");
		doGet(request, response);
	}

}
