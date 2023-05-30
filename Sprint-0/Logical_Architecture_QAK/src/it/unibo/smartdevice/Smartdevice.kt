/* Generated by AN DISI Unibo */ 
package it.unibo.smartdevice

import it.unibo.kactor.*
import alice.tuprolog.*
import unibo.basicomm23.*
import unibo.basicomm23.interfaces.*
import unibo.basicomm23.utils.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
	
class Smartdevice ( name: String, scope: CoroutineScope  ) : ActorBasicFsm( name, scope ){

	override fun getInitialState() : String{
		return "s0"
	}
	override fun getBody() : (ActorBasicFsm.() -> Unit){
		val interruptedStateTransitions = mutableListOf<Transition>()
		
				val name = "SmartDevice"
				val version = "V1.0"
				
				var request_ID = 0 
		return { //this:ActionBasciFsm
				state("s0") { //this:State
					action { //it:State
						CommUtils.outyellow("	 $name: Started! $version")
						//genTimer( actor, state )
					}
					//After Lenzi Aug2002
					sysaction { //it:State
					}	 	 
					 transition( edgeName="goto",targetState="idle", cond=doswitch() )
				}	 
				state("idle") { //this:State
					action { //it:State
						CommUtils.outyellow("	 $name: Waiting for Truck...")
						 request_ID ++  
						//genTimer( actor, state )
					}
					//After Lenzi Aug2002
					sysaction { //it:State
				 	 		stateTimer = TimerActor("timer_idle", 
				 	 					  scope, context!!, "local_tout_smartdevice_idle", 1000.toLong() )
					}	 	 
					 transition(edgeName="t03",targetState="truckArrived",cond=whenTimeout("local_tout_smartdevice_idle"))   
				}	 
				state("truckArrived") { //this:State
					action { //it:State
						 val ID = request_ID  
						if(  ID.toInt()%3 == 0  
						 ){delay(10000) 
						}
						CommUtils.outyellow("	 $name: Truck Arrived!")
						CommUtils.outyellow("	 $name: Sending request -$request_ID-")
						if(  ID.toInt()%2 == 0  
						 ){request("depositRequest", "depositRequest($ID,plastic,10)" ,"wasteservice" )  
						}
						else
						 {request("depositRequest", "depositRequest($ID,glass,8)" ,"wasteservice" )  
						 }
						//genTimer( actor, state )
					}
					//After Lenzi Aug2002
					sysaction { //it:State
				 	 		stateTimer = TimerActor("timer_truckArrived", 
				 	 					  scope, context!!, "local_tout_smartdevice_truckArrived", 20000.toLong() )
					}	 	 
					 transition(edgeName="t14",targetState="truckArrived",cond=whenTimeout("local_tout_smartdevice_truckArrived"))   
					transition(edgeName="t15",targetState="truckGoAway",cond=whenReply("loadaccept"))
					transition(edgeName="t16",targetState="truckGoAway",cond=whenReply("loadrejecetd"))
				}	 
				state("truckGoAway") { //this:State
					action { //it:State
						if( checkMsgContent( Term.createTerm("loadrejecetd(ID)"), Term.createTerm("loadrejecetd(ID)"), 
						                        currentMsg.msgContent()) ) { //set msgArgList
								CommUtils.outyellow("	 $name: Carico - ${payloadArg(0)} - Rifiutato!")
						}
						if( checkMsgContent( Term.createTerm("loadaccept(ID)"), Term.createTerm("loadaccept(ID)"), 
						                        currentMsg.msgContent()) ) { //set msgArgList
								CommUtils.outyellow("	 $name: Scarico - ${payloadArg(0)} - Completato!")
						}
						CommUtils.outyellow("	 $name: Il Truck può andare!")
						//genTimer( actor, state )
					}
					//After Lenzi Aug2002
					sysaction { //it:State
				 	 		stateTimer = TimerActor("timer_truckGoAway", 
				 	 					  scope, context!!, "local_tout_smartdevice_truckGoAway", 1000.toLong() )
					}	 	 
					 transition(edgeName="t37",targetState="idle",cond=whenTimeout("local_tout_smartdevice_truckGoAway"))   
				}	 
			}
		}
}
