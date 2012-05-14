package quhao;

/**
 * AbstractUserId entity provides the base persistence definition of the UserId
 * entity. @author MyEclipse Persistence Tools
 */

public abstract class AbstractUserId implements java.io.Serializable {

	// Fields

	private String firstname;
	private String lastname;

	// Constructors

	/** default constructor */
	public AbstractUserId() {
	}

	/** full constructor */
	public AbstractUserId(String firstname, String lastname) {
		this.firstname = firstname;
		this.lastname = lastname;
	}

	// Property accessors

	public String getFirstname() {
		return this.firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return this.lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof AbstractUserId))
			return false;
		AbstractUserId castOther = (AbstractUserId) other;

		return ((this.getFirstname() == castOther.getFirstname()) || (this
				.getFirstname() != null
				&& castOther.getFirstname() != null && this.getFirstname()
				.equals(castOther.getFirstname())))
				&& ((this.getLastname() == castOther.getLastname()) || (this
						.getLastname() != null
						&& castOther.getLastname() != null && this
						.getLastname().equals(castOther.getLastname())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result
				+ (getFirstname() == null ? 0 : this.getFirstname().hashCode());
		result = 37 * result
				+ (getLastname() == null ? 0 : this.getLastname().hashCode());
		return result;
	}

}