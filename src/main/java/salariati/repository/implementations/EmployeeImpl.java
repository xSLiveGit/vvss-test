package salariati.repository.implementations;

import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import salariati.exception.EmployeeException;

import salariati.model.Employee;

import salariati.repository.interfaces.EmployeeRepositoryInterface;
import salariati.validator.EmployeeValidator;

public class EmployeeImpl implements EmployeeRepositoryInterface {
	
	private final String employeeDBFile;
	private EmployeeValidator employeeValidator = new EmployeeValidator();

    public EmployeeImpl(String employeeDBFile) {
        this.employeeDBFile = employeeDBFile;
    }

    @Override
	public void addEmployee(Employee employee) throws EmployeeException {
		if( employeeValidator.isValid(employee) ) {
            appendEmployee(employee);
		}
	}

	@Override
	public void deleteEmployee(Employee employee) throws EmployeeException {
		List<Employee> employeeList = getEmployeeList()
				.stream()
				.filter(employeeToTest -> !employeeToTest.getCnp().equals(employee.getCnp()))
				.collect(Collectors.toList());

        overwriteEmployees(employeeList);
	}

	@Override
	public void modifyEmployee(Employee oldEmployee, Employee newEmployee) throws EmployeeException {
        List<Employee> employeeList = getEmployeeList();
        employeeList.forEach(employee -> {
            if(employee.getCnp().equals(newEmployee.getCnp())){
                employee.setSalary(newEmployee.getSalary());
                employee.setLastName(newEmployee.getLastName());
                employee.setFunction(newEmployee.getFunction());
            }
        });

        overwriteEmployees(employeeList);
	}

	private void appendEmployee(Employee employee) throws EmployeeException {
        try(BufferedWriter writer = new BufferedWriter(new FileWriter(employeeDBFile, true))) {
            writer.write(employee.toString());
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
            throw new EmployeeException("Is impossible to add given empoyee");
        }
    }

	private void overwriteEmployees(List<Employee> employees) throws EmployeeException {
        try(BufferedWriter writer = new BufferedWriter(new FileWriter(employeeDBFile, false))) {
            writeEmployees(employees,writer);
        } catch (IOException e) {
            e.printStackTrace();
            throw new EmployeeException("Bad file format");
        }
    }

    private void writeEmployees(List<Employee> employees, BufferedWriter writer) throws EmployeeException {
	    for(Employee employee : employees){
            writeEmployee(employee,writer);
        }
    }

    private void writeEmployee(Employee employee, BufferedWriter writer) throws EmployeeException {
        try {
            writer.write(employee.toString());
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
            throw new EmployeeException("Invalid employee");
        }
    }

	//deleteEmployee ar trebui sa aiba o implementare
    //modifyEmployee ar trebui sa aiba o implementare
    //linia 58 - nu ar trebui construit un employee daca nu e folosit
	@Override
	public List<Employee> getEmployeeList() {
		List<Employee> employeeList = new ArrayList<>();
		
		try (BufferedReader br = new BufferedReader(new FileReader(employeeDBFile))){
			String line = br.readLine();
			while (line != null) {
				try {
					Employee employee = Employee.getEmployeeFromString(line.split(";"));
					employeeList.add(employee);
				} catch(EmployeeException ex) {
					System.err.println("Error while reading: " + ex.toString());
				}
                line = br.readLine();
			}
		} catch (IOException e) {
			System.err.println("Error while reading: " + e);
		}
		
		return employeeList;
	}

	@Override
	public List<Employee> getEmployeeByCriteria(String criteria) {
		return getEmployeeList()
                .stream()
                .sorted((o1, o2) -> {
                    String yearO1 = o1.getCnp().substring(3,5);
                    String yearO2 = o2.getCnp().substring(3,5);
                    return yearO1.compareTo(yearO2);
                }).collect(Collectors.toList());
	}

}
