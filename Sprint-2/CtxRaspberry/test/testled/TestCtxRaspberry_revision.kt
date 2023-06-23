package unibo.testled

/**
 * PER AVVIARE QUESTA TEST-UNIT BUILDARE IL PROGETTO CON IL FILE CtxRaspberry_revision_Test.qak NELLA DIRECTORY di MAIN (src) del progetto
 * RUN TESTS separately manually
 */

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

class TestCtxRaspberry_revision {
    private lateinit var conn: Interaction2021
    private lateinit var obsLed: CoapTestObserver
    private lateinit var obsSonar: CoapTestObserver

    private var setupOk = false

    private val StateHistoryLed = LinkedList<String>()
    private val StateHistorySonar23 = LinkedList<String>()

    private val testName = "TestCtxRaspberry Sprint-2"


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
            var sonarActor = QakContext.getActor("sonar23")

            while (ledActor == null) {
                println("$testName	|	waiting for application start...")
                CommUtils.delay(200)
                ledActor = QakContext.getActor("led")
            }

            while (sonarActor == null) {
                println("$testName	|	waiting for application start...")
                CommUtils.delay(200)
                sonarActor = QakContext.getActor("sonar23")
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
        val setupOk = ArrayBlockingQueue<Boolean>(2)


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

        object : Thread() {
            override fun run() {
                val ctx = "ctxraspberry"

                obsSonar = CoapTestObserver()
                val actor = "sonar23"
                val path = "$ctx/$actor"
                val coapConn = CoapConnection(addr, path)
                coapConn.observeResource(obsSonar)

                try {
                    setupOk.put(true)
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                }
            }
        }.start()

        setupOk.take()
        setupOk.take()
    }

    fun populateStateHistoryList(){
        StateHistoryLed.add("LedState(LedOff)")
        StateHistoryLed.add("LedState(LedBlink)")
        StateHistoryLed.add("LedState(LedOn)")
        StateHistoryLed.add("LedState(LedBlink)")
        StateHistoryLed.add("LedState(LedOff)")


        StateHistorySonar23.add("Started!")
        StateHistorySonar23.add("sonardata(75)")
        StateHistorySonar23.add("sonardata(70)")
        StateHistorySonar23.add("sonardata(65)")
        StateHistorySonar23.add("sonardata(60)")
        StateHistorySonar23.add("sonardata(55)")
        StateHistorySonar23.add("sonardata(50)")
    }

    @Test
    @Throws(InterruptedException::class)
    fun sonar23Test() {
        CommUtils.delay(3000)

        println("Sonar StateHistory: ${obsSonar.getUpdateHistory()}")

        assertEquals(StateHistorySonar23, obsSonar.getUpdateHistory())
    }

    @Test
    @Throws(InterruptedException::class)
    fun ledStateChangeTest() {
        //CommUtils.delay(3000)

        val ledOff = "msg(ledStateUpdate, dispatch, testunit, led, state(LedOff), 12)"
        val ledOn = "msg(ledStateUpdate, dispatch, testunit, led, state(LedOn), 18)"
        val ledBlink = "msg(ledStateUpdate, dispatch, testunit, led, state(LedBlink), 24)"



        try {
            println("$testName	|   Message: $ledOff")
            conn.forward(ledOff)
            CommUtils.delay(3000)

            println("$testName	|	Message: $ledBlink")
            conn.forward(ledBlink)
            CommUtils.delay(2000)

            println("$testName	|	Message: $ledOn")
            conn.forward(ledOn)
            CommUtils.delay(1000)

            println("$testName	|	Message: $ledBlink")
            conn.forward(ledBlink)
            CommUtils.delay(2000)

            println("$testName	|	Message: $ledOff")
            conn.forward(ledOff)
            CommUtils.delay(1000)

        } catch (e: Exception) {
            e.printStackTrace()
        }


        CommUtils.delay(500)
        println("Led StateHistory: ${obsLed.getUpdateHistory()}")

        assertEquals(StateHistoryLed, obsLed.getStateHistory())
        CommUtils.delay(500)
    }





}