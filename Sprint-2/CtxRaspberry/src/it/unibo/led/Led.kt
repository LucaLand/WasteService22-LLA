/* Generated by AN DISI Unibo */ 
package it.unibo.led

import it.unibo.kactor.*
import alice.tuprolog.*
import unibo.basicomm23.*
import unibo.basicomm23.interfaces.*
import unibo.basicomm23.utils.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
	
class Led ( name: String, scope: CoroutineScope  ) : ActorBasicFsm( name, scope ){

	override fun getInitialState() : String{
		return "s0"
	}
	override fun getBody() : (ActorBasicFsm.() -> Unit){
		val interruptedStateTransitions = mutableListOf<Transition>()
		
				val name = "LedActor"
				val version = "V1.0"
		
				var ledState = "LedOff"
				var robotState = "athome"
				
				val runtime = Runtime.getRuntime()
				
		return { //this:ActionBasciFsm
				state("s0") { //this:State
					action { //it:State
						CommUtils.outred("	 $name: Started! $version")
						//genTimer( actor, state )
					}
					//After Lenzi Aug2002
					sysaction { //it:State
					}	 	 
					 transition( edgeName="goto",targetState="handleRobotStateEvent", cond=doswitch() )
				}	 
				state("handleRobotStateEvent") { //this:State
					action { //it:State
						if( checkMsgContent( Term.createTerm("robotStateEvent(STATE)"), Term.createTerm("robotStateEvent(STATE)"), 
						                        currentMsg.msgContent()) ) { //set msgArgList
								CommUtils.outred("	 $name: Handling RobotState change!")
								 robotState = payloadArg(0)  
								if(  robotState == "athome"  
								 ){ ledState = "LedOff"  
								}
								if(  robotState == "moving"  
								 ){ ledState = "LedBlink"  
								}
								if(  robotState == "stopped"  
								 ){ ledState = "LedOn"  
								}
								CommUtils.outred("	 $name: Led state - $ledState")
						}
						if(  ledState == "LedOff"  
						 ){ runtime.exec("sudo bash led25GpioTurnOff.sh")  
						}
						if(  ledState == "LedBlink"  
						 ){ runtime.exec("sudo bash led25GpioTurnOn.sh")  
						delay(100) 
						 runtime.exec("sudo bash led25GpioTurnOff.sh")  
						}
						if(  ledState == "LedOn"  
						 ){ runtime.exec("sudo bash led25GpioTurnOn.sh")  
						}
						//genTimer( actor, state )
					}
					//After Lenzi Aug2002
					sysaction { //it:State
				 	 		stateTimer = TimerActor("timer_handleRobotStateEvent", 
				 	 					  scope, context!!, "local_tout_led_handleRobotStateEvent", 100.toLong() )
					}	 	 
					 transition(edgeName="t00",targetState="handleRobotStateEvent",cond=whenTimeout("local_tout_led_handleRobotStateEvent"))   
					transition(edgeName="t01",targetState="handleRobotStateEvent",cond=whenEvent("robotStateEvent"))
				}	 
			}
		}
}