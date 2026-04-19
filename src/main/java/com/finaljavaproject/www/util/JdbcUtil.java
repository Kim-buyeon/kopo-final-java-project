package com.finaljavaproject.www.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import java.util.stream.Collectors;

public class JdbcUtil {
	private static Properties props = new Properties() ;
	static {
		try(InputStream is = JdbcUtil.class.getClassLoader().getResourceAsStream("application.properties")) {
			if(is != null) {
				props.load(is);
				Class.forName(props.getProperty("db.driver"));
			}else {
				System.err.println("경고 : application.properties 파일을 찾을 수 없습니다.");
			}
		} catch (Exception e) {
			System.err.println("DB 드라이버 로딩 실패:" + e.getMessage());
		}
	}
	
	public static Connection getConnection() throws SQLException{
		return DriverManager.getConnection(
				props.getProperty("db.url"),
				props.getProperty("db.user"),
				props.getProperty("db.password")
			);
	}
	
	public static void runSqlScript(String fileName) {
		    System.out.println("SQL 스크립트 실행 시작 : " + fileName);
		    try (Connection conn = getConnection();
		         Statement stmt = conn.createStatement()) {
		        
		        conn.setAutoCommit(false);
		        InputStream is = JdbcUtil.class.getClassLoader().getResourceAsStream(fileName);
		        
		        if (is == null) {
		            System.err.println("스크립트 파일을 찾을 수 없습니다.");
		            return;
		        }

		        try (BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"))) {
		            String script = reader.lines().collect(Collectors.joining("\n"));
		            String[] queries = script.split(";");
		            
		            for (String query : queries) {
		                String trimmedQuery = query.trim();
		                if (!trimmedQuery.isEmpty()) {
		                    try {
		                        stmt.execute(trimmedQuery);
		                    } catch (SQLException e) {
		                        if (e.getErrorCode() == 942 || e.getErrorCode() == 955) {
		                            System.out.println(">>> [알림] 삭제할 테이블이 없어 건너뜁니다.");
		                        } else {
		                            throw e; 
		                        }
		                    }
		                }
		            }
		            conn.commit();
		            System.out.println("SQL 스크립트 실행 완료");
		        } catch (SQLException e) {
		            System.err.println(">>> 쿼리 실행 중 중대한 오류 발생하여 롤백 진행: " + e.getMessage());
		            conn.rollback();
		        } finally {
		            conn.setAutoCommit(true);
		        }
		    } catch (Exception e) {
		        System.err.println(">>> 스크립트 실행 중 중대한 오류 발생: " + e.getMessage());
		    }
		}
	public static void close(AutoCloseable...resources) {
		for(AutoCloseable res: resources) {
			if(res != null) {
				try {
					res.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public static void setupDatabase(String fileName) {
		    if (!isDatabaseInitialized()) {
		        System.out.println(">>> 시스템 최초 실행: 모든 테이블을 생성합니다.");
		        runSqlScript(fileName); 
		    } else {
		        System.out.println(">>> 이미 데이터베이스가 구축되어 있습니다. 초기화를 건너뜁니다.");
		    }
		}
	
	public static boolean isDatabaseInitialized() {
		String[] requiredTables = {"MEMBER", "CATEGORY", "PRODUCT", "ORDERS", "CARTITEM"};
		try(Connection conn = getConnection()) {
			DatabaseMetaData meta = conn.getMetaData();
			for(String table: requiredTables) {
				try (ResultSet rs = meta.getTables(null, null, table.toUpperCase(), null)) {
			                if (!rs.next()) {
			                    System.out.println("DEBUG: [" + table + "] 테이블이 없습니다. 초기화를 진행합니다.");
			                    return false;
			                }
			            }
			}
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	
	
}
