package eckel.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import eckel.dao.OrderDao;
import eckel.model.MPS;
import eckel.model.Order;

/**
 * Servlet implementation class AddOrder
 */
@WebServlet("/AddOrder")
public class AddOrder extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddOrder() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		int orderid=0;
		int productid=Integer.parseInt(request.getParameter("productid"));
		int customerid=Integer.parseInt(request.getParameter("customerid"));
		int orderquantity=Integer.parseInt(request.getParameter("orderquantity"));
		String deliverydate=request.getParameter("deliverydate");
		Order odr=new Order(orderid,productid,customerid,orderquantity,deliverydate);
		OrderDao.addOrder(odr);
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
