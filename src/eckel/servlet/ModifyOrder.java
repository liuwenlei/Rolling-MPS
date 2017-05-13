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
 * Servlet implementation class ModifyOrder
 */
@WebServlet("/ModifyOrder")
public class ModifyOrder extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ModifyOrder() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		int orderid=Integer.parseInt(request.getParameter("orderid"));
		int productid=Integer.parseInt(request.getParameter("productid"));
		int customerid=Integer.parseInt(request.getParameter("customerid"));
		int orderquantity=Integer.parseInt(request.getParameter("orderquantity"));
		String deliverydate=request.getParameter("deliverydate");
		Order order=new Order(orderid,productid,customerid,orderquantity,deliverydate);
		OrderDao.modifyOrder(order);
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
