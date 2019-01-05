package com.test;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

@WebServlet(name = "TestServlet", urlPatterns = {"/TestServlet"})
public class TestServlet extends HttpServlet {

    @Resource(lookup = "java:jboss/datasources/test_mysql")
    private DataSource dataSource;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            Connection connection = dataSource.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT ename, job FROM emp ORDER BY ename");
            ResultSet resultSet = preparedStatement.executeQuery();

            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>DB Servlet</title>");
            out.println("</head>");
            out.println("<body>");

            if (resultSet.next() == false) {
                out.println("<p>NO DATA - NOW INITIALIZING - PLEASE RELOAD THE PAGE</p><br/>");
                initializeDb();
            } else {

                while (resultSet.next()) {
                    out.println("<p>" + resultSet.getString(1) + "</p><p>" + resultSet.getString(2) + "</p><br/>");
                }
            }

            resultSet.close();
            preparedStatement.close();
            connection.close();

            out.println("</body>");
            out.println("</html>");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            out.close();
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }
    
    private void initializeDb(){
        try{
        Connection connection = dataSource.getConnection();
        Statement stmt = connection.createStatement();
        stmt.executeQuery("DROP TABLE IF EXISTS emp");
        stmt.executeQuery("DROP TABLE IF EXISTS dept");
        stmt.executeQuery("CREATE TABLE emp(empno INT NOT NULL PRIMARY KEY AUTO_INCREMENT, ename VARCHAR(30), job VARCHAR(30), mgr INT, hiredate DATE, sal INT, comm INT, deptno INT)");
        stmt.executeQuery("CREATE TABLE dept(deptno INT NOT NULL PRIMARY KEY AUTO_INCREMENT, dname VARCHAR(30), loc VARCHAR(30))");
        // dept table
        stmt.executeQuery("INSERT INTO dept VALUES(10, 'ACCOUNTING', 'NEW YORK')");
        stmt.executeQuery("INSERT INTO dept VALUES(20, 'RESEARCH', 'DALLAS')");
        stmt.executeQuery("INSERT INTO dept VALUES(30, 'SALES', 'CHICAGO')");
        stmt.executeQuery("INSERT INTO dept VALUES(40, 'OPERATIONS', 'BOSTON')");
        // emp table
        stmt.executeQuery("INSERT INTO emp (EMPNO,ENAME,JOB,MGR,HIREDATE,SAL,COMM,DEPTNO) VALUES ('7839','KING','PRESIDENT',0,STR_TO_DATE('11,12,1981','%m,%d,%Y'),'5000',0,'10')");
        stmt.executeQuery("INSERT INTO emp (EMPNO,ENAME,JOB,MGR,HIREDATE,SAL,COMM,DEPTNO) VALUES ('7698','BLAKE','MANAGER','7839',STR_TO_DATE('05,01,1981','%m,%d,%Y'),'2850',0,'30')");
        stmt.executeQuery("INSERT INTO emp (EMPNO,ENAME,JOB,MGR,HIREDATE,SAL,COMM,DEPTNO) VALUES ('7782','CLARK','MANAGER','7839',STR_TO_DATE('06,09,1981','%m,%d,%Y'),'2450',0,'10')");
        stmt.executeQuery("INSERT INTO emp (EMPNO,ENAME,JOB,MGR,HIREDATE,SAL,COMM,DEPTNO) VALUES ('7566','JONES','MANAGER','7839',STR_TO_DATE('04,02,1981','%m,%d,%Y'),'2975',0,'20')");
        stmt.executeQuery("INSERT INTO emp (EMPNO,ENAME,JOB,MGR,HIREDATE,SAL,COMM,DEPTNO) VALUES ('7788','SCOTT','ANALYST','7566',STR_TO_DATE('12,09,1982','%m,%d,%Y'),'3000',0,'20')");
        stmt.executeQuery("INSERT INTO emp (EMPNO,ENAME,JOB,MGR,HIREDATE,SAL,COMM,DEPTNO) VALUES ('7902','FORD','ANALYST','7566',STR_TO_DATE('12,03,1981','%m,%d,%Y'),'3000',0,'20')");
        stmt.executeQuery("INSERT INTO emp (EMPNO,ENAME,JOB,MGR,HIREDATE,SAL,COMM,DEPTNO) VALUES ('7369','SMITH','CLERK','7902',STR_TO_DATE('12,17,1980','%m,%d,%Y'),'800',0,'20')");
        stmt.executeQuery("INSERT INTO emp (EMPNO,ENAME,JOB,MGR,HIREDATE,SAL,COMM,DEPTNO) VALUES ('7499','ALLEN','SALESMAN','7698',STR_TO_DATE('02,20,1981','%m,%d,%Y'),'1600','300','30')");
        stmt.executeQuery("INSERT INTO emp (EMPNO,ENAME,JOB,MGR,HIREDATE,SAL,COMM,DEPTNO) VALUES ('7521','WARD','SALESMAN','7698',STR_TO_DATE('02,22,1981','%m,%d,%Y'),'1250','500','30')");
        stmt.executeQuery("INSERT INTO emp (EMPNO,ENAME,JOB,MGR,HIREDATE,SAL,COMM,DEPTNO) VALUES ('7654','MARTIN','SALESMAN','7698',STR_TO_DATE('09,28,1981','%m,%d,%Y'),'1250','1400','30')");
        stmt.executeQuery("INSERT INTO emp (EMPNO,ENAME,JOB,MGR,HIREDATE,SAL,COMM,DEPTNO) VALUES ('7844','TURNER','SALESMAN','7698',STR_TO_DATE('09,08,1981','%m,%d,%Y'),'1500','0','30')");
        stmt.executeQuery("INSERT INTO emp (EMPNO,ENAME,JOB,MGR,HIREDATE,SAL,COMM,DEPTNO) VALUES ('7876','ADAMS','CLERK','7788',STR_TO_DATE('01,12,1983','%m,%d,%Y'),'1100',0,'20')");
        stmt.executeQuery("INSERT INTO emp (EMPNO,ENAME,JOB,MGR,HIREDATE,SAL,COMM,DEPTNO) VALUES ('7900','JAMES','CLERK','7698',STR_TO_DATE('12,03,1981','%m,%d,%Y'),'950',0,'30')");
        stmt.executeQuery("INSERT INTO emp (EMPNO,ENAME,JOB,MGR,HIREDATE,SAL,COMM,DEPTNO) VALUES ('7934','MILLER','CLERK','7782',STR_TO_DATE('01,23,1982','%m,%d,%Y'),'1300',0,'10')");
        
        connection.commit();
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        
    }

}
