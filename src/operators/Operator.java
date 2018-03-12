package operators;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class Operator {
	protected Session session = null;
	
	public Operator(){
		Configuration configuration = new Configuration();
		SessionFactory sessionFactory = configuration.configure().buildSessionFactory();
		this.session = sessionFactory.openSession();
	}
	
	public Session getSession(){
		return session;
	}
	
	public void closeSession() throws HibernateException {  
        if (session != null) {  
            session.close();  
        }  
    }  
}
