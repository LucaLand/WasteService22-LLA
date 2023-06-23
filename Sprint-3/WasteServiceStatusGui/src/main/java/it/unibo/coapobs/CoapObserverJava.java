package it.unibo.coapobs;

import org.eclipse.californium.core.CoapHandler;
import org.eclipse.californium.core.CoapResponse;
import unibo.basicomm23.coap.CoapConnection;

public class CoapObserverJava implements CoapHandler {

    String currentState = null;
    private String obsName = "obs";




    public CoapObserverJava(String addr, String ctxName, String actorName) {
            String path = ctxName +"/"+ actorName;
            CoapConnection coapConn = new CoapConnection(addr, path);
            coapConn.observeResource(this);
            System.out.println("CoapConnection active! On: " +addr +"/"+ ctxName + "/" + actorName +" -- " + coapConn);
            this.obsName = addr+"/"+ctxName+"/"+actorName;
    }

    @Override
    public void onLoad(CoapResponse response) {
        currentState = response.getResponseText();
        System.out.println(obsName+ " || Stato: " + currentState);
    }

    @Override
    public void onError() {
        System.out.println(obsName+ " || ERROR");
    }

    public String getCurrentState() {
        return currentState;
    }


}
