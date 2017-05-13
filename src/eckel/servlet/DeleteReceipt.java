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
 * Servlet implementation class DeleteReceipt
 */
@WebServlet("/DeleteReceipt")
public class DeleteReceipt extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DeleteReceipt() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		int receiptid=Integer.parseInt(request.getParameter("receiptid"));
		Receipt receipt=ReceiptDao.getReceiptById(receiptid);
		int change=0-receipt.quantity;
		ReceiptDao.deleteReceiptById(receiptid);
		System.out.println(change);System.out.println(receipt.quantity);
		ReceiptDao.updateStock(change,receipt.productid);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
