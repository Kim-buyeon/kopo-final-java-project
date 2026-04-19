package com.finaljavaproject.www.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.finaljavaproject.www.domain.Orders;
import com.finaljavaproject.www.util.JdbcUtil;

public class OrderRepository implements Repository<Orders, String> {

    @Override
    public void save(Orders item) {
	    String sql = "MERGE INTO  orderitem o " +
		                     "USING DUAL ON (o.order_id = ?) " +
		                     "WHEN MATCHED THEN " +
		                     "  UPDATE SET member_id = ?, product_id = ?, quantity = ? " +
		                     "WHEN NOT MATCHED THEN " +
		                     "  INSERT (order_id, member_id, product_id, quantity) VALUES (?, ?, ?, ?)";
	 try(Connection conn = JdbcUtil.getConnection()) {
		 conn.setAutoCommit(false);
		try(PreparedStatement pstmt = conn.prepareStatement(sql);) {
			pstmt.setString(1, item.getId());
			pstmt.setString(2, item.getMemberId());
			pstmt.setString(3, item.getProductId());
			pstmt.setInt(4, item.getQuantity());
			
			pstmt.setString(5, item.getId());
			pstmt.setString(6, item.getMemberId());
			pstmt.setString(7, item.getProductId());
			pstmt.setInt(8, item.getQuantity());
			pstmt.executeUpdate();
			conn.commit();
			System.out.println("정상적으로 저장/수정되었습니다.");
		}catch (SQLException e) {
			System.err.println("쿼리 실행도중 오류 발생" + e.getMessage());
			conn.rollback();
		}finally {
			conn.setAutoCommit(true);
		}
		
	} catch (Exception e) {
		System.err.println("생성 or 수정 실패 : " + e.getMessage());
		e.printStackTrace();
	}
    }

    @Override
    public List<Orders> findAll() {
        List<Orders> orders = new ArrayList<>();
        String sql = "SELECT * FROM orderitem";
        try (Connection conn = JdbcUtil.getConnection()){
	       Statement stmt = conn.createStatement();
	       ResultSet rs = stmt.executeQuery(sql);
	       while(rs.next()) {
		       Orders order = new Orders(
				       rs.getString("member_id"),
				       rs.getString("product_id"),
				       rs.getInt("quantity"));
		orders.add(order);
	       }
	} catch (Exception e) {
		System.err.println("읽어오기 실패" + e.getMessage());
		e.printStackTrace();
	}
        return orders;
    }

    @Override
    public Orders findById(String s) {
        return null;
    }

    @Override
    public void deleteById(String s) {

    }
}
