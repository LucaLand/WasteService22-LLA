package it.unibo.coapobs;

import org.eclipse.californium.core.CoapHandler;
import org.eclipse.californium.core.CoapResponse;
import unibo.basicomm23.coap.CoapConnection;

public class CoapObserverJava implements CoapHandler {

    String currentState = null;


    public CoapObserverJava(String addr, String ctxName, String actorName) {
        new Thread(() -> {
            String path = "$ctxName/$actorName";
            CoapConnection coapConn = new CoapConnection(addr, path);
            coapConn.observeResource(this);
            System.out.println("CoapConnection active! On: " +addr +"/"+ ctxName + "/" + actorName +" -- " + coapConn);
        }).start();
    }

    @Override
    public void onLoad(CoapResponse response) {
        currentState = response.getResponseText();
        System.out.println("Stato: " + currentState);
    }

    @Override
    public void onError() {
        System.out.println("ERROR");
    }

    public String getCurrentState() {
        return currentState;
    }
}
