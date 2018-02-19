package edu.vt.crest.hr.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Version;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Defines an EmployeeEntity used to represent employee rows in the database.
 */
@Entity(name = "Employee")
@NamedQueries(
  {
    @NamedQuery(
      name = EmployeeEntity.NAMED_QUERY_FIND_BY_ID,
      query = "SELECT e from Employee e WHERE e.id = :id"
    ),
    @NamedQuery(
      name = EmployeeEntity.NAMED_QUERY_LIST_ALL,
      query = "SELECT e from Employee e"
    )
  })
@XmlRootElement
public class EmployeeEntity implements Serializable {

    public static final String NAMED_QUERY_FIND_BY_ID = "EmployeeEntity.findById";

    public static final String NAMED_QUERY_LIST_ALL = "EmployeeEntity.listAll";

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", updatable = false, nullable = false)
	private Long id;

	@Version
	@Column(name = "version")
    @Min(Integer.MIN_VALUE)
    @Max(Integer.MAX_VALUE)
	private int version;

	@Column(name="first_name")
    @Size(min = 1)
	private String firstName;

	@Column(name="last_name")
    @Size(min = 1)
	private String lastName;

	public Long getId() {
		return this.id;
	}

	public void setId(final Long id) {
		this.id = id;
	}

	public int getVersion() {
		return this.version;
	}

	public void setVersion(final int version) {
		this.version = version;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof EmployeeEntity)) {
			return false;
		}
		final EmployeeEntity other = (EmployeeEntity) obj;
		if (id != null) {
			if (!id.equals(other.id)) {
				return false;
			}
		}
		return true;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	@Override
	public String toString() {
		String result = getClass().getSimpleName() + " ";
		if (firstName != null && !firstName.trim().isEmpty())
			result += "firstName: " + firstName;
		if (lastName != null && !lastName.trim().isEmpty())
			result += ", lastName: " + lastName;
		return result;
	}
}