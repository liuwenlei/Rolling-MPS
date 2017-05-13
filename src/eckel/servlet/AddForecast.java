package eckel.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import eckel.dao.ForecastDao;

import eckel.model.Forecast;
import eckel.model.MPS;

/**
 * Servlet implementation class AddForecast
 */
@WebServlet("/AddForecast")
public class AddForecast extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddForecast() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		int forecastid=0;
		int productid=Integer.parseInt(request.getParameter("productid"));
		int customerid=Integer.parseInt(request.getParameter("customerid"));
		int forecastquantity=Integer.parseInt(request.getParameter("forecastquantity"));
		String deliverydate=request.getParameter("deliverydate");
		Forecast fc=new Forecast(forecastid,productid,customerid,forecastquantity,deliverydate);
		ForecastDao.addForecast(fc);
		MPS.update(productid);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
