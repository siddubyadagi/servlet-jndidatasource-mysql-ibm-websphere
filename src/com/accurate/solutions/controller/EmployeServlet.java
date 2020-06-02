package com.accurate.solutions.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

/**
 * Servlet implementation class EmployeServlet
 */
@WebServlet("/EmployeServlet")
public class EmployeServlet extends HttpServlet implements Servlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
//		response.getWriter().append("Served at: ").append(request.getContextPath());
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Connection conn = null;
		PrintWriter out = response.getWriter();
		try {
			// JNDI
			System.out.println("==============================================JNDI====================================");
			Context aContext = new InitialContext();
			DataSource aDataSource = (DataSource) aContext.lookup("java:comp/env/jdbc/accurate");
			conn = aDataSource.getConnection();
			System.out.println("============================================conn======================================"+conn);
			// JNDI end

			// raw JDBC for the same SAMPLEsample DB2 database
			// Class.forName("COM.ibm.db2.jdbc.app.DB2Driver"); //need db2java.zip in the
			// path
			// conn = DriverManager.getConnection("jdbc:db2:sample"); //DB2
			// raw JDBC end

			String firstname = "";
			String lastname = "";
			String sql = "select fiestname, lastname from employee";
			Statement stm = conn.createStatement();
			ResultSet rst = stm.executeQuery(sql);
			out.println("===================================");
			out.println("Firstname " + " Lastname");
			out.println("===================================");
			for (int i = 0; (i < 10 && rst.next()); i++) // only print 10 names
			{
				firstname = rst.getString("fiestname");
				lastname = rst.getString("lastname");			
				out.println(firstname + "   ,    "+lastname);
			}
			out.println("===================================");
			if (conn != null) {
				conn.close();
				System.out.println("Successfully closed");
			}
		} catch (Exception e) {
			System.out.println(e);
		}

	}

}
