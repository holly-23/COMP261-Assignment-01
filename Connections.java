import java.awt.*;

public class Connections {
    private Stop arrive;
    private Stop depart;
    public Trip connecting;
    boolean highlighted = false;

    public Connections(Stop arrive, Stop depart, Trip connecting){
        this.arrive = arrive;
        this.depart = depart;
        this.connecting = connecting;

    }
    public void drawCons(Location start, double num, Graphics g, int x, int y){
        Point in = arrive.getLoc().asPoint(start,num);
        Point out = depart.getLoc().asPoint(start,num);
        in.translate(x,y);
        out.translate(x,y);
        if(highlighted==true) {
            g.setColor(Color.GREEN);
            int x1 = (int) in.getX(), y1 = (int) in.getY();
            int x2 = (int) out.getX(), y2 = (int) out.getY();
            g.drawLine(x1, y1, x2, y2);
        }else{
            g.setColor(Color.DARK_GRAY);
            int x1 = (int) in.getX(), y1 = (int) in.getY();
            int x2 = (int) out.getX(), y2 = (int) out.getY();
            g.drawLine(x1, y1, x2, y2);
        }
    }
    public Trip getTrip(){
        return this.connecting;
    }
}

