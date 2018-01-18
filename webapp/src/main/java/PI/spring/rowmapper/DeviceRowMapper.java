package PI.spring.rowmapper;

import PI.spring.model.Device;
import org.springframework.jdbc.core.RowMapper;
import PI.spring.model.User;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DeviceRowMapper implements RowMapper<Device> {

    @Override
    public Device mapRow(ResultSet resultSet, int line) throws SQLException {
        Device device = new Device();
        device.setDevice_id(resultSet.getString("device_id"));
        return device;
    }

}