package hibernate;

import org.hibernate.HibernateException;
import org.hibernate.Session;

import hibernate.HibernateUtils;

import junit.framework.TestCase;

public class CsTest extends TestCase {

	/**
	 * inverse与插入
	 */
	public void testAdd(){
		Session session = null;
		try {
			session = HibernateUtils.getSession();
			
			Student s1 = new Student();
			s1.setName("s1");
			
			Student s2 = new Student();
			s2.setName("s2");
			
			Classes c = new Classes();
			c.setName("yangguo");
			/**
			 * 这两个没有建立关联，没有下面的语句是不能插入student的
			s1.setClasses(c);
			s2.setClasses(c);
			 */
			c.getStudents().add(s2);
			c.getStudents().add(s1);
			
			
			session.save(c);
			session.beginTransaction().commit();
		} catch (HibernateException e) {
			// TODO: handle exception
			e.printStackTrace();
			session.getTransaction().rollback();
		} finally{
			HibernateUtils.closeSession(session);
		}
		
	}
	
	public void testAdd2(){
		Session session = null;
		try {
			session = HibernateUtils.getSession();
			
//			Student s1 = new Student();
//			s1.setName("s1");
//			s1.setId(5);
//			session.delete(s1);
			
			Classes c = (Classes) session.load(Classes.class, 15);
			Student s = new Student();
			s.setName("good");
			s.setClasses(c);//如果是学生维护关系，就必须有这个方法(必须明确指定是属于哪个班级，因为班级不会来维护关系的)，
			//否则就可以不需要setClasses,因为是班级来维护，它会update student添加关联
			c.getStudents().add(s);
			
			session.beginTransaction().commit();
		} catch (HibernateException e) {
			// TODO: handle exception
			e.printStackTrace();
			session.getTransaction().rollback();
		} finally{
			HibernateUtils.closeSession(session);
		}
	} 
	
	//更新学生班级，测试inverse的影响
	public void testUpdate(){
		Session session = null;
		try {
			session = HibernateUtils.getSession();
			
			Student s = (Student) session.get(Student.class, 11);
			Classes c = (Classes) session.load(Classes.class, 5);
			s.setClasses(c);
			
			session.beginTransaction().commit();
		} catch (HibernateException e) {
			// TODO: handle exception
			e.printStackTrace();
			session.getTransaction().rollback();
		} finally{
			HibernateUtils.closeSession(session);
		}
	}
	
	/**
	 * 测试级联删除
	 */
	public void testDeleteCascade(){
		Session session = null;
		try {
			session = HibernateUtils.getSession();
			
			/**
			 * a.cascade=all*************
			 * 此时cascade=all,则update,delete,save都会产生级联关系
			 * 当删除classes=1的时候，会：
			 * 1.获取班级为1的所有学生。
			 * 2.将这些学生的班级id设置为null。
			 * 3.删除这些学生。
			 * 4.删除为1的班级。
			 * 从而完成了级联删除。
			 * 
			 * 当更新classes=1的时候(不能更新主控端的主键id)，更改学生集合为null时会：
			 * 1.设置在班级为1的所有学生的classesid=null(inverse=false)
			 * 这样会使学生都成为脏数据，没有班级对应
			 * 
			 * 注：当classes在维护关系(inverse=false)不设置级联的时候也会这样(classes在维护关系，所以就会把关联的学生也修改了)。
			 * 		但是当是students维护关系的时候(inverse=true)不会执行上面操作
			 * 		(因为班级classes不维护关系，所以当关系修改(set为null)的时候，它不会通知学生，故而不会修改学生的classid字段)。
			 * 
			 * b.cascade=none*************
			 * 此时不会又级联删除关系,当删除classes=1的时候，会：
			 * 1.将班级为id=1的所有学生classid设置为null。
			 * 2.删除为1的班级。
			 * 此时不会删除学生，只是将学生的classid字段设置为null
			 * 注：如果在many-to-one,one是主控方，many是级联方。只有主控方的cascade设置才会有效，而被控方无效。
			 * 
			 * 总结：inverse是会影响cascade的(更新会影响，但是删除没影响)，关键是看是维护关系，维护关系的一端会对级联操作有影响
			 * 1.对于删除，inverse不会影响cascade.
			 * 2.对于更新，inverse会影响cascade，当inverse=false(classes维护关系)更新classes会影响students;
			 *   但是inverse=true(student维护关系)更新classes不会影响students(即classesid不变) (上面是通过改变classes的students集合为空)
			 * 3.对于保存，inverse与cascade没多大关联.
			 */
			Classes c = (Classes) session.load(Classes.class, 13);
			
			//级联删除
			session.delete(c);
			
			//级联更新
//			c.setStudents(null);
//			session.update(c);
			
			session.beginTransaction().commit();
		} catch (HibernateException e) {
			// TODO: handle exception
			e.printStackTrace();
			session.getTransaction().rollback();
		} finally{
			HibernateUtils.closeSession(session);
		}
	}
	
	public void testSetLazy(){
		Session session = null;
		try {
			session = HibernateUtils.getSession();
			
			/**
			 * 1.lazy=true(在集合set上设置)
			 * 只发出一条语句，用student对象时才会加载。
			 * 只有用到student的时候才发出查询语句
			 * 
			 * 2.lazy=false(在集合set上设置)
			 * 发出两条语句，会加载所有的students。
			 * c.getStudents().size()发出的语句是通用的语句
			 * 
			 * 3.lazy=extra(在集合set上设置)
			 * 发出一条语句，用student对象时才会加载。
			 * c.getStudents().size()发出的语句是优化过的智能语句
			 * 
			 * 
			 * 3.在<class>上设置的lazy对集合没有影响,只会影响普通属性
			 */
			Classes c = (Classes) session.load(Classes.class, 8);
			System.out.println(c);
			System.out.println(c.getName());
			System.out.println(c.getStudents().size());
			System.err.println(c.getStudents());
			
			
			session.beginTransaction().commit();
		} catch (HibernateException e) {
			// TODO: handle exception
			e.printStackTrace();
			session.getTransaction().rollback();
		} finally{
			HibernateUtils.closeSession(session);
		}
	}

}
