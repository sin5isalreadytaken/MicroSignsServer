
import HibernateCode.User;
import operators.UserOperator;

public class Test {

	//TODO delete
		public static void main(String[] args){
			UserOperator userOperator = new UserOperator();
//			User user = userOperator.getUserByToken("6f6385ef66df5e8343c9a8fca07c21d0");
//			user.setInfoByInfoname("nickname", "tiny law");
//			userOperator.updateUser(user);
			userOperator.updatePassword("15583675009", "123456");
			userOperator.closeSession();
		}
}
