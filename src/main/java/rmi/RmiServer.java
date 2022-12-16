package rmi;

import models.Employee;
import models.Unit;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface RmiServer extends Remote {

    boolean insertUnit(Unit unit) throws RemoteException;

    boolean deleteUnit(int id) throws RemoteException;

    boolean insertEmployee(Employee employee) throws RemoteException;

    boolean deleteEmployee(int id) throws RemoteException;

    boolean updateEmployee(Employee employee) throws RemoteException;

    boolean moveToAnotherUnit(int unitId, int newUnitId) throws RemoteException;

    List<Employee> findEmployeesByUnitName(String unitName) throws RemoteException;

    List<Unit> findAllUnits() throws RemoteException;
}