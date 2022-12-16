package rmi;


import dao.EmployeeDao;
import dao.UnitDao;
import models.Employee;
import models.Unit;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class RmiServerImpl extends UnicastRemoteObject implements RmiServer {
    private final EmployeeDao employeeDao;
    private final UnitDao unitDao;
    private final transient ReadWriteLock lock;

    public RmiServerImpl() throws RemoteException {
        this.employeeDao = new EmployeeDao();
        this.unitDao = new UnitDao();
        this.lock = new ReentrantReadWriteLock();
    }

    @Override
    public boolean insertUnit(Unit unit) throws RemoteException {
        try {
            lock.writeLock().lock();
            return unitDao.insert(unit);
        } finally {
            lock.writeLock().unlock();
        }
    }

    @Override
    public boolean deleteUnit(int id) throws RemoteException {
        try {
            lock.writeLock().lock();
            return unitDao.deleteById(id);
        } finally {
            lock.writeLock().unlock();
        }
    }

    @Override
    public boolean insertEmployee(Employee employee) throws RemoteException {
        try {
            lock.writeLock().lock();
            return employeeDao.insert(employee);
        } finally {
            lock.writeLock().unlock();
        }
    }

    @Override
    public boolean deleteEmployee(int id) throws RemoteException {
        try {
            lock.writeLock().lock();
            return employeeDao.deleteById(id);
        } finally {
            lock.writeLock().unlock();
        }
    }

    @Override
    public boolean updateEmployee(Employee employee) throws RemoteException {
        try {
            lock.writeLock().lock();
            return employeeDao.update(employee);
        } finally {
            lock.writeLock().unlock();
        }
    }

    @Override
    public boolean moveToAnotherUnit(int unitId, int newUnitId) throws RemoteException {
        try {
            lock.writeLock().lock();
            return employeeDao.moveToAnotherUnit(unitId, newUnitId);
        } finally {
            lock.writeLock().unlock();
        }
    }

    @Override
    public List<Employee> findEmployeesByUnitName(String unitName) throws RemoteException {
        try {
            lock.readLock().lock();
            return employeeDao.findByUnitName(unitName);
        } finally {
            lock.readLock().unlock();
        }
    }

    @Override
    public List<Unit> findAllUnits() throws RemoteException {
        try {
            lock.readLock().lock();
            return unitDao.findAll();
        } finally {
            lock.readLock().unlock();
        }
    }
}