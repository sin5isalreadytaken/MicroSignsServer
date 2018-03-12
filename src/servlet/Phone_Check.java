package servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import results.PhoneCheckRlt;
import net.sf.json.JSONObject;
import utils.CommonUtil;
import utils.PrintWriterHelper;
import utils.SignInUtil;

/**
 * Servlet implementation class Phone_Check
 */
@WebServlet("/Phone_Check")
public class Phone_Check extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Phone_Check() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 * @return PhoneCheckRlt in json
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("Phone_Check doGet()");
		CommonUtil.logger.info("Phone_Check doGet()");
		PrintWriter out = PrintWriterHelper.getPrintWriter(request, response);
		String phoneNumber = request.getParameter("phonenumber");
		String verificationCode = SignInUtil.generateVerificationCode();
		JSONObject phoneCheckRltArray = null;
		
		String result = SignInUtil.sendMsg(phoneNumber, verificationCode);
		if ("success".equals(result)){
			PhoneCheckRlt phoneCheckRlt = new PhoneCheckRlt(0, "result", verificationCode);
			System.out.println(phoneNumber + "成功获取验证码");
			CommonUtil.logger.info(phoneNumber + "成功获取验证码");
			phoneCheckRltArray = JSONObject.fromObject(phoneCheckRlt);
		}
		else {
			PhoneCheckRlt phoneCheckRlt = new PhoneCheckRlt(1, result, verificationCode);
			System.out.println(phoneNumber + "获取验证码失败");
			CommonUtil.logger.info(phoneNumber + "获取验证码失败");
			phoneCheckRltArray = JSONObject.fromObject(phoneCheckRlt);
		}
		
		out.write(phoneCheckRltArray.toString());
		out.flush();
		out.close();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("Phone_Check doPost()");
		CommonUtil.logger.info("Phone_Check doPost()");
		doGet(request, response);
	}

}
