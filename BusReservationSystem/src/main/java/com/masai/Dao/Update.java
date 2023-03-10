package com.masai.Dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Update
 */
@WebServlet("/update")
public class Update extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		 String uname = request.getParameter("name");
	     String uemail = request.getParameter("email");
	     String upass = request.getParameter("pass");
	     String umobile = request.getParameter("contact");
	     RequestDispatcher dispatcher = null;
	     
	     if(uname!="" && uemail!="" && upass!="" && umobile!="") {
			     Connection conn = null;
			    try {
//			    	Class.forName("com.mysql.cj.jdbc.Driver");
			        conn = DbUtils.connectToDatabase();
			    	PreparedStatement ps = conn.prepareStatement(" update users set username=?,password=?,email=?,mobileno=? where username=?");
			    	ps.setString(1, uname);
			    	ps.setString(2, upass);
			    	ps.setString(3, uemail);
			    	ps.setString(4, umobile);
			    	ps.setString(5, uname);
			    	
			    	int out = ps.executeUpdate();
			    	dispatcher = request.getRequestDispatcher("UpdateProfile.jsp");
			    	if(out>0) {
			    		request.setAttribute("status6", "Success");
			    		 dispatcher = request.getRequestDispatcher("UpdateProfile.jsp");
		    			 dispatcher.forward(request, response);
			    	}else {
			    		request.setAttribute("status6", "incorrect");
			    		 dispatcher = request.getRequestDispatcher("UpdateProfile.jsp");
		    			 dispatcher.forward(request, response);
			    	}
			    	dispatcher.forward(request, response);
			    }catch(Exception ex) {
			    	ex.printStackTrace();
			    }finally {
			    	try {
						DbUtils.closeConnection(conn);
					} catch (SQLException e) {
						e.printStackTrace();
					}
			    }
	     }
	     else {
	    	 dispatcher = request.getRequestDispatcher("UpdateProfile.jsp");
	    	 request.setAttribute("status6", "failed");
	    	 dispatcher.forward(request, response);
	     }
	}

}
