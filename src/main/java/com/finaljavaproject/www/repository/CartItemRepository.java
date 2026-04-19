package com.finaljavaproject.www.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.finaljavaproject.www.domain.CartItem;
import com.finaljavaproject.www.util.JdbcUtil;

public class CartItemRepository implements Repository<CartItem, String> {

    @Override
    public void save(CartItem item) {
        String sql = "MERGE INTO cartitem c " +
                     "USING DUAL ON (c.cart_id = ?) " +
                     "WHEN MATCHED THEN " +
                     "  UPDATE SET quantity = ? " +
                     "WHEN NOT MATCHED THEN " +
                     "  INSERT (cart_id, member_id, product_id, quantity) " +
                     "  VALUES (?, ?, ?, ?)";

        try (Connection conn = JdbcUtil.getConnection();) {
	        conn.setAutoCommit(false);
	        try(PreparedStatement pstmt = conn.prepareStatement(sql)) {
		        	  pstmt.setString(1, item.getId());
		            pstmt.setInt(2, item.getQuantity());
		            pstmt.setString(3, item.getId());
		            pstmt.setString(4, item.getMemberId());
		            pstmt.setString(5, item.getProductId());
		            pstmt.setInt(6, item.getQuantity());
		            pstmt.executeUpdate();
		            conn.commit();
		            System.out.println("정상적으로 저장/수정되었습니다.");
	        } catch (SQLException e) {
		        System.err.println("쿼리 발생 중 오류 발생 롤백 진행" + e.getMessage());
			conn.rollback();
	        }finally {
			conn.setAutoCommit(true);
		}
            
            System.out.println("장바구니 업데이트 완료: " + item.getId());
        } catch (Exception e) {
            System.err.println("장바구니 저장 실패: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public List<CartItem> findAll() {
        List<CartItem> cartItems = new ArrayList<>();
        String sql = "SELECT * FROM cartitem";

        try (Connection conn = JdbcUtil.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                CartItem item = new CartItem();
                item.setId(rs.getString("cart_id"));
                item.setMemberId(rs.getString("member_id"));
                item.setProductId(rs.getString("product_id"));
                item.setQuantity(rs.getInt("quantity"));
                cartItems.add(item);
            }
        } catch (Exception e) {
            System.err.println("장바구니 전체 조회 실패: " + e.getMessage());
        }
        return cartItems;
    }

    @Override
    public CartItem findById(String id) {
        String sql = "SELECT * FROM cartitem WHERE cart_id = ?";
        try (Connection conn = JdbcUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    CartItem item = new CartItem();
                    item.setId(rs.getString("cart_id"));
                    item.setMemberId(rs.getString("member_id"));
                    item.setProductId(rs.getString("product_id"));
                    item.setQuantity(rs.getInt("quantity"));
                    return item;
                }
            }
        } catch (Exception e) {
            System.err.println("장바구니 단건 조회 실패: " + e.getMessage());
        }
        return null;
    }

    @Override
    public void deleteById(String id) {
        String sql = "DELETE FROM cartitem WHERE cart_id = ?";
        try (Connection conn = JdbcUtil.getConnection();) {
	        conn.setAutoCommit(false);
	        try( PreparedStatement pstmt = conn.prepareStatement(sql)) {
		        pstmt.setString(1, id);
		         int result = pstmt.executeUpdate();
			if(result < 0) {
				conn.commit();
				System.out.println("장바구니 아이템 삭제 완료: " + id);
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
            System.err.println("장바구니 아이템 삭제 실패: " + e.getMessage());
        }
    }
}