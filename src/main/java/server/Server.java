package server;

import dao.*;
import models.Employee;
import models.Unit;
import util.IoUtils;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

import java.io.DataInputStream;
import java.io.IOException;

public class Server {
    private ServerSocket serverSocket;
    private final int port;
    private DataInputStream in;
    private DataOutputStream out;
    private final EmployeeDao employeeDao;
    private final UnitDao unitDao;

    public Server(int port) {
        this.port = port;
        this.employeeDao = new EmployeeDao();
        this.unitDao = new UnitDao();
    }

    public void start() throws IOException {
        serverSocket = new ServerSocket(port);
        System.out.println("Server started");
        while (true) {
            Socket clientSocket = serverSocket.accept();
            System.out.println("Client accepted");

            in = new DataInputStream(clientSocket.getInputStream());
            out = new DataOutputStream(clientSocket.getOutputStream());

            while (processQuery());
        }
    }

    private boolean processQuery() {
        System.out.println("0");
        try {
            String query = IoUtils.readString(in);
            System.out.println(query);

            switch (query) {
                case "insertUnit" -> {
                    Unit unit = IoUtils.readUnit(in);
                    boolean result = unitDao.insert(unit);
                    out.writeBoolean(result);
                }

                case "deleteUnit" -> {
                    int id = in.readInt();
                    boolean result = unitDao.deleteById(id);
                    out.writeBoolean(result);
                }

                case "insertEmployee" -> {
                    System.out.println("1");
                    Employee employee = IoUtils.readEmployee(in);
                    System.out.println("2");
                    boolean result = employeeDao.insert(employee);
                    System.out.println("3");
                    out.writeBoolean(result);
                }

                case "deleteEmployee" -> {
                    int id = in.readInt();
                    boolean result = employeeDao.deleteById(id);
                    out.writeBoolean(result);
                }

                case "updateEmployee" -> {
                    Employee employee = IoUtils.readEmployee(in);
                    boolean result = employeeDao.update(employee);
                    out.writeBoolean(result);
                }

                case "moveToAnotherUnit" -> {
                    int playerId = in.readInt();
                    int newTeamId = in.readInt();
                    boolean result = employeeDao.moveToAnotherUnit(playerId, newTeamId);
                    out.writeBoolean(result);
                }

                case "findEmployeesByUnitName" -> {
                    String unitName = IoUtils.readString(in);
                    List<Employee> result = employeeDao.findByUnitName(unitName);
                    writeListOfEmployees(result);
                }

                case "findAllUnits" -> {
                    List<Unit> units = unitDao.findAll();
                    System.out.println("we find!");
                    writeListOfUnits(units);
                }

                default -> {
                    return false;
                }
            }

            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private void writeListOfEmployees(List<Employee> employees) throws IOException {
        out.writeInt(employees.size());

        for (Employee employee : employees) {
            IoUtils.writeEmployee(out, employee);
        }
    }

    private void writeListOfUnits(List<Unit> units) throws IOException {
        out.writeInt(units.size());

        for (Unit unit : units) {
            IoUtils.writeUnit(out, unit);
        }
    }

    public static void main(String[] args) {
        Server server = new Server(5088);
        try {
            server.start();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}