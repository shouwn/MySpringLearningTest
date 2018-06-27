package article.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.NamingException;
import javax.sql.DataSource;

import article.dto.Board;

public class BoardDAO {

	private DataSource dataSource;

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	public List<Board> findAll() throws SQLException, NamingException, ClassNotFoundException 
    {
        String sql = "SELECT * FROM board";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            try (ResultSet resultSet = statement.executeQuery()) {
                ArrayList<Board> list = new ArrayList<Board>();
                while (resultSet.next()) {
                	Board board = new Board();
					board.setId(resultSet.getInt("id"));
					board.setBoardName(resultSet.getString("boardName"));
					list.add(board);
                }
                return list;
            }
        }
    }
}
