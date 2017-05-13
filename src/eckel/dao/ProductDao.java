package eckel.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import eckel.model.Product;

public class ProductDao {
	public static List<Product> getProducts(){
		List<Product> products=new ArrayList<Product>();
		Connection con=DBConnection.getConnection();
		String sql="select * from product_tb";
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		try {
			pstmt=con.prepareStatement(sql);
			rs=pstmt.executeQuery();
			while(rs.next()){
				products.add(new Product(rs.getInt(1),rs.getString(2),rs.getString(3),rs.getInt(4),rs.getInt(5),rs.getInt(6)));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			DBConnection.close(pstmt);
			DBConnection.close(con);
		}
		return products;
	}
	public static Product getProductById(int id){
		Product product=null;
		Connection con=DBConnection.getConnection();
		String sql="select * from product_tb where productid=?";
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		try {
			pstmt=con.prepareStatement(sql);
			pstmt.setInt(1,id);
			rs=pstmt.executeQuery();
			while(rs.next()){
				product=new Product(rs.getInt(1),rs.getString(2),rs.getString(3),rs.getInt(4),rs.getInt(5),rs.getInt(6));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			DBConnection.close(pstmt);
			DBConnection.close(con);
		}
		return product;
	}
	public static void modifyProduct(Product product){
		Connection con=DBConnection.getConnection();
		String sql="update product_tb set productname=?,spec=?,leadtime=? where productid=?";
		PreparedStatement pstmt=null;
		try{
			pstmt=con.prepareStatement(sql);
			pstmt.setString(1,product.productname);
			pstmt.setString(2,product.spec);
			pstmt.setInt(3,product.leadtime);
			pstmt.setInt(4,product.productid);
			pstmt.executeUpdate();
		}catch(SQLException e){
			e.printStackTrace();
		}finally{
			DBConnection.close(pstmt);
			DBConnection.close(con);
		}
	}
	public static void modifyStock(Product product){
		Connection con=DBConnection.getConnection();
		String sql="update product_tb set stock=?,safestock=? where productid=?";
		PreparedStatement pstmt=null;
		try{
			pstmt=con.prepareStatement(sql);
			pstmt.setInt(1,product.stock);
			pstmt.setInt(2,product.safestock);
			pstmt.setInt(3,product.productid);
			pstmt.executeUpdate();
		}catch(SQLException e){
			e.printStackTrace();
		}finally{
			DBConnection.close(pstmt);
			DBConnection.close(con);
		}
	}
	public static void addProduct(Product product){
		Connection con=DBConnection.getConnection();
		String sql="insert into product_tb values(?,?,?,?,0,0)";
		PreparedStatement pstmt=null;
		try{
			pstmt=con.prepareStatement(sql);
			pstmt.setInt(1,product.productid);
			pstmt.setString(2,product.productname);
			pstmt.setString(3,product.spec);
			pstmt.setInt(4,product.leadtime);
			pstmt.executeUpdate();
		}catch(SQLException e){
			e.printStackTrace();
		}finally{
			DBConnection.close(pstmt);
			DBConnection.close(con);
		}
	}
	public static void deleteProductById(int productid){
		Connection con=DBConnection.getConnection();
		String sql="delete from product_tb where productid=?";
		PreparedStatement pstmt=null;
		try{
			pstmt=con.prepareStatement(sql);
			pstmt.setInt(1,productid);
			pstmt.executeUpdate();
		}catch(SQLException e){
			e.printStackTrace();
		}finally{
			DBConnection.close(pstmt);
			DBConnection.close(con);
		}
	}

}
