package unibo.testled

import it.unibo.ctxraspberry.main
import it.unibo.kactor.QakContext
import org.junit.Before
import org.junit.Test
import unibo.basicomm23.coap.CoapConnection
import unibo.basicomm23.interfaces.Interaction2021
import unibo.basicomm23.tcp.TcpClientSupport
import unibo.basicomm23.utils.CommUtils
import unibo.coapobs.CoapTestObserver
import java.lang.Exception
import java.util.*
import java.util.concurrent.ArrayBlockingQueue
import kotlin.test.assertEquals

class TestLed {
    private lateinit var conn: Interaction2021
    private lateinit var obsLed: CoapTestObserver

    private var setupOk = false

    private val StateHistoryLed = LinkedList<String>()

    private val testName = "TestLedStateChange Sprint-2"


    @Before
    fun before() {
        if(!setupOk) {
            println("$testName	|	initTest...")
            object : Thread() {
                override fun run() {
                    main()
                }
            }.start()

            var ledActor = QakContext.getActor("led")

            while (ledActor == null) {
                println("$testName	|	waiting for application start...")
                CommUtils.delay(200)
                ledActor = QakContext.getActor("led")
            }

            try {
                conn = TcpClientSupport.connect("localhost", 8076, 5)
            } catch (e: Exception) {
                println("$testName	|	connection failed...")
            }
            startObs("localhost:8076")
            populateStateHistoryList()
            setupOk = true
        }
    }

    fun startObs(addr: String?) {
        val setupOk = ArrayBlockingQueue<Boolean>(1)


        object : Thread() {
            override fun run() {
                val ctx = "ctxraspberry"


                obsLed = CoapTestObserver()
                val actor = "led"
                val path = "$ctx/$actor"
                val coapConn = CoapConnection(addr, path)
                coapConn.observeResource(obsLed)

                try {
                    setupOk.put(true)
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                }
            }
        }.start()

        setupOk.take()
    }

    fun populateStateHistoryList(){
        StateHistoryLed.add("LedState(LedOff)")
        StateHistoryLed.add("LedState(LedBlink)")
        StateHistoryLed.add("LedState(LedOn)")
        StateHistoryLed.add("LedState(LedBlink)")
        StateHistoryLed.add("LedState(LedOff)")
    }

    @Test
    @Throws(InterruptedException::class)
    fun transportTrolleyStop() {
        CommUtils.delay(3000)

        val robotStateEventHome = "msg(robotStateEvent, event, testunit, none, robotStateEvent(athome), 12)"
        val robotStateEventMoving = "msg(robotStateEvent, event, testunit, none, robotStateEvent(moving), 18)"
        val robotStateEventStopped = "msg(robotStateEvent, event, testunit, none, robotStateEvent(stopped), 24)"


        try {
            println("$testName	|   Message: $robotStateEventHome")
            conn.forward(robotStateEventHome)
            CommUtils.delay(3000)

            println("$testName	|	Message: $robotStateEventMoving")
            conn.forward(robotStateEventMoving)
            CommUtils.delay(2000)

            println("$testName	|	Message: $robotStateEventStopped")
            conn.forward(robotStateEventStopped)
            CommUtils.delay(1000)

            println("$testName	|	Message: $robotStateEventMoving")
            conn.forward(robotStateEventMoving)
            CommUtils.delay(2000)

            println("$testName	|	Message: $robotStateEventHome")
            conn.forward(robotStateEventHome)
            CommUtils.delay(1000)

        } catch (e: Exception) {
            e.printStackTrace()
        }


        CommUtils.delay(500)


        println("TransportTrolley StateHistory: ${obsLed.getStateHistory()}")

        assertEquals(StateHistoryLed, obsLed.getStateHistory())

        CommUtils.delay(500)
    }
}