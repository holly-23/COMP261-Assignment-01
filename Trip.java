import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

public class Trip {
   private String tripID;
   public List<Stop> stopList = new ArrayList<Stop>();
   public List<Connections> connectionList = new ArrayList<Connections>();

   public Trip (String tripID) {
      this.tripID = tripID;
   }

   public void addStop(Stop stop){
      stopList.add(stop);                                //add stops to trips
   }

   public String getTripID(){ return tripID; }
}