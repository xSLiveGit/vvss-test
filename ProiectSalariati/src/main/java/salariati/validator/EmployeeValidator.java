package salariati.validator;

import salariati.enumeration.DidacticFunction;
import salariati.model.Employee;

public class EmployeeValidator {
	
	public EmployeeValidator(){}

	public boolean isValid(Employee employee) {
		if(	employee == null ||
			employee.getSalary() == null ||
			employee.getCnp() == null ||
			employee.getFunction() == null ||
			employee.getLastName() == null
			)
		{
			return false;
		}

		boolean isLastNameValid  = employee.getLastName().matches("[a-zA-Z]+") && (employee.getLastName().length() >= 1) && (employee.getLastName().length() <= 255);
		boolean isCNPValid       = employee.getCnp().matches("[1-9][0-9]+") && (employee.getCnp().length() == 13);
		boolean isFunctionValid  = employee.getFunction().equals(DidacticFunction.ASISTENT) ||
								   employee.getFunction().equals(DidacticFunction.LECTURER) ||
								   employee.getFunction().equals(DidacticFunction.TEACHER);
		boolean isSalaryValid    = employee.getSalary().matches("[0-9]+") && (employee.getSalary().length() >= 1) && (Integer.parseInt(employee.getSalary()) > 0);
		
		return isLastNameValid && isCNPValid && isFunctionValid && isSalaryValid;
	}

	
}
