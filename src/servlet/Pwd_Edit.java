package servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import operators.UserOperator;
import results.PwdEditRlt;
import net.sf.json.JSONObject;
import utils.CommonUtil;
import utils.PrintWriterHelper;

/**
 * Servlet implementation class Pwd_Edit
 */
@WebServlet("/Pwd_Edit")
public class Pwd_Edit extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Pwd_Edit() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("Pwd_Edit doGet()");
		CommonUtil.logger.info("Pwd_Edit doGet()");
		String phonenumber = request.getParameter("phonenumber");
		String password = request.getParameter("password");
		PrintWriter out = PrintWriterHelper.getPrintWriter(request, response);
		JSONObject pwdEditRltArray = null;
		UserOperator userOperator = new UserOperator();
		
		if (userOperator.ifPhonenumberExists(phonenumber)){
			userOperator.updatePassword(phonenumber, password);
			PwdEditRlt pwdEditRlt = new PwdEditRlt(0, "success");
			System.out.println(phonenumber + "更改密码成功");
			CommonUtil.logger.info(phonenumber + "更改密码成功");
			pwdEditRltArray = JSONObject.fromObject(pwdEditRlt);
		}
		else {
			PwdEditRlt pwdEditRlt = new PwdEditRlt(1, "手机号不存在（更改密码）");
			System.out.println(phonenumber + "手机号不存在（更改密码）");
			CommonUtil.logger.info(phonenumber + "手机号不存在（更改密码）");
			pwdEditRltArray = JSONObject.fromObject(pwdEditRlt);
		}
		out.print(pwdEditRltArray.toString());
		out.flush();
		out.close();
		userOperator.closeSession();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("Pwd_Edit doPost()");
		CommonUtil.logger.info("Pwd_Edit doPost()");
		doGet(request, response);
	}

}
