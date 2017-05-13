package eckel.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import eckel.dao.ReceiptDao;
import eckel.model.Receipt;

/**
 * Servlet implementation class ModifyReceipt
 */
@WebServlet("/ModifyReceipt")
public class ModifyReceipt extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ModifyReceipt() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		int receiptid=Integer.parseInt(request.getParameter("receiptid"));
		int productid=Integer.parseInt(request.getParameter("productid"));
		int quantity=Integer.parseInt(request.getParameter("quantity"));
		String from=request.getParameter("from");
		String date=request.getParameter("date");
		Receipt newreceipt=new Receipt(receiptid,productid,quantity,from,date);
		Receipt oldreceipt=ReceiptDao.getReceiptById(receiptid);
		int change=quantity-oldreceipt.quantity;
		ReceiptDao.modifyReceipt(newreceipt);
		ReceiptDao.updateStock(change,productid);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
