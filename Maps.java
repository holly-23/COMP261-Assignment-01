import javafx.scene.effect.Light;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.List;


public class Maps extends GUI {
    private final Map<String, Stop> stopMap = new HashMap<String, Stop>();
    private final Map<String, Trip> tripMap = new HashMap<String, Trip>();
    private final List<Connections> connectionsList = new ArrayList<Connections>();
    private final Set<Trip> tripList = new HashSet<>();
    private Set<Trip> tripSet = new HashSet<>();
    private Location start = Location.newFromLatLon(-12.499435, 130.94329);        //starting point and centre of network
    private double num = 10;

    @Override
    protected void redraw(Graphics g) {
        int x = getDrawingAreaDimension().width / 2, y = getDrawingAreaDimension().height / 2;
        for (Stop stop : stopMap.values()) {
            stop.drawStop(start, num, g, x, y);           //implements drawStop
        }
        for (Trip trip : tripList) {
            for (Connections connections : trip.connectionList) {
                connections.drawCons(start, num, g, x, y);    //implements drawCons
            }
        }
    }

    @Override
    protected void onClick(MouseEvent e) {
        Point point = new Point((e.getX() - getDrawingAreaDimension().width / 2), (e.getY() - getDrawingAreaDimension().height / 2));
        Location mouse = Location.newFromPoint(point, start, num);
        tripSet.clear();
        Stop nearest = null;
        Double min = Double.MAX_VALUE;
        for (Stop stop : stopMap.values()) {
            double mouseDistance = mouse.distance(stop.getLoc());
            if (min == null) {
                min = mouseDistance;
            } else if (mouseDistance < min) {
                nearest = stop;
                min = mouseDistance;
            }
        }

            if (nearest != null) {
                getTextOutputArea().setText(nearest.getName() + "\nTrip:");
                for (Connections c : nearest.getConnection()){
                   tripSet.add(c.getTrip());
                }
                for(Trip t : tripSet){
                    getTextOutputArea().append("\n" + t.getTripID());
                }
            }
        nearest.highlighted = true;
        }




    @Override
    protected void onSearch() {
        String log = getSearchBox().getText();
        tripSet = new HashSet<>();
        for(Stop stop : stopMap.values()){
            if(stop.getName().equals(log)){
                stop.highlighted = true;
                getTextOutputArea().setText(stop.getName() + "\nTrip:");
                for (Connections c : stop.getConnection()){
                    tripSet.add(c.getTrip());
                }
                for(Trip t : tripSet){
                    getTextOutputArea().append("\n" + t.getTripID());
                    for (Connections con:t.connectionList){
                        con.highlighted = true;
                    }
                }
            }

        }


     }

    @Override
    protected void onMove(Move m) {
        switch (m){
            case EAST: //moves map right
                this.start = start.moveBy(1,0);
                break;
            case WEST: //moves map left
                this.start = start.moveBy(-1,0);
                break;
            case NORTH: //moves map up
                this.start = start.moveBy(0,1);
                break;
            case SOUTH: //moves map down
                this.start = start.moveBy(0,-1);
                break;
            case ZOOM_IN:
                this.num += 6;
                break;
            case ZOOM_OUT:
                this.num -= 6;
                break;
            default:
                break;
        }
    }

    @Override
    protected void onLoad(File stopFile, File tripFile) {
        BufferedReader sReader = null;
        String current;                                                         //variable for the line being checked
        int i = 0;                                                              //check the line
        try {
            sReader = new BufferedReader(new FileReader(stopFile));
            while ((current = sReader.readLine()) != null) {
                if (i > 0) {                                                      //skip first line
                    String[] st = current.split("\t");
                    double lat = Double.parseDouble(st[2]);                     //converts to double from string
                    double lon = Double.parseDouble(st[3]);                     //converts to double from string
                    Stop stop = new Stop(st[0], st[1], lat, lon);               //creating the stop
                    stopMap.put(st[0], stop);                                    //storing in map
                }
                //System.out.println(current);
                i++;
            }

        } catch (IOException e) {
        }

        BufferedReader tReader = null;
        try {
            tReader = new BufferedReader(new FileReader(tripFile));
            while ((current = tReader.readLine()) != null) {
                List<Connections> temp = new ArrayList<>();
                String[] st = current.split("\t");
                Trip t = new Trip(st[0]);
                if (i > 0) {
                    for (i = 1; i < st.length + 1; i++) {
                        t.addStop(stopMap.get(st[1]));
                        if (i < st.length - 1) {
                            Stop arriving = stopMap.get(st[i]);
                            Stop departing = stopMap.get(st[i + 1]);
                            Connections c = new Connections(arriving, departing, t);
                            temp.add(c);
                            arriving.addArrivals(c);
                            departing.addDepartures(c);
                        }
                    }
                    tripList.add(t);
                    t.connectionList = temp;
                }
            }
        } catch (IOException e) {
    }
    }
    public static void main(String[] args) {new Maps();                         //initialise map
    }
}