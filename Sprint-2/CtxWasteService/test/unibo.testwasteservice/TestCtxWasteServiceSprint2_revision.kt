package unibo.testwasteservice

/**
 * PER AVVIARE QUESTA TEST-UNIT BUILDARE IL PROGETTO CON IL FILE ctxWasteService_Test.qak NELLA DIRECTORY di MAIN (src) del progetto
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

class TestCtxWasteServiceSprint2_revision {

    private lateinit var conn: Interaction2021
    private lateinit var obsTransporttrolley: CoapTestObserver
    private lateinit var obsCustompathexecutor: CoapTestObserver
    private lateinit var obsSonarDataHandler: CoapTestObserver

    private var setupOk = false

    private val material = Material.glass
    private val weight = 20

    private val StateHistoryTransportTrolley = LinkedList<String>()
    private val StateHistoryCustomPathExecutor = LinkedList<String>()
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

            var transporttrolleyActor = getActor("transporttrolley")
            var custompathexecutorActor = getActor("custompathexecutor")
            var sonardatahandlerActor = getActor("sonardatahandler")



            while (transporttrolleyActor == null) {
                println("$testName	|	waiting for application start...")
                CommUtils.delay(200)
                transporttrolleyActor = getActor("transporttrolley")
            }

            while (custompathexecutorActor == null) {
                println("$testName	|	waiting for application start...")
                CommUtils.delay(200)
                custompathexecutorActor = getActor("custompathexecutor")
            }

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
        val setupOk = ArrayBlockingQueue<Boolean>(3)


        object : Thread() {
            override fun run() {
                val ctx = "ctxwasteservice"


                obsTransporttrolley = CoapTestObserver()
                val actor = "transporttrolley"
                val path = "$ctx/$actor"
                val coapConn = CoapConnection(addr, path)
                coapConn.observeResource(obsTransporttrolley)

                try {
                    setupOk.put(true)
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                }
            }
        }.start()


        object : Thread() {
            override fun run() {
                val ctx = "ctxwasteservice"


                obsCustompathexecutor = CoapTestObserver()
                val actor = "custompathexecutor"
                val path = "$ctx/$actor"
                val coapConn = CoapConnection(addr, path)
                coapConn.observeResource(obsCustompathexecutor)

                try {
                    setupOk.put(true)
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                }
            }
        }.start()

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
        setupOk.take()
        setupOk.take()
    }

    fun populateStateHistoryList(){
        StateHistorySonarDataHanlder.add("State(handleSonarData(D:50))")
        StateHistorySonarDataHanlder.add("State(handleSonarData(D:5))")
        StateHistorySonarDataHanlder.add("State(handleSonarData(D:5))")
        StateHistorySonarDataHanlder.add("State(handleSonarData(D:15))")
        StateHistorySonarDataHanlder.add("State(handleSonarData(D:5))")
        StateHistorySonarDataHanlder.add("State(handleSonarData(D:15))")


        //TT
        StateHistoryTransportTrolley.add("transporttrolleyState(waiting)")
        StateHistoryTransportTrolley.add("transporttrolleyState(handlePickupReq)")
        StateHistoryTransportTrolley.add("transporttrolleyState(goPickUp)")
        StateHistoryTransportTrolley.add("transporttrolleyState(handleAlarm)")
        StateHistoryTransportTrolley.add("transporttrolleyState(resume)")
        StateHistoryTransportTrolley.add("transporttrolleyState(pickupDone)")
        StateHistoryTransportTrolley.add("transporttrolleyState(goDeposit)")
        StateHistoryTransportTrolley.add("transporttrolleyState(handleAlarm)")
        StateHistoryTransportTrolley.add("transporttrolleyState(resume)")
        StateHistoryTransportTrolley.add("transporttrolleyState(depositDone)")


        //CustomPathExecutor
        StateHistoryCustomPathExecutor.add("custompathexecutorState(handleMoveRequest)")
        StateHistoryCustomPathExecutor.add("custompathexecutorState(execStep)")
        StateHistoryCustomPathExecutor.add("custompathexecutorState(execStep)")
        StateHistoryCustomPathExecutor.add("custompathexecutorState(stopped)")
        StateHistoryCustomPathExecutor.add("custompathexecutorState(resumed)")
        StateHistoryCustomPathExecutor.add("custompathexecutorState(execStep)")
        StateHistoryCustomPathExecutor.add("custompathexecutorState(stepDone(1))")
        StateHistoryCustomPathExecutor.add("custompathexecutorState(pathDone(1))")
        StateHistoryCustomPathExecutor.add("custompathexecutorState(handleMoveRequest)")
        StateHistoryCustomPathExecutor.add("custompathexecutorState(execStep)")
        StateHistoryCustomPathExecutor.add("custompathexecutorState(stopped)")
        StateHistoryCustomPathExecutor.add("custompathexecutorState(resumed)")
        StateHistoryCustomPathExecutor.add("custompathexecutorState(execStep)")
        StateHistoryCustomPathExecutor.add("custompathexecutorState(execStep)")
        StateHistoryCustomPathExecutor.add("custompathexecutorState(stepDone(2))")
        StateHistoryCustomPathExecutor.add("custompathexecutorState(execStep)")
        StateHistoryCustomPathExecutor.add("custompathexecutorState(execStep)")
        StateHistoryCustomPathExecutor.add("custompathexecutorState(execStep)")
        StateHistoryCustomPathExecutor.add("custompathexecutorState(stepDone(3))")
        StateHistoryCustomPathExecutor.add("custompathexecutorState(pathDone(3))")

    }

    @Test
    @Throws(InterruptedException::class)
    fun transportTrolleyStopRevision() {
        CommUtils.delay(3000)
        var asw = ""

        val depositRequest = "msg(depositRequest, request, testunit, wasteservice, depositRequest($material, $weight),1)"
        println("$testName	|    message: $depositRequest")
        try {
            conn.forward(depositRequest)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        val sonarDataUpdate1 = "msg(coapUpdate,dispatch,sonar23,sonardatahandler,coapUpdate(sonar23, sonardata(50)),15)"
        val sonarDataUpdateStop = "msg(coapUpdate,dispatch,sonar23,sonardatahandler,coapUpdate(sonar23, sonardata(5)),20)"
        val sonarDataUpdate3 = "msg(coapUpdate,dispatch,sonar23,sonardatahandler,coapUpdate(sonar23, sonardata(15)),25)"



        try {
            println("$testName	|   Message: $sonarDataUpdate1")
            conn.forward(sonarDataUpdate1)
            CommUtils.delay(3000)

            println("$testName	|	Message: $sonarDataUpdateStop")
            conn.forward(sonarDataUpdateStop)
            CommUtils.delay(1000)

            println("$testName	|	Message: $sonarDataUpdateStop")
            conn.forward(sonarDataUpdateStop)
            CommUtils.delay(1000)

            println("$testName	|	Message: $sonarDataUpdate3")
            conn.forward(sonarDataUpdate3)
            CommUtils.delay(7000)

            println("$testName	|	Message: $sonarDataUpdateStop")
            conn.forward(sonarDataUpdateStop)
            CommUtils.delay(1000)

            println("$testName	|	Message: $sonarDataUpdate3")
            conn.forward(sonarDataUpdate3)
            CommUtils.delay(10000)

        } catch (e: Exception) {
            e.printStackTrace()
        }


        CommUtils.delay(500)


        println("TransportTrolley StateHistory: ${obsTransporttrolley.getStateHistory()}")
        println("CustomPathExecutor StateHistory: ${obsCustompathexecutor.getStateHistory()}")
        println("SonarDataHandler StateHistory: ${obsSonarDataHandler.getStateHistory()}")

        assertEquals(StateHistoryTransportTrolley, obsTransporttrolley.getStateHistory())
        assertEquals(StateHistoryCustomPathExecutor, obsCustompathexecutor.getStateHistory())
        assertEquals(StateHistorySonarDataHanlder, obsSonarDataHandler.getStateHistory())

        CommUtils.delay(500)
    }




}