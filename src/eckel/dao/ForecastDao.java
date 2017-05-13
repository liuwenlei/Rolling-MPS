package eckel.dao;

import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import eckel.model.Forecast;


public class ForecastDao {
	public static void addForecast(Forecast fc){
		Connection con=DBConnection.getConnection();
		String sql="insert into forecast_tb values(?,?,?,?,?)";
		PreparedStatement pstmt=null;
		try {
			pstmt=con.prepareStatement(sql);
			pstmt.setInt(1,fc.forecastid);
			pstmt.setInt(2,fc.productid);
			pstmt.setInt(3,fc.customerid);
			pstmt.setInt(4,fc.forecastquantity);
			pstmt.setString(5,fc.deliverydate);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			DBConnection.close(pstmt);
			DBConnection.close(con);
		}
	}
	public static void modifyForecast(Forecast forecast){
		Connection con=DBConnection.getConnection();
		String sql="update forecast_tb set productid=?,customerid=?,forecastquantity=?,deliverydate=? where forecastid=?";
		PreparedStatement pstmt=null;
		try {
			pstmt=con.prepareStatement(sql);
			pstmt.setInt(1,forecast.productid);
			pstmt.setInt(2,forecast.customerid);
			pstmt.setInt(3,forecast.forecastquantity);
			pstmt.setString(4,forecast.deliverydate);
			pstmt.setInt(5,forecast.forecastid);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			DBConnection.close(pstmt);
			DBConnection.close(con);
		}
	}
	
	public static List<Forecast> getForecasts(){
		List<Forecast> forecasts=new ArrayList<Forecast>();
		Connection con=DBConnection.getConnection();
		String sql="select * from forecast_tb";
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		try {
			pstmt=con.prepareStatement(sql);
			rs=pstmt.executeQuery();
			while(rs.next()){
				forecasts.add(new Forecast(rs.getInt(1),rs.getInt(2),rs.getInt(3),rs.getInt(4),rs.getString(5)));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			DBConnection.close(pstmt);
			DBConnection.close(con);
		}
		return forecasts;
	}
	public static int getSumByDate(int productid,String day){
		int sum=0;
		Connection con=DBConnection.getConnection();
		String sql="select sum(forecastquantity) from forecast_tb where productid=? and deliverydate=?";
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		try {
			pstmt=con.prepareStatement(sql);
			pstmt.setInt(1,productid);
			pstmt.setString(2,day);
			rs=pstmt.executeQuery();
			while(rs.next()){
				sum=rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			DBConnection.close(pstmt);
			DBConnection.close(con);
		}
		return sum;
	}
	
	public static void deleteForecastById(int forecastid){
		Connection con=DBConnection.getConnection();
		String sql="delete from forecast_tb where forecastid=?";
		PreparedStatement pstmt=null;
		try{
			pstmt=con.prepareStatement(sql);
			pstmt.setInt(1,forecastid);
			pstmt.executeUpdate();
		}catch(SQLException e){
			e.printStackTrace();
		}finally{
			DBConnection.close(pstmt);
			DBConnection.close(con);
		}
	}
	public static Forecast getForecastById(int id){
		Forecast forecast=new Forecast();
		Connection con=DBConnection.getConnection();
		String sql="select * from forecast_tb where forecastid=?";
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		try {
			pstmt=con.prepareStatement(sql);
			pstmt.setInt(1,id);
			rs=pstmt.executeQuery();
			while(rs.next()){
				forecast=new Forecast(rs.getInt(1),rs.getInt(2),rs.getInt(3),rs.getInt(4),rs.getString(5));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			DBConnection.close(pstmt);
			DBConnection.close(con);
		}
		return forecast;
	}
}
