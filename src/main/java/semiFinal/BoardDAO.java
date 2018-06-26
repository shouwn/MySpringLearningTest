package semiFinal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.NamingException;

public class BoardDAO {
    public static List<Board> findAll() throws SQLException, NamingException 
    {
        String sql = "SELECT * FROM board";
        try (Connection connection = DB.getConnection("bbs2");
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
