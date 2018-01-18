package PI.spring.dao;

import PI.spring.model.Device;
import PI.spring.model.Location;
import PI.spring.model.User;
import PI.spring.rowmapper.DeviceRowMapper;
import PI.spring.rowmapper.LocationRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import javax.sql.DataSource;
import java.util.List;

public class DeviceDaoReal implements DeviceDao{
    @Autowired
    DataSource dataSource;

    @Override
    public List<Device> getDeviceList(User user) {

            JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

            String sql = String.format("Select * from devices where user_id = '%s'",user.getId());
            List<Device> devices = jdbcTemplate.query(sql,new DeviceRowMapper());
        return devices;
    }

    @Override
    public void getLastLocations(Device device, int ammount, String date) {

        String where_clausure = "";
        if(!date.equals("none")){
            where_clausure = "and date > '" + date+ "'";
        }
        String sql = String.format("select * from locations where device_id = '%s' %s order by date desc limit %s",
                device.getDevice_id(),
                where_clausure,
                ammount);

        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        List<Location> locations = jdbcTemplate.query(sql, new LocationRowMapper());

        device.setLocations(locations);

    }

    @Override
    public boolean addDevice(User user, Device device) {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        String sql = String.format("insert into devices(user_id,device_id) values ('%s','%s')",
            user.getId(),
            device.getDevice_id());

        int row_affected = 0;

        try {
            row_affected = jdbcTemplate.update(sql);
        }
        catch (Exception exception){return false;}
        finally {
        return row_affected > 0 ? true : false;
        }

    }

    @Override
    public boolean deleteDevice(User user, Device device) {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        String sql = String.format("delete from  devices where user_id = '%s' and device_id = '%s'" ,
                user.getId(),
                device.getDevice_id());

        int row_affected = jdbcTemplate.update(sql);
        return row_affected > 0 ? true : false;

    }

    @Override
    public boolean addLoction(Device device, Location location) {
        String sql = String.format("insert into locations(device_id,x,y,date) values ('%s','%s','%s',NOW())",
            device.getDevice_id(),
                location.getX(),
                location.getY());

        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        int row_affected = jdbcTemplate.update(sql);
        return row_affected > 0 ? true : false;
    }
}
