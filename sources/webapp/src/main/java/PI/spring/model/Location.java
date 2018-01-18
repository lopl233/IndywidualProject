package PI.spring.model;

import org.json.JSONObject;

public class Location {
    private double x;
    private double y;
    private String date;

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public String getDate() {return date;}

    public void setDate(String date) {this.date = date;}

    @Override
    public String toString() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("x",x);
        jsonObject.put("y",y);
        jsonObject.put("date",date);
        return jsonObject.toString();
    }
}
