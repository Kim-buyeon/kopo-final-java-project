package com.finaljavaproject.www.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.finaljavaproject.www.domain.Category;
import com.finaljavaproject.www.util.JdbcUtil;

public class CategoryRepository implements Repository<Category, String> {

    @Override
    public void save(Category item) {
        String sql = "MERGE INTO category c " +
                     "USING DUAL ON (c.category_id = ?) " +
                     "WHEN MATCHED THEN " +
                     "  UPDATE SET name = ?, sortorder = ?, parent_category_id  = ? " +
                     "WHEN NOT MATCHED THEN " +
                     "  INSERT (category_id, name, sortorder, parent_category_id ) " +
                     "  VALUES (?, ?, ?, ?)";

        try (Connection conn = JdbcUtil.getConnection();) {
	        conn.setAutoCommit(false);
	        try(PreparedStatement pstmt = conn.prepareStatement(sql)) {
		           pstmt.setString(1, item.getCategoryId());
		            pstmt.setString(2, item.getName());
		            pstmt.setString(3, item.getSortOrder());
		            pstmt.setString(4, item.getParentId()); 
		            pstmt.setString(5, item.getCategoryId());
		            pstmt.setString(6, item.getName());
		            pstmt.setString(7, item.getSortOrder());
		            pstmt.setString(8, item.getParentId());
		            pstmt.executeUpdate();
		            conn.commit();
		            System.out.println("정상적으로 저장/수정되었습니다");
	        } catch (SQLException e) {
		        System.err.println("쿼리 발생 중 오류 발생 롤백 진행" + e.getMessage());
			conn.rollback();
	        }finally {
		        conn.setAutoCommit(false);
	        }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Category> findAll() {
        List<Category> categories = new ArrayList<>();
        String sql = "SELECT * FROM category ORDER BY sortorder ASC";

        try (Connection conn = JdbcUtil.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Category category = new Category(
         		       rs.getString("category_id"),
         		       rs.getString("parent_category_id"),      
         		       rs.getString("name"),
         		       rs.getString("sortorder")
                );
                categories.add(category);
            }
        } catch (Exception e) {
            System.err.println("카테고리 전체 조회 실패: " + e.getMessage());
        }
        return categories;
    }

    @Override
    public Category findById(String id) {
        String sql = "SELECT * FROM category WHERE category_id = ?";
        try (Connection conn = JdbcUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return new Category(
                  		  rs.getString("category_id"),
                  		  rs.getString("parent_category_id"),
                  		  rs.getString("name"),
                  		  rs.getString("sortorder")
                    );
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void deleteById(String id) {
        String sql = "DELETE FROM category WHERE category_id = ?";
        try (Connection conn = JdbcUtil.getConnection();) {
	        conn.setAutoCommit(false);
	        try( PreparedStatement pstmt = conn.prepareStatement(sql)) {
		        pstmt.setString(1, id);
		       int result =  pstmt.executeUpdate();
		       if(result < 0) {
			       conn.commit();
			       System.out.println("카테고리 삭제 완료");
		       }else {
			       System.out.println("삭제할 카테고리가 존재하지 않습니다.");
		       }
		} catch (SQLException e) {
			conn.rollback();
			System.err.println("쿼리 실행 도중 오류 발생" + e.getMessage());
		}finally {
			conn.setAutoCommit(true);
		}
            
        } catch (Exception e) {
            System.err.println("카테고리 삭제 실패: " + e.getMessage());
        }
    }
}