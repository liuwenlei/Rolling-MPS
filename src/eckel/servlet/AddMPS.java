package eckel.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import eckel.dao.MPSDao;
import eckel.model.MPS;

/**
 * Servlet implementation class AddMPS
 */
@WebServlet("/AddMPS")
public class AddMPS extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddMPS() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		int productid=Integer.parseInt(request.getParameter("productid"));
		int ph=Integer.parseInt(request.getParameter("ph"));
		int lotsize=Integer.parseInt(request.getParameter("lotsize"));
		int rf=Integer.parseInt(request.getParameter("rf"));
		int sf=Integer.parseInt(request.getParameter("sf"));
		MPS mps=new MPS(productid,ph,lotsize,rf,sf);
		String key=MPSDao.addMPS(mps);
		MPS.update(productid);
		response.getWriter().write(key);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
