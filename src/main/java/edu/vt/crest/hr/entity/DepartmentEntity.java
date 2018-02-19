package edu.vt.crest.hr.entity;

import java.io.Serializable;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Version;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Defines a DepartmentEntity used to represent department rows in the database.
 */
@Entity(name = "Department")
@NamedQueries(
  {
    @NamedQuery(
      name = DepartmentEntity.NAMED_QUERY_FIND_BY_ID,
      query = "SELECT d from Department d WHERE d.id = :id"
    ),
    @NamedQuery(
      name = DepartmentEntity.NAMED_QUERY_LIST_ALL,
      query = "SELECT d from Department d"
    )
  })
@XmlRootElement
public class DepartmentEntity implements Serializable {

    public static final String NAMED_QUERY_FIND_BY_ID = "DepartmentEntity.findById";

    public static final String NAMED_QUERY_LIST_ALL = "DepartmentEntity.listAll";

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

	@Column
    @Size(min = 1)
	private String name;

	@Column
    @Size(min = 1)
	private String identifier;

	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "department_id", referencedColumnName = "id")
	private Set<EmployeeEntity> employees;

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

	public Set<EmployeeEntity> getEmployees() {
		return this.employees;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof DepartmentEntity)) {
			return false;
		}
		DepartmentEntity other = (DepartmentEntity) obj;
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIdentifier() {
		return identifier;
	}

	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}

	public void addEmployee(EmployeeEntity employee) {
		this.employees.add(employee);
	}

	@Override
	public String toString() {
		String result = getClass().getSimpleName() + " ";
		if (name != null && !name.trim().isEmpty())
			result += "name: " + name;
		if (identifier != null && !identifier.trim().isEmpty())
			result += ", identifier: " + identifier;
		return result;
	}

}