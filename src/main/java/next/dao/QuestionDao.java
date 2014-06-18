package next.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

import next.model.Question;
import next.support.db.ConnectionManager;

public class QuestionDao {
	private Connection con = null;

	public QuestionDao() {
		this.con = ConnectionManager.getConnection();
	}

	public void insert(Question question) throws SQLException {
		PreparedStatement pstmt = null;
		try {
			String sql = "INSERT INTO QUESTIONS (writer, title, contents, createdDate, countOfComment) VALUES (?, ?, ?, ?, ?)";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, question.getWriter());
			pstmt.setString(2, question.getTitle());
			pstmt.setString(3, question.getContents());
			pstmt.setTimestamp(4,
					new Timestamp(question.getTimeFromCreateDate()));
			pstmt.setInt(5, question.getCountOfComment());

			pstmt.executeUpdate();
		} finally {
			if (pstmt != null) {
				pstmt.close();
			}

			if (con != null) {
				con.close();
			}
		}
	}

	public List<Question> findAll() throws SQLException {
		try {
			SelectJdbcTemplate template = new SelectJdbcTemplate(con) {
				void setValues(PreparedStatement pstmt) throws SQLException {
				}

				Object mapRow(ResultSet rs) throws SQLException {
					return new Question(rs.getLong("questionId"),
							rs.getString("writer"), rs.getString("title"),
							rs.getString("contents"),
							rs.getTimestamp("createdDate"),
							rs.getInt("countOfComment"));
				}
			};
			String query = "SELECT questionId, writer, title, contents, createdDate, countOfComment FROM QUESTIONS "
                    + "order by questionId desc";
			return (List<Question>) template.selectAll(query);
		} finally {
			if (con != null) {
				con.close();
			}
		}
	}

	public Question findById(final long questionId) throws SQLException {
		try{
		SelectJdbcTemplate template = new SelectJdbcTemplate(con) {
			void setValues(PreparedStatement pstmt) throws SQLException {
				pstmt.setLong(1, questionId);
			}

			Object mapRow(ResultSet rs) throws SQLException {
				return new Question(rs.getLong("questionId"),
						rs.getString("writer"), rs.getString("title"),
						rs.getString("contents"),
						rs.getTimestamp("createdDate"),
						rs.getInt("countOfComment"));
			}
		};
		String query = "SELECT questionId, writer, title, contents, createdDate, countOfComment FROM QUESTIONS "
				+ "WHERE questionId = ?";
		return (Question) template.selectById(query);
		}finally {
			if (con != null) {
				con.close();
			}
		}
	}

	/*
	 * Connection con = null; PreparedStatement pstmt = null; ResultSet rs =
	 * null; try { con = ConnectionManager.getConnection(); String sql =
	 * "SELECT questionId, writer, title, contents, createdDate, countOfComment FROM QUESTIONS "
	 * + "WHERE questionId = ?"; pstmt = con.prepareStatement(sql);
	 * pstmt.setLong(1, questionId);
	 * 
	 * rs = pstmt.executeQuery();
	 * 
	 * Question question = null; if (rs.next()) { question = new
	 * Question(rs.getLong("questionId"), rs.getString("writer"),
	 * rs.getString("title"), rs.getString("contents"),
	 * rs.getTimestamp("createdDate"), rs.getInt("countOfComment")); }
	 * 
	 * return question; } finally { if (rs != null) { rs.close(); } if (pstmt !=
	 * null) { pstmt.close(); } if (con != null) { con.close(); } }
	 */
	public void addCountOfComment(long questionId) throws SQLException {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = ConnectionManager.getConnection();
			String sql = "UPDATE QUESTIONS SET countOfComment=countOfComment+1 WHERE questionId = ?";
			// update counter set count=count+1
			pstmt = con.prepareStatement(sql);
			pstmt.setLong(1, questionId);

			pstmt.executeUpdate();

			return;
		} finally {
			if (pstmt != null) {
				pstmt.close();
			}
			if (con != null) {
				con.close();
			}
		}
	}
}
