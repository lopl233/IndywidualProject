package PI.spring.dao;

import PI.spring.model.Device;
import PI.spring.model.Location;
import PI.spring.model.User;

import java.util.List;

public interface DeviceDao {
    List<Device> getDeviceList(User user);
    void getLastLocations(Device device, int ammount, String date);
    boolean addDevice(User user, Device device);
    boolean deleteDevice(User user, Device device);
    boolean addLoction(Device device, Location location);
}
