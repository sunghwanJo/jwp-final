package next.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import next.model.Question;

public abstract class SelectJdbcTemplate {
	private Connection conn;

	public SelectJdbcTemplate(Connection conn) {
		this.conn = conn;
	}

	public Object selectById(String query) throws SQLException {
		PreparedStatement pstmt = conn.prepareStatement(query);
		setValues(pstmt);

		ResultSet rs = pstmt.executeQuery();
		if (rs.next()) {
			return mapRow(rs);
		}
		return null;
	}

	public Object selectAll(String query) throws SQLException {
		PreparedStatement pstmt = conn.prepareStatement(query);
		setValues(pstmt);
		ResultSet rs = pstmt.executeQuery();

		List<Question> questions = new ArrayList<Question>();
		Question question = null;
		while (rs.next()) {
			question = new Question(rs.getLong("questionId"),
					rs.getString("writer"), rs.getString("title"), null,
					rs.getTimestamp("createdDate"), rs.getInt("countOfComment"));
			questions.add(question);
		}
		return questions;
	}

	abstract Object mapRow(ResultSet rs) throws SQLException;

	abstract void setValues(PreparedStatement pstmt) throws SQLException;
}