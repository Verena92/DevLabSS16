package de.hdm.wim.events.model;

import java.util.List;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Representation of a company as it is saved by the document representation.
 * The Lists 'employees', 'projects' and 'products' can be used to find connections
 * with other entities (such as 'Product', 'Employee' or 'Project').
 * 
 * @author Jens Lindner, Max Harhoff, Sebastian Vaas, Stefan Sigel
 *
 */
@Entity
public class Company {

	@Id
	private String companyID;
	private String companyName;
	private String industrialSector;
	private String headquarter;
	private int numberEmployee;

	@ElementCollection
	private List<String> employees;
	@ElementCollection
	private List<String> projects;
	
	public Company(String companyID, String companyName, String sector, String headquarter, List<String> employees, List<String> projects, int numberEmployee) {
		this.companyID = companyID;
		this.companyName = companyName;
		this.industrialSector = sector;
		this.headquarter = headquarter;
		this.employees = employees;
		this.projects = projects;
		this.numberEmployee = numberEmployee;
	}

    /**
	 * Default constructor needed for JSON Parsing
	 */
	public Company() {
		
	}
	
	public int getNumberEmployee() {
		return numberEmployee;
	}

	public void setNumberEmployee(int numberEmployee) {
		this.numberEmployee = numberEmployee;
	}

	public String getIndustrialSector() {
		return industrialSector;
	}

	public void setIndustrialSector(String industrialSector) {
		this.industrialSector = industrialSector;
	}
	
	public String getCompanyID() {
		return companyID;
	}

	public void setCompanyID(String companyID) {
		this.companyID = companyID;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getHeadquarter() {
		return headquarter;
	}

	public void setHeadquarter(String headquarter) {
		this.headquarter = headquarter;
	}

	public List<String> getEmployees() {
		return employees;
	}

	public void setEmployees(List<String> employees) {
		this.employees = employees;
	}

	public List<String> getProjects() {
		return projects;
	}

	public void setProjects(List<String> projects) {
		this.projects = projects;
	}

	@Override
	public String toString() {
		return "Company [companyID=" + companyID + ", companyName=" + companyName + ", industrialSector=" + industrialSector + ", headquarter=" + headquarter + ", numberEmployee="
				+ numberEmployee + ", employees=" + employees + ", projects=" + projects + "]";
	}
	
	
}
