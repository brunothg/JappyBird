package de.bno.jappybird.dao;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

public class BestenlisteFileDAO implements BestenlisteDAO {

	private static Connection con;

	public BestenlisteFileDAO() {

		loadDriver();
		setUp();
	}

	private void loadDriver() {
		try {
			Class.forName("org.hsqldb.jdbcDriver");
		} catch (ClassNotFoundException e) {
		}
	}

	private Connection getConnection() throws SQLException {

		if (con != null) {
			return con;
		}

		String file = new File("./data/db").getAbsolutePath();

		con = DriverManager.getConnection("jdbc:hsqldb:file:" + file
				+ "; shutdown=true", "root", "");

		return con;
	}

	private void setUp() {

		try {

			String sql = "create table if not exists scores (name varchar(50), score int)";

			Statement stmt = getConnection().createStatement();
			stmt.execute(sql);

			stmt.close();

			con.close();
			con = null;
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public List<Score> getBestenliste() {

		return getBestScores(10);
	}

	@Override
	public List<Score> getBestScores(int number) {

		number = Math.max(1, Math.min(number, 10));

		List<Score> ret = new LinkedList<Score>();

		try {
			Statement stmt = getConnection().createStatement();

			String sql = "select * from scores order by score desc limit "
					+ number;

			ResultSet result = stmt.executeQuery(sql);

			while (result.next()) {
				ret.add(new Score(result.getString("name"), result
						.getInt("score")));
			}

			stmt.close();
			con.close();
			con = null;
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return ret;

	}

	@Override
	public List<Score> getLastScores(int number) {
		number = Math.max(1, Math.min(number, 10));

		List<Score> ret = new LinkedList<Score>();

		try {
			Statement stmt = getConnection().createStatement();

			String sql = "select * from scores order by score asc limit "
					+ number;

			ResultSet result = stmt.executeQuery(sql);

			while (result.next()) {
				ret.add(new Score(result.getString("name"), result
						.getInt("score")));
			}

			stmt.close();
			con.close();
			con = null;
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return ret;
	}

	private int numberOfScores() {

		int ret = 0;

		try {
			Statement stmt = getConnection().createStatement();

			String sql = "select count(*) from scores";

			ResultSet result = stmt.executeQuery(sql);

			result.next();
			ret = result.getInt(1);

			stmt.close();
			con.close();
			con = null;
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return ret;
	}

	private boolean exists(Score score) {

		boolean ret = false;

		try {
			Statement stmt = getConnection().createStatement();

			String sql = String.format(
					"select * from scores where score=%d and name='%s'",
					score.getScore(), score.getName());

			ResultSet result = stmt.executeQuery(sql);

			ret = result.next();

			stmt.close();
			con.close();
			con = null;
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return ret;
	}

	@Override
	public boolean insertScore(Score score) {

		boolean ret = true;

		if (exists(score)) {
			return false;
		}

		try {

			int scoreToBeat = -1;
			Score lastScore = null;

			int numberOfScores = numberOfScores();

			if (numberOfScores >= 10) {
				List<Score> last = getLastScores(1);

				if (!last.isEmpty()) {

					lastScore = last.get(0);
					scoreToBeat = last.get(0).getScore();
				}
			}

			if (score.getScore() <= scoreToBeat) {
				ret = false;
			} else {

				if (numberOfScores >= 10 && lastScore != null) {
					Statement stmt = getConnection().createStatement();

					stmt.execute(String.format(
							"delete from scores where score=%d and name='%s'",
							lastScore.getScore(), lastScore.getName()));

					stmt.close();
				}

				Statement stmt = getConnection().createStatement();

				String sql = "insert into scores values ('" + score.getName()
						+ "'," + score.getScore() + ")";

				stmt.execute(sql);

				stmt.close();

				con.close();
				con = null;

			}
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}

		return ret;
	}

}
