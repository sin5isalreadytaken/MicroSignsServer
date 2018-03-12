package servlet;

import items.UserItem;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import HibernateCode.User;
import operators.UserOperator;
import results.UserLoginRlt;
import net.sf.json.JSONObject;
import utils.CommonUtil;
import utils.PrintWriterHelper;
import utils.SignInUtil;

/**
 * Servlet implementation class User_Login
 */
@WebServlet("/User_Login")
public class User_Login extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public User_Login() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 * @return LoginRlt in json
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("User_Login doGet()");
		CommonUtil.logger.info("User_Login doGet()");
		PrintWriter out = PrintWriterHelper.getPrintWriter(request, response);
		String phoneNumber = request.getParameter("phonenumber");
		String password = request.getParameter("password");
		JSONObject loginRltArray = null;
		UserOperator userOperator = new UserOperator();
		
		User user = userOperator.getUserByPhonenumber(phoneNumber);
		if (user == null){
			UserLoginRlt ulr = new UserLoginRlt(1, "账号密码错误", new UserItem());
			System.out.println("账号错误");
			loginRltArray = JSONObject.fromObject(ulr);
		}
		else{
			String md5code = user.getMd5code();
			String md5password = user.getMd5password();
			String passwordCheck = SignInUtil.md5Encipher(password + md5code);
			if (md5password.equals(passwordCheck)){
				String token = SignInUtil.generateToken(md5password);
				user.setToken(token);
				userOperator.update(user);
				HttpSession session = request.getSession(true);
				session.setAttribute("user", user);
				session.setAttribute("token", token);
				session.setMaxInactiveInterval(CommonUtil.maxInactiveInterval);
				String sessionid = session.getId();
				System.out.println(sessionid);
//				Cookie cookie = new Cookie("JSESSIONID", sessionid);
//				response.addCookie(cookie);
//				response.addHeader("Set-Cookie", "JSESSIONID=" + sessionid);
				UserLoginRlt ulr = new UserLoginRlt(0, "success", new UserItem(user));
				System.out.println(phoneNumber + "登录成功");
				CommonUtil.logger.info(phoneNumber + "登录成功");
				loginRltArray = JSONObject.fromObject(ulr);
			}
			else{
				UserLoginRlt ulr = new UserLoginRlt(1, "账号密码错误", new UserItem());
				System.out.println(phoneNumber + "密码错误");
				CommonUtil.logger.info(phoneNumber + "密码错误");
				loginRltArray = JSONObject.fromObject(ulr);
			}
		}
		out.write(loginRltArray.toString());
		out.flush();
		out.close();
		userOperator.closeSession();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("User_Login doPost()");
		CommonUtil.logger.info("User_Login doPost()");
		doGet(request, response);
	}

}
