package unibo.coapobs

import org.eclipse.californium.core.CoapHandler
import java.util.concurrent.BlockingQueue
import java.util.concurrent.ArrayBlockingQueue
import org.eclipse.californium.core.CoapResponse
import java.lang.InterruptedException
import java.util.LinkedList
import kotlin.Throws

open class CoapTestObserver : CoapHandler {

    private val stateHistory = LinkedList<String>()
    private val updateHistory = LinkedList<String>()
    var currState: String? = null
        private set


    override fun onLoad(response: CoapResponse) {
        currState = response.responseText
        if(currState != null && currState.toString().contains("State")){
            stateHistory.add(currState!!)
        }
        if (currState !=  null){
            updateHistory.add(currState!!)
        }
    }

    override fun onError() {}

    fun getStateHistory() : List<String>{
        return stateHistory;
    }

    fun getUpdateHistory() : List<String>{
        return updateHistory;
    }
}