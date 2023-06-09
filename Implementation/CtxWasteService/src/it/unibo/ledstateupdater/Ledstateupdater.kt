/* Generated by AN DISI Unibo */ 
package it.unibo.ledstateupdater

import it.unibo.kactor.*
import alice.tuprolog.*
import unibo.basicomm23.*
import unibo.basicomm23.interfaces.*
import unibo.basicomm23.utils.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
	
class Ledstateupdater ( name: String, scope: CoroutineScope  ) : ActorBasicFsm( name, scope ){

	override fun getInitialState() : String{
		return "s0"
	}
	override fun getBody() : (ActorBasicFsm.() -> Unit){
		val interruptedStateTransitions = mutableListOf<Transition>()
		 
				var Value = ""
				var robotState = ""
		return { //this:ActionBasciFsm
				state("s0") { //this:State
					action { //it:State
						updateResourceRep( "started"  
						)
						CoapObserverSupport(myself, "localhost","8072","ctxwasteservice","transporttrolley")
						//genTimer( actor, state )
					}
					//After Lenzi Aug2002
					sysaction { //it:State
					}	 	 
					 transition( edgeName="goto",targetState="handleRobotStateUpdate", cond=doswitch() )
				}	 
				state("handleRobotStateUpdate") { //this:State
					action { //it:State
						if( checkMsgContent( Term.createTerm("coapUpdate(RESOURCE,VALUE)"), Term.createTerm("coapUpdate(RESOURCE,VALUE)"), 
						                        currentMsg.msgContent()) ) { //set msgArgList
								
												if(payloadArg(1).contains("robotState")){
													Value = payloadArg(1).split("(")[1]
													robotState = Value.dropLast(1)
												}
								CommUtils.outred("	 $name: Handling RobotState change!")
								 var LedState = "null"  
								if(  robotState == "athome"  
								 ){ LedState = "LedOff"  
								}
								if(  robotState == "moving"  
								 ){ LedState = "LedBlink"  
								}
								if(  robotState == "stopped"  
								 ){ LedState = "LedOn"  
								}
								forward("ledStateUpdate", "state($LedState)" ,"led" ) 
								CommUtils.outred("	 $name: Sending ledState: $LedState")
								updateResourceRep( "ledState($LedState)"  
								)
						}
						//genTimer( actor, state )
					}
					//After Lenzi Aug2002
					sysaction { //it:State
					}	 	 
					 transition(edgeName="t025",targetState="handleRobotStateUpdate",cond=whenDispatch("coapUpdate"))
				}	 
			}
		}
}
