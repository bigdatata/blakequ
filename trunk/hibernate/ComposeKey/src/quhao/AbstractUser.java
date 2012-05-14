package quhao;

/**
 * AbstractUser entity provides the base persistence definition of the User
 * entity. @author MyEclipse Persistence Tools
 */

public abstract class AbstractUser implements java.io.Serializable {

	// Fields

	private UserId id;
	private Integer age;

	// Constructors

	/** default constructor */
	public AbstractUser() {
	}

	/** minimal constructor */
	public AbstractUser(UserId id) {
		this.id = id;
	}

	/** full constructor */
	public AbstractUser(UserId id, Integer age) {
		this.id = id;
		this.age = age;
	}

	// Property accessors

	public UserId getId() {
		return this.id;
	}

	public void setId(UserId id) {
		this.id = id;
	}

	public Integer getAge() {
		return this.age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}
	
	public String toString(){
		return "name:"+id.getFirstname()+id.getLastname()+", age:"+age;
	}

}