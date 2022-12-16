package client;

import models.*;
import util.IoUtils;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Client {
    private Socket socket;
    private final String host;
    private final int port;
    private DataInputStream in;
    private DataOutputStream out;

    public Client(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public void connect() throws IOException {
        socket = new Socket(host, port);
        in = new DataInputStream(socket.getInputStream());
        out = new DataOutputStream(socket.getOutputStream());
    }

    public void disconnect() throws IOException {
        socket.close();
    }

    public boolean insertUnit(Unit unit) throws IOException {
        IoUtils.writeString(out, "insertUnit");
        IoUtils.writeUnit(out, unit);

        return in.readBoolean();
    }

    public boolean deleteUnit(int id) throws IOException {
        IoUtils.writeString(out, "deleteUnit");
        out.writeInt(id);

        return in.readBoolean();
    }

    public boolean insertEmployee(Employee employee) throws IOException {
        IoUtils.writeString(out, "insertEmployee");
        IoUtils.writeEmployee(out, employee);
        return in.readBoolean();
    }

    public boolean deleteEmployee(int id) throws IOException {
        IoUtils.writeString(out, "deleteEmployee");
        out.writeInt(id);

        return in.readBoolean();
    }

    public boolean updateEmployee(Employee employee) throws IOException {
        IoUtils.writeString(out, "updateEmployee");
        IoUtils.writeEmployee(out, employee);

        return in.readBoolean();
    }

    public boolean moveToAnotherUnit(int employeeId, int newUnitId) throws IOException {
        IoUtils.writeString(out, "moveToAnotherUnit");
        out.writeInt(employeeId);
        out.writeInt(newUnitId);

        return in.readBoolean();
    }

    public List<Employee> findEmployeesByUnitName(String unitName) throws IOException {
        IoUtils.writeString(out, "findEmployeesByUnitName");
        IoUtils.writeString(out, unitName);

        return readEmployees();
    }

    public List<Unit> findAllUnits() throws IOException {
        IoUtils.writeString(out, "findAllUnits");

        return readUnits();
    }

    private List<Employee> readEmployees() throws IOException {
        List<Employee> result = new ArrayList<>();
        int listSize = in.readInt();

        for (int i = 0; i < listSize; i++) {
            result.add(IoUtils.readEmployee(in));
        }

        return result;
    }

    private List<Unit> readUnits() throws IOException {
        List<Unit> result = new ArrayList<>();
        int listSize = in.readInt();
        System.out.println(listSize);

        for (int i = 0; i < listSize; i++) {
            result.add(IoUtils.readUnit(in));
        }

        return result;
    }
}