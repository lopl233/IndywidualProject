package PI.spring.rowmapper;

import PI.spring.model.Location;
import org.springframework.jdbc.core.RowMapper;
import PI.spring.model.User;

import java.sql.ResultSet;
import java.sql.SQLException;

public class LocationRowMapper implements RowMapper<Location> {

    @Override
    public Location mapRow(ResultSet resultSet, int line) throws SQLException {
        Location location = new Location();
        location.setX(resultSet.getDouble("x"));
        location.setY(resultSet.getDouble("y"));
        location.setDate(resultSet.getString("date"));
        return location;
    }

}