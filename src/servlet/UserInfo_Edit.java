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
import net.sf.json.JSONObject;
import operators.UserOperator;
import results.UserInfoEditRlt;
import utils.CommonUtil;
import utils.PrintWriterHelper;

/**
 * Servlet implementation class UserInfo_Edit
 */
@WebServlet("/UserInfo_Edit")
public class UserInfo_Edit extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UserInfo_Edit() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("UserInfo_Edit doGet()");
		CommonUtil.logger.info("UserInfo_Edit doGet()");
		PrintWriter out = PrintWriterHelper.getPrintWriter(request, response);
		String token = request.getParameter("token");
		String infoname = request.getParameter("infoname");
		String infovalue = request.getParameter("infovalue");
		JSONObject userInfoEditRltArray = null;
		UserOperator userOperator = new UserOperator();
		
		
		
		Cookie[] cookies = request.getCookies();
		if (cookies == null || cookies.length == 0)
		System.out.println("Has not visited this website");
		else
		{
		for (int i = 0; i < cookies.length; i++)
		{
		System.out.println("cookie name:" + cookies[i].getName() + "cookie value:" +
		cookies[i].getValue());
		}
		}
		
		
		
		HttpSession session = request.getSession();
		String tokenInSession = (String) session.getAttribute("token");
		if (tokenInSession == null){
			tokenInSession = token;
			System.out.println("未从session中获取到token");
		}
		if (!tokenInSession.equals(token)) {
			UserInfoEditRlt userInfoEditRlt = new UserInfoEditRlt(1, "会话过期，请重新登录", new UserItem());
			System.out.println("会话过期，请重新登录:token=" + token);
			CommonUtil.logger.info("会话过期，请重新登录:token=" + token);
			userInfoEditRltArray = JSONObject.fromObject(userInfoEditRlt);
		}
		else {
			User user = (User) session.getAttribute("user");
			if (user == null){
				System.out.println("未从session中获取到user");
				user = userOperator.getUserByToken(token);
				if (user == null) {
					UserInfoEditRlt userInfoEditRlt = new UserInfoEditRlt(1, "token不存在，请重新登录", new UserItem());
					System.out.println("token不存在，请重新登录:token=" + token);
					CommonUtil.logger.info("token不存在，请重新登录:token=" + token);
					userInfoEditRltArray = JSONObject.fromObject(userInfoEditRlt);
				}
				else {
					user.setInfoByInfoname(infoname, infovalue);
					System.out.println("更改用户资料" + infoname);
					CommonUtil.logger.info("更改用户资料" + infoname);
					userOperator.update(user);
					session.setAttribute("user", user);
					UserInfoEditRlt userInfoEditRlt = new UserInfoEditRlt(0, "success", new UserItem(user));
					System.out.println("更改用户资料成功:token=" + token);
					CommonUtil.logger.info("更改用户资料成功:token=" + token);
					userInfoEditRltArray = JSONObject.fromObject(userInfoEditRlt);
				}
			}
			else {
				user.setInfoByInfoname(infoname, infovalue);
				System.out.println("更改用户资料" + infoname);
				CommonUtil.logger.info("更改用户资料" + infoname);
				userOperator.update(user);
				session.setAttribute("user", user);
				UserInfoEditRlt userInfoEditRlt = new UserInfoEditRlt(0, "success", new UserItem(user));
				System.out.println("更改用户资料成功:token=" + token);
				CommonUtil.logger.info("更改用户资料成功:token=" + token);
				userInfoEditRltArray = JSONObject.fromObject(userInfoEditRlt);
			}
		}
		out.write(userInfoEditRltArray.toString());
		out.flush();
		out.close();
		userOperator.closeSession();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("UserInfo_Edit doPost()");
		CommonUtil.logger.info("UserInfo_Edit doPost()");
		doGet(request, response);
	}

}
