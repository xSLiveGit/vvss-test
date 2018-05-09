package salariati.repository.interfaces;

import java.util.List;

import salariati.exception.EmployeeException;
import salariati.model.Employee;

public interface EmployeeRepositoryInterface {
	
	void addEmployee(Employee employee) throws EmployeeException;
	void deleteEmployee(Employee employee) throws EmployeeException;
	void modifyEmployee(Employee oldEmployee, Employee newEmployee) throws EmployeeException;
	List<Employee> getEmployeeList();
	List<Employee> getEmployeeByCriteria(String criteria);

}
