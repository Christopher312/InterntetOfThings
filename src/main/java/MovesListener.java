/**
 * Created by graceyyu on 10/29/16.
 */
import com.bezirk.middleware.Bezirk;
 import com.bezirk.middleware.addressing.ZirkEndPoint;
import com.bezirk.middleware.java.proxy.BezirkMiddleware; 
import com.bezirk.middleware.messages.Event;
 import com.bezirk.middleware.messages.EventSet;

public class MovesListener {
    public MovesListener() {
        BezirkMiddleware.initialize("macaronipenguins"); 
        final Bezirk bezirk = BezirkMiddleware.registerZirk("Moves Listener");  
        EventSet turnEvents = new EventSet(MovementInstructionEvent.class); 
        turnEvents.setEventReceiver(new EventSet.EventReceiver()) { 
            @Override 
            public void receiveEvent(Event event, ZirkEndPoint sender) { 
                if (event instanceof MovementInstructionEvent) { 
                    final MovementInstructionEvent currentTurn = (MovementInstructionEvent) event; 
                    double[][] moves = new double[currentTurn.angles.length][2];
                    for (int i = 0; i < currentTurn.angles.length; i++) {
                        moves[i][0] = currentTurn.angles[i];
                        moves[i][1] = currentTurn.distances[i];
                    }
                    System.out.println("\nReceived turn: " + currentTurn.toString()); 
                    // do something 
                    TurnEvent turn = new TurnEvent(moves);
                    bezirk.sendEvent(turn);
                } 
            } 
        }
    }
}
