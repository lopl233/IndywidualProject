package PI.spring.controller;

import PI.spring.dao.DeviceDao;
import PI.spring.model.Device;
import PI.spring.model.Location;
import PI.spring.model.User;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class DeviceController {
    @Autowired
    DeviceDao deviceDao;

    @RequestMapping(value = "/devices", method = RequestMethod.GET , produces = "application/json")
    @ResponseBody
    public String getDevices(HttpSession session, Model model){

        User user = (User)session.getAttribute("user");
        List<Device> deviceList = deviceDao.getDeviceList(user);
        Object[] mapped_devices= deviceList.stream().map((s) -> s.getDevice_id()).toArray();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("device_list", mapped_devices);
        return jsonObject.toString();
    }

    @RequestMapping(value = "/device_list", method = RequestMethod.GET)
    public String getDeviceList(HttpSession session, Model model){

        User user = (User)session.getAttribute("user");
        List<Device> deviceList = deviceDao.getDeviceList(user);
        Device device = new Device();
        model.addAttribute("deviceList",deviceList);
        model.addAttribute("device",device);

        return "devices";
    }

    @RequestMapping(value = "/track/{device_id}", method = RequestMethod.GET)
    public String trackDevice(Model model, @PathVariable("device_id") String device_id){

        Device device = new Device();
        device.setDevice_id(device_id);

        deviceDao.getLastLocations(device,10,"none");

        model.addAttribute("device",device);

        return "track_device";
    }

    @RequestMapping(value = "/add_device", method = RequestMethod.POST)
    public String addDevice(HttpSession session, @ModelAttribute Device device){
        User user = (User) session.getAttribute("user");
        deviceDao.addDevice(user,device);
        return "redirect:/devices";
    }

    @RequestMapping(value = "/add_device/{device_id}", method = RequestMethod.POST)
    @ResponseBody
    public String addDeviceREST(HttpSession session, @PathVariable("device_id") String device_id){
        User user = (User) session.getAttribute("user");
        Device device = new Device();
        device.setDevice_id(device_id);
        boolean added = deviceDao.addDevice(user,device);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("added", added);
        return jsonObject.toString();
    }

    @RequestMapping(value = "/delete_device/{device_id}", method = RequestMethod.GET)
    public String deleteDevice(HttpSession session, @PathVariable("device_id") String  device){
        Device device1 = new Device();
        device1.setDevice_id(device);
        System.out.println(device);
        User user = (User) session.getAttribute("user");
        deviceDao.deleteDevice(user,device1);
        return "redirect:/devices";
    }

    @RequestMapping(value = "/add_location", method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    public String addLocation(HttpEntity<String> httpEntity){

        String body = httpEntity.getBody();
        JSONObject obj = new JSONObject(body);
        JSONObject result = new JSONObject();
        Device device = new Device();
        Location location = new Location();

        try {
            device.setDevice_id(obj.getString("device_id"));
            location.setX(obj.getDouble("x"));
            location.setY(obj.getDouble("y"));
        }catch (JSONException e){
            result.put("result", "failed");
            return result.toString();
        }

        boolean added = deviceDao.addLoction(device,location);
        if (added) result.put("result", "added");
        else result.put("result", "failed");

        return result.toString();
    }

    @RequestMapping(value = "/get_locations/{device_id}/{date}/{how_much}", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public String getLocations(@PathVariable(value="device_id") String device_id,@PathVariable(value="date") String date,@PathVariable(value="how_much") int how_much){

        Device device = new Device();
        device.setDevice_id(device_id);

        JSONObject result = new JSONObject();
        deviceDao.getLastLocations(device, how_much, date);

        List<String> locations = device.getLocations().stream().map((Location l)->l.toString()).collect(Collectors.toList());

        result.put("numberOfLocations", locations.size());
        result.put("locations",locations);

        return result.toString();
    }




}
