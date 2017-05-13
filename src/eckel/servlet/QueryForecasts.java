package eckel.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import eckel.dao.ForecastDao;
import eckel.model.Forecast;


/**
 * Servlet implementation class QueryForecasts
 */
@WebServlet("/QueryForecasts")
public class QueryForecasts extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public QueryForecasts() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType("text/html;charset=utf-8");
		List<Forecast> forecasts=ForecastDao.getForecasts();
		String text=stringifyList(forecasts);
		response.getWriter().write(text);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
	public String stringifyList(List<Forecast> forecasts){
		String text="[";
		int i=1;
		for(Forecast f:forecasts){
			if(i!=1){
				text+=",";
			}
			text+=f.getJson();
			i++;
		}
		text+="]";
		return text;
	}

}
