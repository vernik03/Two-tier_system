package client;

import models.Employee;
import models.Unit;
import rmi.RmiServer;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class Client {
    public static void main(String[] args)
            throws MalformedURLException, NotBoundException, RemoteException, InterruptedException {
        RmiServer server = (RmiServer) Naming.lookup("//localhost:22222/server");

        System.out.println("Connected");
        boolean res;
        res =  server.insertUnit(new Unit(1, "Recruitment_unit"));
        System.out.println("Insert unit Recruitment_unit " + res);
        res =  server.insertUnit(new Unit(2, "Accounting_unit"));
        System.out.println("Insert unit Accounting_unit " + res);
        res =  server.insertUnit(new Unit(3, "Training_unit"));
        System.out.println("Insert unit Training_unit " + res);


        res = server.insertEmployee(new Employee(1, 1, "Petrov", 1000));
        System.out.println("Insert new employee " + res);
        res = server.insertEmployee(new Employee(2, 1, "Zelechyn", 1700));
        System.out.println("Insert new employee " + res);
        res = server.insertEmployee(new Employee(3, 1, "Kilko", 2000));
        System.out.println("Insert new employee " + res);

        res = server.insertEmployee(new Employee(4, 2, "Mirova", 1000));
        System.out.println("Insert new employee " + res);
        res = server.insertEmployee(new Employee(5, 2, "Nevedrov", 2700));
        System.out.println("Insert new employee " + res);
        res = server.insertEmployee(new Employee(6, 3, "Frankov", 3000));
        System.out.println("Insert new employee " + res);


        res = server.moveToAnotherUnit(1, 3);
        System.out.println("Move employee with id 1 to unit Training_unit " + res);
        res = server.deleteEmployee(4);
        System.out.println("Delete employee with id 4 " + res);
        res = server.deleteEmployee(5);
        System.out.println("Delete employee with id 5 " + res);
        res = server.deleteUnit(2);
        System.out.println("Delete unit with id 2 " + res);
        //System.out.println(server.findEmployeesByUnitName("Recruitment_unit"));
        //System.out.println("Find employees for Recruitment_unit");
        System.out.println(server.findAllUnits());
        System.out.println("Find all units");
//        server.disconnect();

        System.out.println("Disconnected");
    }
}