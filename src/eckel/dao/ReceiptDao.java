package eckel.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import eckel.model.Receipt;

public class ReceiptDao {
	public static List<Receipt> getReceipts(){
		List<Receipt> receipts=new ArrayList<Receipt>();
		Connection con=DBConnection.getConnection();
		String sql="select * from receipt_tb";
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		try {
			pstmt=con.prepareStatement(sql);
			rs=pstmt.executeQuery();
			while(rs.next()){
				receipts.add(new Receipt(rs.getInt(1),rs.getInt(2),rs.getInt(3),rs.getString(4),rs.getString(5)));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			DBConnection.close(pstmt);
			DBConnection.close(con);
		}
		return receipts;
	}
	public static Receipt getReceiptById(int id){
		Receipt receipt=new Receipt();
		Connection con=DBConnection.getConnection();
		String sql="select * from receipt_tb where receiptid=?";
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		try {
			pstmt=con.prepareStatement(sql);
			pstmt.setInt(1,id);
			rs=pstmt.executeQuery();
			while(rs.next()){
				receipt=new Receipt(rs.getInt(1),rs.getInt(2),rs.getInt(3),rs.getString(4),rs.getString(5));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			DBConnection.close(pstmt);
			DBConnection.close(con);
		}
		return receipt;
	}
	public static void modifyReceipt(Receipt receipt){
		Connection con=DBConnection.getConnection();
		String sql="update receipt_tb set productid=?,quantity=?,from=?,date=? where receiptid=?";
		PreparedStatement pstmt=null;
		try{
			pstmt=con.prepareStatement(sql);
			pstmt.setInt(1,receipt.productid);
			pstmt.setInt(2,receipt.quantity);
			pstmt.setString(3,receipt.from);
			pstmt.setString(4,receipt.date);
			pstmt.setInt(5,receipt.receiptid);
			pstmt.executeUpdate();
		}catch(SQLException e){
			e.printStackTrace();
		}finally{
			DBConnection.close(pstmt);
			DBConnection.close(con);
		}
	}
	
	public static void addReceipt(Receipt receipt){
		Connection con=DBConnection.getConnection();
		String sql="insert into receipt_tb values(?,?,?,?,?)";
		PreparedStatement pstmt=null;
		try{
			pstmt=con.prepareStatement(sql);
			pstmt.setInt(1,receipt.receiptid);
			pstmt.setInt(2,receipt.productid);
			pstmt.setInt(3,receipt.quantity);
			pstmt.setString(4,receipt.from);
			pstmt.setString(5,receipt.date);
			pstmt.executeUpdate();
		}catch(SQLException e){
			e.printStackTrace();
		}finally{
			DBConnection.close(pstmt);
			DBConnection.close(con);
		}
	}
	public static void deleteReceiptById(int receiptid){
		Connection con=DBConnection.getConnection();
		String sql="delete from receipt_tb where receiptid=?";
		PreparedStatement pstmt=null;
		try{
			pstmt=con.prepareStatement(sql);
			pstmt.setInt(1,receiptid);
			pstmt.executeUpdate();
		}catch(SQLException e){
			e.printStackTrace();
		}finally{
			DBConnection.close(pstmt);
			DBConnection.close(con);
		}
	}
	public static void updateStock(int change,int productid){
		Connection con=DBConnection.getConnection();
		String sql="update product_tb set stock=stock+? where productid=?";
		PreparedStatement pstmt=null;
		try{
			pstmt=con.prepareStatement(sql);
			pstmt.setInt(1,change);
			pstmt.setInt(2,productid);
			pstmt.executeUpdate();
		}catch(SQLException e){
			e.printStackTrace();
		}finally{
			DBConnection.close(pstmt);
			DBConnection.close(con);
		}
	}

}
