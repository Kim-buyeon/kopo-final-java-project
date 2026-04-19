package com.finaljavaproject.www.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.finaljavaproject.www.domain.Product;
import com.finaljavaproject.www.domain.constant.ProductStatus;
import com.finaljavaproject.www.util.JdbcUtil;

public class ProductRepository implements Repository<Product, String> {
	
	@Override
	public void save(Product item) {
		String sql = "MERGE INTO products p " + 
				      "USING DUAL ON (p.product_id = ?) " +
				      "WHEN MATCHED THEN " + 
				      " UPDATE SET stock = ?, price = ?, product_status = ? " +
				      "WHEN NOT MATCHED THEN " + 
				      " INSERT (product_id, name, description, price, stock, product_status) " +
				      " VALUES (?, ?, ?, ?, ?, ?)";

		try (Connection conn = JdbcUtil.getConnection();) { 
			conn.setAutoCommit(false);
			try(PreparedStatement pstmt = conn.prepareStatement(sql);) {
				
				pstmt.setString(1, item.getProductId());
				pstmt.setInt(2, item.getStock());
				pstmt.setInt(3, item.getPrice());
				pstmt.setString(4, item.getProductStatus().name());
				pstmt.setString(5, item.getProductId());
				pstmt.setString(6, item.getName());
				pstmt.setString(7, item.getDescription());
				pstmt.setInt(8, item.getPrice());
				pstmt.setInt(9, item.getStock());
				pstmt.setString(10, item.getProductStatus().name());
				pstmt.executeUpdate();
				conn.commit();
				System.out.println("정상적으로 저장/수정되었습니다.");
			} catch (SQLException e) {
				System.err.println("쿼리 발생 중 오류 발생 롤백 진행" + e.getMessage());
				conn.rollback();
			}finally {
				conn.setAutoCommit(true);
			}
			
		} catch (Exception e) {
			System.err.println("저장 실패: " + e.getMessage());
			e.printStackTrace();
		}		
	}

	@Override
	public List<Product> findAll() {
		List<Product> products = new ArrayList<>();
		String sql = "SELECT * FROM products";
		
		try (Connection conn = JdbcUtil.getConnection();
		     Statement stmt = conn.createStatement();
		     ResultSet rs = stmt.executeQuery(sql)) {
			
			while (rs.next()) {
				ProductStatus status = ProductStatus.valueOf(rs.getString("product_status"));
				products.add(new Product(
						rs.getString("product_id"),
						rs.getString("name"),
						rs.getString("description"),
						rs.getInt("price"), 
						rs.getInt("stock"),
						status));
			}
		} catch (Exception e) {
			System.err.println("전체 조회 실패: " + e.getMessage());
		}
		return products;
	}

	@Override
	public Product findById(String id) {
		String sql = "SELECT * FROM products WHERE product_id = ?";
		try (Connection conn = JdbcUtil.getConnection();
		     PreparedStatement pstmt = conn.prepareStatement(sql)) {
			
			pstmt.setString(1, id);
			try (ResultSet rs = pstmt.executeQuery()) {
				if (rs.next()) {
					return new Product(
							rs.getString("product_id"),
							rs.getString("name"),
							rs.getString("description"),
							rs.getInt("price"),
							rs.getInt("stock"),
							ProductStatus.valueOf(rs.getString("product_status")));
				}
			}
		} catch (Exception e) {
			System.err.println("단건 조회 실패: " + e.getMessage());
		}
		return null;
	}

	@Override
	public void deleteById(String id) {
		String sql = "DELETE FROM products WHERE product_id = ?"; 
		try (Connection conn = JdbcUtil.getConnection();) {
			conn.setAutoCommit(false);
			try(PreparedStatement pstmt = conn.prepareStatement(sql)) {
				pstmt.setString(1, id);
				int result = pstmt.executeUpdate();
				if(result > 0) {
					conn.commit();
					System.out.println("정상적으로 삭제가 이루어졌숩니다.");
				}else {
					System.out.println("삭제할 상품이 존재하지 않습니다." + id);
				}
			
			} catch (SQLException e) {
				conn.rollback();
				System.err.println("쿼리 실행 도중 오류 발생하여 롤백 진행" + e.getMessage());
			}finally {
				conn.setAutoCommit(true);
			}
			
		} catch (Exception e) {
			System.err.println("삭제 실패: " + e.getMessage());
		}
	}
}