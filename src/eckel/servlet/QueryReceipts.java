package eckel.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import eckel.dao.ReceiptDao;
import eckel.dao.UserDao;
import eckel.model.Receipt;
import eckel.model.User;

/**
 * Servlet implementation class QueryReceipts
 */
@WebServlet("/QueryReceipts")
public class QueryReceipts extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public QueryReceipts() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType("text/html;charset=utf-8");
		List<Receipt> receipts=ReceiptDao.getReceipts();
		String text=stringifyList(receipts);
		response.getWriter().write(text);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
	public String stringifyList(List<Receipt> receipts){
		String text="[";
		int i=1;
		for(Receipt r:receipts){
			if(i!=1){
				text+=",";
			}
			text+=r.getJson();
			i++;
		}
		text+="]";
		return text;
	}


}
