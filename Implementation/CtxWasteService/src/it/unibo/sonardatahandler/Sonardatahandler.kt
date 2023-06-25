/* Generated by AN DISI Unibo */ 
package it.unibo.sonardatahandler

import it.unibo.kactor.*
import alice.tuprolog.*
import unibo.basicomm23.*
import unibo.basicomm23.interfaces.*
import unibo.basicomm23.utils.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
	
class Sonardatahandler ( name: String, scope: CoroutineScope  ) : ActorBasicFsm( name, scope ){

	override fun getInitialState() : String{
		return "s0"
	}
	override fun getBody() : (ActorBasicFsm.() -> Unit){
		val interruptedStateTransitions = mutableListOf<Transition>()
		
				val DLIMIT = 10
				var stopped = false
				
				var Value = ""
				var distance = ""
		return { //this:ActionBasciFsm
				state("s0") { //this:State
					action { //it:State
						CommUtils.outblack("	 sonardatahandler: 	Started")
						delay(1000) 
						CoapObserverSupport(myself, "192.168.1.22","8076","ctxraspberry","sonar23")
						//genTimer( actor, state )
					}
					//After Lenzi Aug2002
					sysaction { //it:State
					}	 	 
					 transition( edgeName="goto",targetState="handleSonarData", cond=doswitch() )
				}	 
				state("handleSonarData") { //this:State
					action { //it:State
						if( checkMsgContent( Term.createTerm("coapUpdate(RESOURCE,VALUE)"), Term.createTerm("coapUpdate(RESOURCE,VALUE)"), 
						                        currentMsg.msgContent()) ) { //set msgArgList
								
												distance = "null"
												if(payloadArg(1).contains("sonardata")){
													Value = payloadArg(1).split("(")[1]
													distance = Value.dropLast(1)
												}
								if(  distance != "null"  
								 ){if(  distance.toInt() <= DLIMIT && stopped == false  
								 ){ stopped = true  
								forward("alarm", "alarm(stop)" ,"transporttrolley" ) 
								CommUtils.outblack("	 $name: Handling SonarData($distance) - Robot Stopped: $stopped")
								CommUtils.outblack("	 $name: Sending alarm(stop)!")
								}
								if(  distance.toInt() > DLIMIT && stopped == true  
								 ){ stopped = false  
								forward("alarmStop", "alarmStop(resume)" ,"transporttrolley" ) 
								CommUtils.outblack("	 $name: Handling SonarData($distance) - Robot Stopped: $stopped")
								CommUtils.outblack("	 $name: Sending alarmStop(resume)!")
								}
								}
						}
						//genTimer( actor, state )
					}
					//After Lenzi Aug2002
					sysaction { //it:State
					}	 	 
					 transition(edgeName="t024",targetState="handleSonarData",cond=whenDispatch("coapUpdate"))
				}	 
			}
		}
}
