package unibo.testwasteservice

/**
 * PER AVVIARE QUESTA TEST-UNIT BUILDARE IL PROGETTO CON IL FILE ctxWasteService_revision_Test2.qak NELLA DIRECTORY di MAIN (src) del progetto
 * E RUNNARE il CtxRaspberry dopo aver attivato il test
 */

import it.unibo.ctxwasteservice.main
import it.unibo.kactor.QakContext.Companion.getActor
import org.junit.Before
import org.junit.Test
import kotlin.Throws
import java.lang.InterruptedException
import Material
import unibo.basicomm23.coap.CoapConnection
import unibo.basicomm23.interfaces.Interaction2021
import unibo.basicomm23.tcp.TcpClientSupport
import unibo.basicomm23.utils.CommUtils
import unibo.coapobs.CoapTestObserver
import java.lang.Exception
import java.util.LinkedList
import java.util.concurrent.ArrayBlockingQueue
import kotlin.test.assertEquals

class Test2CtxWasteServiceSprint2_revision {

    private lateinit var conn: Interaction2021
    private lateinit var obsSonarDataHandler: CoapTestObserver

    private var setupOk = false

    private val material = Material.glass
    private val weight = 20

    private val StateHistorySonarDataHanlder = LinkedList<String>()

    private val testName = "TestTransportTrolleyStop Srint-2"


    @Before
    fun before() {
        if(!setupOk) {
            println("$testName	|	initTest...")
            object : Thread() {
                override fun run() {
                    main()
                }
            }.start()

            var sonardatahandlerActor = getActor("sonardatahandler")

            while (sonardatahandlerActor == null) {
                println("$testName	|	waiting for application start...")
                CommUtils.delay(200)
                sonardatahandlerActor = getActor("sonardatahandler")
            }

            try {
                conn = TcpClientSupport.connect("localhost", 8072, 5)
            } catch (e: Exception) {
                println("$testName	|	connection failed...")
            }
            startObs("localhost:8072")
            populateStateHistoryList()
            setupOk = true
        }
    }

    fun startObs(addr: String?) {
        val setupOk = ArrayBlockingQueue<Boolean>(1)


        object : Thread() {
            override fun run() {
                val ctx = "ctxwasteservice"

                obsSonarDataHandler = CoapTestObserver()
                val actor = "sonardatahandler"
                val path = "$ctx/$actor"
                val coapConn = CoapConnection(addr, path)

                coapConn.observeResource(obsSonarDataHandler)

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
        StateHistorySonarDataHanlder.add("State(handleSonarData(D:70))")
        StateHistorySonarDataHanlder.add("State(handleSonarData(D:65))")
        StateHistorySonarDataHanlder.add("State(handleSonarData(D:60))")
    }

    @Test
    @Throws(InterruptedException::class)
    fun transportTrolleyStop() {
        while(obsSonarDataHandler.getStateHistory().lastIndex < 2){
            CommUtils.delay(50)
        }


        println("TransportTrolley StateHistory: ${obsSonarDataHandler.getStateHistory()}")
        assertEquals(StateHistorySonarDataHanlder, obsSonarDataHandler.getStateHistory())
    }




}