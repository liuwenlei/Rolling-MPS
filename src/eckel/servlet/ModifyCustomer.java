package eckel.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import eckel.dao.CustomerDao;
import eckel.model.Customer;

/**
 * Servlet implementation class ModifyCustomer
 */
@WebServlet("/ModifyCustomer")
public class ModifyCustomer extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ModifyCustomer() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		int customerid=Integer.parseInt(request.getParameter("customerid"));
		String customername=request.getParameter("customername");
		String contacts=request.getParameter("contacts");
		String tel=request.getParameter("tel");
		String email=request.getParameter("email");
		String address=request.getParameter("address");
		Customer customer=new Customer(customerid,customername,contacts,tel,email,address);
		CustomerDao.modifyCustomer(customer);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
