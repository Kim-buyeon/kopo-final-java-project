package com.finaljavaproject.www.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.finaljavaproject.www.domain.Member;
import com.finaljavaproject.www.domain.constant.MemberClassfication;
import com.finaljavaproject.www.util.JdbcUtil;

public class MemberRepository implements Repository<Member, String> {

	@Override
	public void save(Member item) {
	    String sql = "MERGE INTO member m " +
	                 "USING DUAL ON (m.id = ?) " +
	                 "WHEN MATCHED THEN " +
	                 "  UPDATE SET password = ?, name = ?, telno = ?, email = ? " +
	                 "WHEN NOT MATCHED THEN " +
	                 "  INSERT (id, password, name, telno, email, member_classification) " +
	                 "  VALUES (?, ?, ?, ?, ?, ?)";

	    try (Connection conn = JdbcUtil.getConnection()) {
	        conn.setAutoCommit(false);
	        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
	            pstmt.setString(1, item.getId());

	            pstmt.setString(2, item.getPassword());
	            pstmt.setString(3, item.getName());
	            pstmt.setString(4, item.getTelNo());
	            pstmt.setString(5, item.getEmail()); 

	            pstmt.setString(6, item.getId());
	            pstmt.setString(7, item.getPassword());
	            pstmt.setString(8, item.getName());
	            pstmt.setString(9, item.getTelNo());
	            pstmt.setString(10, item.getEmail()); 
	            pstmt.setString(11, item.getMemberClassfication().name()); 

	            pstmt.executeUpdate();
	            conn.commit();
	            System.out.println("회원 정보 저장/수정 완료: " + item.getId());
	        } catch (SQLException e) {
	            System.err.println("쿼리 실행 도중 오류 발생하여 롤백 진행: " + e.getMessage());
	            if (conn != null) conn.rollback();
	        } finally {
	            conn.setAutoCommit(true);
	        }
	    } catch (Exception e) {
	        System.err.println("회원 저장 실패: " + e.getMessage());
	        e.printStackTrace();
	    }
	}

    @Override
    public List<Member> findAll() {
        List<Member> members = new ArrayList<>();
        String sql = "SELECT * FROM member";
        try (Connection conn = JdbcUtil.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                MemberClassfication classification = MemberClassfication.valueOf(rs.getString("member_classification"));
                members.add(new Member(
                        rs.getString("id"),
                        rs.getString("password"),
                        rs.getString("name"),
                        rs.getString("telno"),
                        rs.getString("email"),
                        classification
                ));
            }
        } catch (Exception e) {
            System.err.println("전체 조회 실패: " + e.getMessage());
        }
        return members;
    }

    @Override
    public Member findById(String id) {
        String sql = "SELECT * FROM member WHERE id = ?";
        try (Connection conn = JdbcUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return new Member(
                            rs.getString("id"),
                            rs.getString("password"),
                            rs.getString("name"),
                            rs.getString("telno"),
                            rs.getString("email"),
                            MemberClassfication.valueOf(rs.getString("member_classification"))
                    );
                }
            }
        } catch (Exception e) {
            System.err.println("단건 조회 실패: " + e.getMessage());
        }
        return null;
    }

    @Override
    public void deleteById(String id) {
        String sql = "DELETE FROM member WHERE id = ?";
        try (Connection conn = JdbcUtil.getConnection();) {
	        conn.setAutoCommit(false);
            try( PreparedStatement pstmt = conn.prepareStatement(sql)) {
         	   pstmt.setString(1, id);
                     int result = pstmt.executeUpdate();
                     if (result > 0) {
                         System.out.println("회원 삭제 완료: " + id);
                     } else {
                         System.out.println("삭제할 회원이 존재하지 않습니다: " + id);
                     }
		} catch (SQLException e) {
			System.err.println("쿼리 실행 도중 오류 발생하여 롤백 진행" + e.getMessage());
			conn.rollback();
		}finally {
			conn.setAutoCommit(true);
		}
          
        } catch (Exception e) {
            System.err.println("삭제 실패: " + e.getMessage());
        }
    }
}