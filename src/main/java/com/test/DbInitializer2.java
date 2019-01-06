package com.test;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.annotation.Resource;
import javax.ejb.Singleton;
import javax.sql.DataSource;

@Singleton
public class DbInitializer2 {

    private boolean initialized = false;

    @Resource(lookup = "java:jboss/datasources/test2_postgresql")
    private DataSource dataSource;

    public boolean isInitialized() {
        return initialized;
    }

    public void setInitialized(boolean initialized) {
        this.initialized = initialized;
    }

    public void initializeDb() {
        if (!tablesExists()) {
            try {
                System.out.println("CREATING TABLES AND INSERTING DATA...");

                Connection connection = dataSource.getConnection();
                Statement stmt = connection.createStatement();
                stmt.executeUpdate("DROP TABLE IF EXISTS emp");
                stmt.executeUpdate("DROP TABLE IF EXISTS dept");
                stmt.executeUpdate("CREATE TABLE emp (empno INTEGER, ename TEXT, job TEXT, mgr INTEGER, hiredate DATE, sal INTEGER, comm INTEGER, deptno INTEGER)");
                stmt.executeUpdate("CREATE TABLE dept (deptno INTEGER, dname TEXT, loc TEXT)");
                // dept table
                stmt.executeUpdate("INSERT INTO DEPT (deptno, dname, loc) VALUES (10, 'ACCOUNTING', 'NEW YORK'), (20, 'RESEARCH', 'DALLAS'), (30, 'SALES', 'CHICAGO'), (40, 'OPERATIONS', 'BOSTON')");

                // emp table
                stmt.executeUpdate("INSERT INTO EMP (empno, ename, job, mgr, hiredate, sal, comm, deptno) VALUES " +
                                    "(7369, 'SMITH', 'CLERK', 7902, '1980-12-17', 800, NULL, 20), " +
                                    "(7499, 'ALLEN', 'SALESMAN', 7698, '1981-02-20', 1600, 300, 30), " +
                                    "(7521, 'WARD', 'SALESMAN', 7698, '1981-02-22', 1250, 500, 30), " +
                                    "(7566, 'JONES', 'MANAGER', 7839, '1981-04-02', 2975, NULL, 20), " +
                                    "(7654, 'MARTIN', 'SALESMAN', 7698, '1981-09-28', 1250, 1400, 30), " +
                                    "(7698, 'BLAKE', 'MANAGER', 7839, '1981-05-01', 2850, NULL, 30), " +
                                    "(7782, 'CLARK', 'MANAGER', 7839, '1981-06-09', 2450, NULL, 10), " +
                                    "(7788, 'SCOTT', 'ANALYST', 7566, '1982-12-09', 3000, NULL, 20), " +
                                    "(7839, 'KING', 'PRESIDENT', NULL, '1981-11-17', 5000, NULL, 10), " +
                                    "(7844, 'TURNER', 'SALESMAN', 7698, '1981-09-08', 1500, 0, 30), " +
                                    "(7876, 'ADAMS', 'CLERK', 7788, '1983-01-12', 1100, NULL, 20), " +
                                    "(7900, 'JAMES', 'CLERK', 7698, '1981-12-03', 950, NULL, 30), " +
                                    "(7902, 'FORD', 'ANALYST', 7566, '1981-12-03', 3000, NULL, 20), " +
                                    "(7934, 'MILLER', 'CLERK', 7782, '1982-01-23', 1300, NULL, 10)");

                initialized = true;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        else{
            initialized = true;
            System.out.println("CALLED initializeDb BUT TABLES EXISTS...");
        }

    }

    private boolean tablesExists() {
        boolean exists = false;

        try {
            Connection connection = dataSource.getConnection();
            Statement stmt = connection.createStatement();
            ResultSet resultSet = stmt.executeQuery("SELECT ename, job FROM emp ORDER BY ename");
            resultSet.close();
            stmt.close();
            connection.close();
            exists = true;
        } catch (SQLException e) {
            exists = false;
            System.out.println("TABLE DOES NOT SEEM TO EXIST WHEN CHECKING. ERROR MESSAGE = " + e.getMessage());
        }

        return exists;
    }

}