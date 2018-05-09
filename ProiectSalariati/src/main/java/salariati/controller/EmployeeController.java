package salariati.controller;

import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import salariati.exception.EmployeeException;
import salariati.model.Employee;
import salariati.repository.interfaces.EmployeeRepositoryInterface;
import salariati.validator.EmployeeValidator;

public class EmployeeController {
	
	private EmployeeRepositoryInterface employeeRepository;
	private EmployeeValidator employeeValidator;

	public EmployeeController(EmployeeRepositoryInterface employeeRepository, EmployeeValidator validator) {
		this.employeeRepository = employeeRepository;
		this.employeeValidator = validator;
	}
	
	public void addEmployee(String[] employeeAttrs) throws EmployeeException {
	    Employee employee = Employee.getEmployeeFromString(employeeAttrs);
		if(!employeeValidator.isValid(employee)){
			throw new EmployeeException("Invalid employee");
		}

		List<Employee> employees = employeeRepository.getEmployeeList();
		for(Employee currentEmployee : employees){
			if(currentEmployee.getCnp().equals(employee.getCnp())){
				throw new EmployeeException("There's already an employee with this cnp");
			}
		}

		employeeRepository.addEmployee(employee);
	}
	
	public List<Employee> getEmployeesList() {
		List<Employee> employees = employeeRepository.getEmployeeList();
		employees.sort((o1, o2) -> {
            if(o1.getSalary().equals(o2.getSalary())){
                String age1 = o1.getCnp().substring(1,3);
                String age2 = o2.getCnp().substring(1,3);
                if(!age1.equals(age2))
                    return age1.compareTo(age2);
                String month1 = o1.getCnp().substring(3,5);
                String month2 = o2.getCnp().substring(3,5);
                if(!month1.equals(month2))
                    return month1.compareTo(month2);
                return o1.getCnp().substring(5,7).compareTo(o2.getCnp().substring(5,7));
            }
            return o2.getSalary().compareTo(o1.getSalary());
        });
		return employeeRepository.getEmployeeList();
	}
	
	public void modifyEmployee(String[] employeeAttrs) throws EmployeeException {
        Employee newEmployee = Employee.getEmployeeFromString(employeeAttrs);

        if(!employeeValidator.isValid(newEmployee)){
            throw new EmployeeException("New employee is invalid");
        }

		List<Employee> employees = employeeRepository.getEmployeeList()
				.stream()
				.filter(employee -> employee.getCnp().equals(newEmployee.getCnp()))
				.collect(Collectors.toList());

        if(employees.size() != 1){
			throw new EmployeeException("Invalid employee cnp");
		}

		employeeRepository.modifyEmployee(employees.get(0), newEmployee);
	}

	public void deleteEmployee(String[] employeeAttrs) throws EmployeeException {
        Employee employee = Employee.getEmployeeFromString(employeeAttrs);
        employeeRepository.deleteEmployee(employee);
	}
	
}
