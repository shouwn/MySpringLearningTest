package article.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.NamingException;

import article.ConnectionMaker;
import article.dto.User;

public class UserDAO {
	
	private ConnectionMaker connectionMaker;

    public void setConnectionMaker(ConnectionMaker connectionMaker) {
		this.connectionMaker = connectionMaker;
	}
    
	public List<User> findAll() throws SQLException, NamingException, ClassNotFoundException 
    {
        String sql = "SELECT * FROM user";
        try (Connection connection = connectionMaker.makeConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            try (ResultSet resultSet = statement.executeQuery()) {
                ArrayList<User> list = new ArrayList<User>();
                while (resultSet.next()) {
					User user = new User();
					user.setId(resultSet.getInt("id"));
					user.setName(resultSet.getString("name"));
					list.add(user);
                }
                return list;
            }
        }
    }
}
