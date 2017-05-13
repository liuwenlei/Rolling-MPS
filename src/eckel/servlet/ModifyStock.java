package eckel.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import eckel.dao.ProductDao;
import eckel.model.MPS;
import eckel.model.Product;

/**
 * Servlet implementation class ModifyStock
 */
@WebServlet("/ModifyStock")
public class ModifyStock extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ModifyStock() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		int productid=Integer.parseInt(request.getParameter("productid"));
		int stock=Integer.parseInt(request.getParameter("stock"));
		int safestock=Integer.parseInt(request.getParameter("safestock"));
		Product product=new Product(productid,"","",0,stock,safestock);
		ProductDao.modifyStock(product);
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
