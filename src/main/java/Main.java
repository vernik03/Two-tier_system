import client.Client;

import models.Employee;
import models.Unit;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        Client client = new Client("localhost", 5088);
        try {
            client.connect();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        System.out.println("Connected");
        boolean res;
        res =  client.insertUnit(new Unit(1, "Recruitment_unit"));
        System.out.println("Insert unit Recruitment_unit " + res);
        res =  client.insertUnit(new Unit(2, "Accounting_unit"));
        System.out.println("Insert unit Accounting_unit " + res);
        res =  client.insertUnit(new Unit(3, "Training_unit"));
        System.out.println("Insert unit Training_unit " + res);


        res = client.insertEmployee(new Employee(1, 1, "Petrov", 1000));
        System.out.println("Insert new employee " + res);
        res = client.insertEmployee(new Employee(2, 1, "Zelechyn", 1700));
        System.out.println("Insert new employee " + res);
        res = client.insertEmployee(new Employee(3, 1, "Kilko", 2000));
        System.out.println("Insert new employee " + res);

        res = client.insertEmployee(new Employee(4, 2, "Mirova", 1000));
        System.out.println("Insert new employee " + res);
        res = client.insertEmployee(new Employee(5, 2, "Nevedrov", 2700));
        System.out.println("Insert new employee " + res);
        res = client.insertEmployee(new Employee(6, 3, "Frankov", 3000));
        System.out.println("Insert new employee " + res);


        res = client.moveToAnotherUnit(1, 3);
        System.out.println("Move employee with id 1 to unit Training_unit " + res);
        res = client.deleteEmployee(4);
        System.out.println("Delete employee with id 4 " + res);
        res = client.deleteEmployee(5);
        System.out.println("Delete employee with id 5 " + res);
        res = client.deleteUnit(2);
        System.out.println("Delete unit with id 2 " + res);
        System.out.println(client.findEmployeesByUnitName("Recruitment_unit"));
        System.out.println("Find employees for Recruitment_unit");
        System.out.println(client.findAllUnits());
        System.out.println("Find all units");
        client.disconnect();

        System.out.println("Disconnected");
    }
}