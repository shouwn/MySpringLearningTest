package article.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.NamingException;

import article.ConnectionMaker;
import article.dto.Board;

public class BoardDAO {

	private ConnectionMaker connectionMaker;
    
    public void setConnectionMaker(ConnectionMaker connectionMaker) {
		this.connectionMaker = connectionMaker;
	}

	public List<Board> findAll() throws SQLException, NamingException, ClassNotFoundException 
    {
        String sql = "SELECT * FROM board";
        try (Connection connection = connectionMaker.makeConnection();
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
