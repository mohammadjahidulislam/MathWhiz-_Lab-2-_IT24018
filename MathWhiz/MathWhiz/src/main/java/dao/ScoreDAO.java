package dao;

import model.GameScore;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class ScoreDAO {

    private static boolean tableReady = false;

    /** Creates the table if it does not exist yet (same structure as database/math_whiz_db.sql). */
    private static synchronized void ensureTable(Connection con) throws SQLException {
        if (tableReady) return;
        try (Statement st = con.createStatement()) {
            st.executeUpdate("CREATE TABLE IF NOT EXISTS game_scores ("
                    + "id INT AUTO_INCREMENT PRIMARY KEY, "
                    + "player_name VARCHAR(100) NOT NULL, "
                    + "score INT NOT NULL, "
                    + "difficulty VARCHAR(20) NOT NULL DEFAULT 'NORMAL', "
                    + "played_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP)");
        }
        tableReady = true;
    }

    public void save(GameScore gs) throws SQLException {
        String sql = "INSERT INTO game_scores (player_name, score, difficulty, played_at) VALUES (?, ?, ?, ?)";
        try (Connection con = DBUtil.getConnection()) {
            ensureTable(con);
            try (PreparedStatement ps = con.prepareStatement(sql)) {
                ps.setString(1, gs.getPlayerName());
                ps.setInt(2, gs.getScore());
                ps.setString(3, gs.getDifficulty());
                ps.setTimestamp(4, Timestamp.valueOf(gs.getPlayedAt()));
                ps.executeUpdate();
            }
        }
    }

    public List<GameScore> findTop10() throws SQLException {
        String sql = "SELECT id, player_name, score, difficulty, played_at FROM game_scores "
                + "ORDER BY score DESC, played_at ASC LIMIT 10";
        List<GameScore> list = new ArrayList<>();
        try (Connection con = DBUtil.getConnection()) {
            ensureTable(con);
            try (PreparedStatement ps = con.prepareStatement(sql);
                 ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    GameScore g = new GameScore();
                    g.setId(rs.getInt("id"));
                    g.setPlayerName(rs.getString("player_name"));
                    g.setScore(rs.getInt("score"));
                    g.setDifficulty(rs.getString("difficulty"));
                    Timestamp ts = rs.getTimestamp("played_at");
                    g.setPlayedAt(ts == null ? null : ts.toLocalDateTime());
                    list.add(g);
                }
            }
        }
        return list;
    }
}
