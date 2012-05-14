package quhao;

/**
 * User entity. @author MyEclipse Persistence Tools
 */
public class User extends AbstractUser implements java.io.Serializable {

	// Constructors

	/** default constructor */
	public User() {
	}

	/** minimal constructor */
	public User(UserId id) {
		super(id);
	}

	/** full constructor */
	public User(UserId id, Integer age) {
		super(id, age);
	}

}
