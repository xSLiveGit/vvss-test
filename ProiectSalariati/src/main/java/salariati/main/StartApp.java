package salariati.main;

import salariati.exception.EmployeeException;
import salariati.model.Employee;
import salariati.repository.implementations.EmployeeImpl;
import salariati.repository.interfaces.EmployeeRepositoryInterface;
import salariati.repository.mock.EmployeeMock;
import salariati.validator.EmployeeValidator;
import salariati.controller.EmployeeController;
import salariati.enumeration.DidacticFunction;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.function.Consumer;

//functionalitati
//i.	 adaugarea unui nou angajat (nume, prenume, CNP, functia didactica, salariul de incadrare);
//ii.	 modificarea functiei didactice (asistent/lector/conferentiar/profesor) a unui angajat;
//iii.	 afisarea salariatilor ordonati descrescator dupa salariu si crescator dupa varsta (CNP).

public class StartApp {


	public static void main(String[] args) throws EmployeeException {

		EmployeeRepositoryInterface employeesRepository = new EmployeeImpl("employeeDB/employees.txt");
        EmployeeValidator validator = new EmployeeValidator();
        EmployeeController employeeController = new EmployeeController(employeesRepository, validator);
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

		boolean more = true;
		while(more)
        {
            more = run(employeeController, reader);
        }
	}

    private static boolean run(EmployeeController employeeController, BufferedReader reader) {
        String message =    "1 - Add new employee\n" +
                            "2 - Update employee\n" +
                            "3 - Delete employee\n" +
                            "4 - Show employees\n" +
                            "X - exit";
        System.out.println(message);

        try {
            return readCommandAndExecute(employeeController, reader);
        }
        catch (EmployeeException e) {
            System.out.println(e.getMessage());
        }
        catch (IOException e) {
            e.printStackTrace();
            System.out.println("Invalid option. Try again");
        }

        return true;
    }

    private static boolean readCommandAndExecute(
            EmployeeController employeeController,
            BufferedReader reader
    ) throws IOException, EmployeeException {
        String currentLine = reader.readLine();
        boolean more = true;
        switch (currentLine)
        {
            case "1":
                addEmployeeCommand(employeeController, reader);
                break;
            case "2":
                updateEmployeeCommand(employeeController, reader);
                break;
            case "3":
                deleteEmployeeCommand(employeeController, reader);
                break;
            case "4":
                printEmployeesSortedByAge(employeeController);
                break;
            case "X":
                more = false;
                break;
            default:
                System.out.println("Invalid command. Try again");
        }
        return more;
    }

    private static void printEmployeesSortedByAge(EmployeeController employeeController) {
        employeeController.getEmployeesList().forEach(employee -> System.out.println(employee.toString()));
    }

    private static void deleteEmployeeCommand(EmployeeController employeeController, BufferedReader reader) throws IOException, EmployeeException {
        String[] employeeAttr = readEmployee(reader);
        employeeController.deleteEmployee(employeeAttr);
    }

    private static void updateEmployeeCommand(EmployeeController employeeController, BufferedReader reader) throws IOException, EmployeeException {
        String[] employeeAttr = readEmployee(reader);
        employeeController.modifyEmployee(employeeAttr);
    }

    private static void addEmployeeCommand(EmployeeController employeeController, BufferedReader reader) throws IOException, EmployeeException {
        String[] employeeAttr = readEmployee(reader);
        employeeController.addEmployee(employeeAttr);
    }

    private static String[] readEmployee(BufferedReader reader) throws IOException {
        System.out.println("Write an employee");
        String employeeString = reader.readLine();
        return employeeString.split(";");
    }

}
