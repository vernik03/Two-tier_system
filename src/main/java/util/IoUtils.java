package util;

import models.*;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import static java.lang.String.join;

public final class IoUtils {

    private IoUtils() {}

    public static void writeString(DataOutputStream out, String str) throws IOException {
       out.writeUTF(str);
    }

    public static String readString(DataInputStream in) throws IOException {
        String data = in.readUTF();
        return data;
    }

    public static Employee readEmployee(DataInputStream in) throws IOException {
        Employee employee = new Employee();

        String data = in.readUTF();
        String[] brandData = data.split(";");
        employee.setId(Integer.parseInt(brandData[0]));
        employee.setUnit_id(Integer.parseInt(brandData[1]));
        employee.setSalary(Integer.parseInt(brandData[2]));
        employee.setName(brandData[3]);

        return employee;
    }

    public static Unit readUnit(DataInputStream in) throws IOException {
        Unit unit = new Unit();

        String data = in.readUTF();
        String[] unitData = data.split(";");
        unit.setId(Integer.parseInt(unitData[0]));
        unit.setName(unitData[1]);

        return unit;
    }

    public static void writeEmployee(DataOutputStream out, Employee employee) throws IOException {
        out.writeUTF(join(";", Integer.toString(employee.getId()),  Integer.toString(employee.getUnit_id()),  Integer.toString(employee.getSalary()), employee.getName()));
    }

    public static void writeUnit(DataOutputStream out, Unit unit) throws IOException {
        out.writeUTF(join(";", Integer.toString(unit.getId()), unit.getName()));
    }

}