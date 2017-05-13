package eckel.dao;

import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import eckel.model.Customer;

public class CustomerDao {
	public static List<Customer> getCustomers(){
		List<Customer> customers=new ArrayList<Customer>();
		Connection con=DBConnection.getConnection();
		String sql="select * from customer_tb";
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		try {
			pstmt=con.prepareStatement(sql);
			rs=pstmt.executeQuery();
			while(rs.next()){
				customers.add(new Customer(rs.getInt(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5),rs.getString(6)));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			DBConnection.close(pstmt);
			DBConnection.close(con);
		}
		return customers;
	}
	public static void modifyCustomer(Customer customer){
		Connection con=DBConnection.getConnection();
		String sql="update customer_tb set customername=?,contacts=?,tel=?,email=?,address=? where customerid=?";
		PreparedStatement pstmt=null;
		try{
			pstmt=con.prepareStatement(sql);
			pstmt.setString(1,customer.customername);
			pstmt.setString(2,customer.contacts);
			pstmt.setString(3,customer.tel);
			pstmt.setString(4,customer.email);
			pstmt.setString(5,customer.address);
			pstmt.setInt(6,customer.customerid);
			pstmt.executeUpdate();
		}catch(SQLException e){
			e.printStackTrace();
		}finally{
			DBConnection.close(pstmt);
			DBConnection.close(con);
		}
	}
	
	public static void addCustomer(Customer customer){
		Connection con=DBConnection.getConnection();
		String sql="insert into customer_tb values(?,?,?,?,?,?)";
		PreparedStatement pstmt=null;
		try{
			pstmt=con.prepareStatement(sql);
			pstmt.setInt(1,customer.customerid);
			pstmt.setString(2,customer.customername);
			pstmt.setString(3,customer.contacts);
			pstmt.setString(4,customer.tel);
			pstmt.setString(5,customer.email);
			pstmt.setString(6,customer.address);
			pstmt.executeUpdate();
		}catch(SQLException e){
			e.printStackTrace();
		}finally{
			DBConnection.close(pstmt);
			DBConnection.close(con);
		}
	}
	public static void deleteCustomerById(int customerid){
		Connection con=DBConnection.getConnection();
		String sql="delete from customer_tb where customerid=?";
		PreparedStatement pstmt=null;
		try{
			pstmt=con.prepareStatement(sql);
			pstmt.setInt(1,customerid);
			pstmt.executeUpdate();
		}catch(SQLException e){
			e.printStackTrace();
		}finally{
			DBConnection.close(pstmt);
			DBConnection.close(con);
		}
	}
}
