package operators;

import org.hibernate.Transaction;

import utils.SignInUtil;
import HibernateCode.User;

public class UserOperator extends BaseDAO<User>{
	
	public UserOperator(){
		super();
	}
	
	/**
	 * 通过手机号获取User对象
	 * @param phonenumber
	 * @return User
	 */
	@SuppressWarnings("deprecation")
	public User getUserByPhonenumber(String phonenumber){
		String hql = "select user from User user where phonenumber='" + phonenumber + "'";
		return (User) this.session.createQuery(hql).uniqueResult();
	}
	
	/**
	 * 通过token获取User对象
	 * @param token
	 * @return User
	 */
	@SuppressWarnings("deprecation")
	public User getUserByToken(String token){
		String hql = "select user from User user where token='" + token + "'";
		return (User) this.session.createQuery(hql).uniqueResult();
	}
	
//	/**
//	 * user表插入数据
//	 * @param user 用户类
//	 */
//	public void insertUser(User user){
//		Transaction transaction = this.session.beginTransaction();
//		this.session.save(user);
//		System.out.println("已写入user");
//		transaction.commit();
//	}
	
//	/**
//	 * 更新user表项
//	 * @param user
//	 */
//	public void updateUser(User user){
//		Transaction transaction = this.session.beginTransaction();
//		this.session.update(user);
//		transaction.commit();
//	}
	
	/**
	 * 
	 * @param phonenumber 手机号
	 * @param token 根据手机号和当前时间产生token并写入数据库
	 */
	public void updateToken(String phonenumber, String token){
		Transaction transaction = this.session.beginTransaction();
		String hql = "update User user set token='" + token + "' where phonenumber='" + phonenumber + "'";
		this.session.createQuery(hql).executeUpdate();
		transaction.commit();
	}
	
	/**
	 * 更改密码。重写md5code和md5password
	 * @param phonenumber
	 * @param password
	 */
	public void updatePassword(String phonenumber, String password){
		String md5code = SignInUtil.generateMd5Code(password);
		String md5password = SignInUtil.generate_checkMd5Password(password, md5code);
		String hql = "update User user set md5code='para1',md5password='para2' where phonenumber='para3'";
		hql = hql.replace("para1", md5code).replace("para2", md5password).replace("para3", phonenumber);
		Transaction transaction = this.session.beginTransaction();
		this.session.createQuery(hql).executeUpdate();
		transaction.commit();
	}
	
	/**
	 * 
	 * 检查手机号是否已注册
	 * @param phonenumber
	 * @return true 已注册 false 未注册
	 */
	@SuppressWarnings("deprecation")
	public boolean ifPhonenumberExists(String phonenumber){
		String hql = "select count(*) from User user where phonenumber='" + phonenumber + "'";
		Number number = (Number) this.session.createQuery(hql).uniqueResult();
		if (number.intValue() == 0){
			return false;
		}
		else {
			return true;
		}
	}
	
	/**
	 * 通过手机号查看userid
	 * @param phonenumber
	 * @return int userid
	 */
	@SuppressWarnings("deprecation")
	public int getUseridByPhonenumber(String phonenumber){
		String hql = "select userid from User user where phonenumber='" + phonenumber + "'";
		Number userNum = (Number) this.session.createQuery(hql).uniqueResult();
		return userNum.intValue();
	}
	
}
