package com.test;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.annotation.Resource;
import javax.ejb.Singleton;
import javax.sql.DataSource;

@Singleton
public class DbInitializer {

    private boolean initialized = false;

    @Resource(lookup = "java:jboss/datasources/test_mysql")
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
                stmt.executeUpdate("CREATE TABLE emp(empno INT NOT NULL PRIMARY KEY AUTO_INCREMENT, ename VARCHAR(30), job VARCHAR(30), mgr INT, hiredate DATE, sal INT, comm INT, deptno INT)");
                stmt.executeUpdate("CREATE TABLE dept(deptno INT NOT NULL PRIMARY KEY AUTO_INCREMENT, dname VARCHAR(30), loc VARCHAR(30))");
                // dept table
                stmt.executeUpdate("INSERT INTO dept VALUES(10, 'ACCOUNTING', 'NEW YORK')");
                stmt.executeUpdate("INSERT INTO dept VALUES(20, 'RESEARCH', 'DALLAS')");
                stmt.executeUpdate("INSERT INTO dept VALUES(30, 'SALES', 'CHICAGO')");
                stmt.executeUpdate("INSERT INTO dept VALUES(40, 'OPERATIONS', 'BOSTON')");
                // emp table
                stmt.executeUpdate("INSERT INTO emp (EMPNO,ENAME,JOB,MGR,HIREDATE,SAL,COMM,DEPTNO) VALUES ('7839','KING','PRESIDENT',0,STR_TO_DATE('11,12,1981','%m,%d,%Y'),'5000',0,'10')");
                stmt.executeUpdate("INSERT INTO emp (EMPNO,ENAME,JOB,MGR,HIREDATE,SAL,COMM,DEPTNO) VALUES ('7698','BLAKE','MANAGER','7839',STR_TO_DATE('05,01,1981','%m,%d,%Y'),'2850',0,'30')");
                stmt.executeUpdate("INSERT INTO emp (EMPNO,ENAME,JOB,MGR,HIREDATE,SAL,COMM,DEPTNO) VALUES ('7782','CLARK','MANAGER','7839',STR_TO_DATE('06,09,1981','%m,%d,%Y'),'2450',0,'10')");
                stmt.executeUpdate("INSERT INTO emp (EMPNO,ENAME,JOB,MGR,HIREDATE,SAL,COMM,DEPTNO) VALUES ('7566','JONES','MANAGER','7839',STR_TO_DATE('04,02,1981','%m,%d,%Y'),'2975',0,'20')");
                stmt.executeUpdate("INSERT INTO emp (EMPNO,ENAME,JOB,MGR,HIREDATE,SAL,COMM,DEPTNO) VALUES ('7788','SCOTT','ANALYST','7566',STR_TO_DATE('12,09,1982','%m,%d,%Y'),'3000',0,'20')");
                stmt.executeUpdate("INSERT INTO emp (EMPNO,ENAME,JOB,MGR,HIREDATE,SAL,COMM,DEPTNO) VALUES ('7902','FORD','ANALYST','7566',STR_TO_DATE('12,03,1981','%m,%d,%Y'),'3000',0,'20')");
                stmt.executeUpdate("INSERT INTO emp (EMPNO,ENAME,JOB,MGR,HIREDATE,SAL,COMM,DEPTNO) VALUES ('7369','SMITH','CLERK','7902',STR_TO_DATE('12,17,1980','%m,%d,%Y'),'800',0,'20')");
                stmt.executeUpdate("INSERT INTO emp (EMPNO,ENAME,JOB,MGR,HIREDATE,SAL,COMM,DEPTNO) VALUES ('7499','ALLEN','SALESMAN','7698',STR_TO_DATE('02,20,1981','%m,%d,%Y'),'1600','300','30')");
                stmt.executeUpdate("INSERT INTO emp (EMPNO,ENAME,JOB,MGR,HIREDATE,SAL,COMM,DEPTNO) VALUES ('7521','WARD','SALESMAN','7698',STR_TO_DATE('02,22,1981','%m,%d,%Y'),'1250','500','30')");
                stmt.executeUpdate("INSERT INTO emp (EMPNO,ENAME,JOB,MGR,HIREDATE,SAL,COMM,DEPTNO) VALUES ('7654','MARTIN','SALESMAN','7698',STR_TO_DATE('09,28,1981','%m,%d,%Y'),'1250','1400','30')");
                stmt.executeUpdate("INSERT INTO emp (EMPNO,ENAME,JOB,MGR,HIREDATE,SAL,COMM,DEPTNO) VALUES ('7844','TURNER','SALESMAN','7698',STR_TO_DATE('09,08,1981','%m,%d,%Y'),'1500','0','30')");
                stmt.executeUpdate("INSERT INTO emp (EMPNO,ENAME,JOB,MGR,HIREDATE,SAL,COMM,DEPTNO) VALUES ('7876','ADAMS','CLERK','7788',STR_TO_DATE('01,12,1983','%m,%d,%Y'),'1100',0,'20')");
                stmt.executeUpdate("INSERT INTO emp (EMPNO,ENAME,JOB,MGR,HIREDATE,SAL,COMM,DEPTNO) VALUES ('7900','JAMES','CLERK','7698',STR_TO_DATE('12,03,1981','%m,%d,%Y'),'950',0,'30')");
                stmt.executeUpdate("INSERT INTO emp (EMPNO,ENAME,JOB,MGR,HIREDATE,SAL,COMM,DEPTNO) VALUES ('7934','MILLER','CLERK','7782',STR_TO_DATE('01,23,1982','%m,%d,%Y'),'1300',0,'10')");

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