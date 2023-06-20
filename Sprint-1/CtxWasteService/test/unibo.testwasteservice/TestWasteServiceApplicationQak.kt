package unibo.testwasteservice

/**
 * PER AVVIARE QUESTA TEST-UNIT BUILDARE IL PROGETTO CON IL FILE ctxWasteService_Test.qak NELLA DIRECTORY di MAIN (src)
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
import kotlin.test.assertTrue

class TestWasteServiceApplicationQak {

    private lateinit var conn: Interaction2021
    private lateinit var obsWasteService: CoapTestObserver
    private lateinit var obsTransporttrolley: CoapTestObserver
    private lateinit var obsCustompathexecutor: CoapTestObserver
    private var setupOk = false

    private val material = Material.plastic
    private val weight = 20

    private val StateHistoryWasteService = LinkedList<String>()
    private val StateHistoryTransportTrolley = LinkedList<String>()
    private val StateHistoryCustomPathExecutor = LinkedList<String>()


    @Before
    fun before() {
        if(!setupOk) {
            println("Testwasteservice	|	initTest...")
            object : Thread() {
                override fun run() {
                    main()
                }
            }.start()
            var wasteserviceActor = getActor("wasteservice")
            var transporttrolleyActor = getActor("transporttrolley")
            var custompathexecutorActor = getActor("custompathexecutor")

            while (wasteserviceActor == null) {
                println("Testwasteservice	|	waiting for application start...")
                CommUtils.delay(200)
                wasteserviceActor = getActor("wasteservice")
            }

            while (transporttrolleyActor == null) {
                println("Testwasteservice	|	waiting for application start...")
                CommUtils.delay(200)
                transporttrolleyActor = getActor("transporttrolley")
            }

            while (custompathexecutorActor == null) {
                println("Testwasteservice	|	waiting for application start...")
                CommUtils.delay(200)
                custompathexecutorActor = getActor("custompathexecutor")
            }

            try {
                conn = TcpClientSupport.connect("localhost", 8072, 5)
            } catch (e: Exception) {
                println("Testwasteservice	|	connection failed...")
            }
            startObs("localhost:8072")
            populateStateHistoryList()
            //obs.getNext()
            setupOk = true
        } else {
            //obs.clearHistory()
        }
    }

    fun startObs(addr: String?) {
        val setupOk = ArrayBlockingQueue<Boolean>(3)
        object : Thread() {
            override fun run() {
                val ctx = "ctxwasteservice"

                obsWasteService = CoapTestObserver()
                val actor = "wasteservice"
                val path = "$ctx/$actor"
                val coapConn = CoapConnection(addr, path)

                coapConn.observeResource(obsWasteService)

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

        setupOk.take()
        setupOk.take()
        setupOk.take()
    }

    fun populateStateHistoryList(){
        StateHistoryWasteService.add("wasteserviceState(waiting)")
        StateHistoryWasteService.add("wasteserviceState(requesthandling)")
        StateHistoryWasteService.add("wasteserviceState(requestAccepted)")
        StateHistoryWasteService.add("wasteserviceState(pickingUp)")
        StateHistoryWasteService.add("wasteserviceState(pickupOk)")
        StateHistoryWasteService.add("wasteserviceState(waiting)")
        StateHistoryWasteService.add("wasteserviceState(requesthandling)")
        StateHistoryWasteService.add("wasteserviceState(requestAccepted)")
        StateHistoryWasteService.add("wasteserviceState(pickingUp)")

        //TT
        StateHistoryTransportTrolley.add("transporttrolleyState(waiting)")
        StateHistoryTransportTrolley.add("transporttrolleyState(handlePickupReq)")
        StateHistoryTransportTrolley.add("transporttrolleyState(goPickUp)")
        StateHistoryTransportTrolley.add("transporttrolleyState(pickupDone)")
        StateHistoryTransportTrolley.add("transporttrolleyState(goDeposit)")
        StateHistoryTransportTrolley.add("transporttrolleyState(depositDone)")
        StateHistoryTransportTrolley.add("transporttrolleyState(goHome)")
        StateHistoryTransportTrolley.add("transporttrolleyState(handlePickupReq)")
        StateHistoryTransportTrolley.add("transporttrolleyState(goPickUp)")
        StateHistoryTransportTrolley.add("transporttrolleyState(pickupDone)")

        //CustomPathExecutor
        StateHistoryCustomPathExecutor.add("custompathexecutorState(handleMoveRequest)")
        StateHistoryCustomPathExecutor.add("custompathexecutorState(stepDone(1))")
        StateHistoryCustomPathExecutor.add("custompathexecutorState(pathDone(1))")
        StateHistoryCustomPathExecutor.add("custompathexecutorState(handleMoveRequest)")
        StateHistoryCustomPathExecutor.add("custompathexecutorState(stepDone(2))")
        StateHistoryCustomPathExecutor.add("custompathexecutorState(pathDone(2))")
        StateHistoryCustomPathExecutor.add("custompathexecutorState(handleMoveRequest)")
        StateHistoryCustomPathExecutor.add("custompathexecutorState(stepDone(1))")
        StateHistoryCustomPathExecutor.add("custompathexecutorState(handleMoveRequest)")
        StateHistoryCustomPathExecutor.add("custompathexecutorState(pathDone(1))")
    }

    @Test
    @Throws(InterruptedException::class)
    fun testDoubleRequest() {
        CommUtils.delay(5000)
        var asw = ""

        val depositRequest = "msg(depositRequest, request, testunit, wasteservice, depositRequest($material, $weight),1)"
        println("Testapplication	|	testaccept on message: $depositRequest")
        try {
            asw = conn.request(depositRequest)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        assertTrue(asw.contains("loadaccept"))

        println("Testapplication	|	testStateHistory on message: $depositRequest N2")
        //TODO. La seconda rixhiesta deve arrivare mentr eil trolley sta andando in home
        println("Testapplication	|	Delay 15000.......")
        CommUtils.delay(18000)
        println("Testapplication	|	.......finish")
        try {
            asw = conn.request(depositRequest)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        assertTrue(asw.contains("loadaccept"))
        println("Testapplication	|	FINISHED")

        println("WasteService StateHistory: ${obsWasteService.getStateHistory()}")
        println("TransportTrolley StateHistory: ${obsTransporttrolley.getStateHistory()}")
        println("CustomPathExecutor StateHistory: ${obsCustompathexecutor.getStateHistory()}")

        assertEquals(StateHistoryWasteService, obsWasteService.getStateHistory())
        assertEquals(StateHistoryTransportTrolley, obsTransporttrolley.getStateHistory())
        assertEquals(StateHistoryCustomPathExecutor, obsCustompathexecutor.getStateHistory())
        CommUtils.delay(500)
    }

    @Test
    fun testRejected() {
        CommUtils.delay(3000)
        val prevState =obsWasteService.currState
        val depositRequest = "msg(depositRequest, request, testunit, wasteservice, depositRequest($material, 2000),1)"
        println("Testwasteservice	|	testrejected on message: $depositRequest")
        var asw = ""
        try {
            asw = conn.request(depositRequest)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        assertTrue(asw.contains("loadrejecetd"))
    }


}