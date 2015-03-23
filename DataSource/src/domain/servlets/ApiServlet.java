package domain.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import domain.datasources.TotalFotballGoals;


@WebServlet("/ApiServlet")
public class ApiServlet extends HttpServlet 
{
	private static final long serialVersionUID = 1L;


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		response.setContentType("application/json");
		
		
		TotalFotballGoals goals = new TotalFotballGoals();
		
		
		
		response.getWriter().print(new Gson().toJson(goals.getData()));
		
		System.out.println("Done");
	}

}