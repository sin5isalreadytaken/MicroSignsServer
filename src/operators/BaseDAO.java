package operators;

import java.io.Serializable;

import org.hibernate.Transaction;

public class BaseDAO<T> extends Operator{
	
	public void insert(T object){
		Transaction transaction = this.session.beginTransaction();
		this.session.persist(object);
		transaction.commit();
	}
	
	public void update(T object){
		Transaction transaction = this.session.beginTransaction();
		this.session.update(object);
		transaction.commit();
	}
	
	public void delete(T object){
		Transaction transaction = this.session.beginTransaction();
		this.session.delete(object);
		transaction.commit();
	}
	
	public T find(Class <? extends T> clazz, Serializable id){
		Transaction transaction = this.session.beginTransaction();
		T t = this.session.get(clazz, id);
		transaction.commit();
		return t;
	}

}
