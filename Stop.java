import javax.management.AttributeList;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Stop {
    private List<Connections>arrive = new ArrayList<Connections>();
    private List<Connections>depart = new ArrayList<Connections>();
    private String ID;
    private String name;
    private double lat;
    private double lon;
    private Location loc;
    boolean highlighted = false;

    public Stop(String ID, String name, double lat, double lon) {
        this.ID = ID;
        this.name = name;
        this.lat = lat;
        this.lon = lon;
        this.loc = Location.newFromLatLon(lat,lon);
    }
    public void addArrivals(Connections arriving){
        arrive.add(arriving);
    }
    public void addDepartures(Connections departing){
        depart.add(departing);
    }
    public String getName(){ return name; }
    public double getLat(){
        return lat;
    }
    public double getLon(){
        return lon;
    }
    public Location getLoc(){ return loc; }

    public void drawStop(Location start, double num, Graphics g, int x, int y){
        g.setColor(Color.BLUE);
        Point point = loc.asPoint(start,num);
        point.translate(x,y);
        int x1 = (int)point.getX(), y1 = (int)point.getY();
        if(highlighted==true){
            g.setColor(Color.GREEN);
            g.fillOval(x1,y1,10,10);
        }
        else{
            g.fillOval(x1,y1,5,5);
        }
    }
    public List<Connections> getConnection() {
        List<Connections> con = new ArrayList();
        con.addAll(arrive);
        con.addAll(depart);
        return con;
    }

}
