/* Generated by AN DISI Unibo */ 
package it.unibo.wasteservicestatusgui

import it.unibo.kactor.*
import alice.tuprolog.*
import unibo.basicomm23.*
import unibo.basicomm23.interfaces.*
import unibo.basicomm23.utils.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
	
class Wasteservicestatusgui ( name: String, scope: CoroutineScope  ) : ActorBasicFsm( name, scope ){

	override fun getInitialState() : String{
		return "s0"
	}
	override fun getBody() : (ActorBasicFsm.() -> Unit){
		val interruptedStateTransitions = mutableListOf<Transition>()
		
				val name = "WasteServiceGui"
				val version = "V1.0"
				val active = true
				
				var TTState = "null"
				var pos = "null"
				var Pb = "null"
				var Gb = "null"
				var ledState = "null"
		return { //this:ActionBasciFsm
				state("s0") { //this:State
					action { //it:State
						CommUtils.outblack("	 $name: Started! $version")
						CommUtils.outblack("	 $name: Ready to observe and update")
						//genTimer( actor, state )
					}
					//After Lenzi Aug2002
					sysaction { //it:State
					}	 	 
					 transition( edgeName="goto",targetState="handleChanges", cond=doswitchGuarded({ active  
					}) )
					transition( edgeName="goto",targetState="deactivated", cond=doswitchGuarded({! ( active  
					) }) )
				}	 
				state("handleChanges") { //this:State
					action { //it:State
						discardMessages = false
						CommUtils.outblack("	 $name: GUI STATE:")
						CommUtils.outblack("			 TTState: $TTState")
						CommUtils.outblack("			 Pos: $pos")
						CommUtils.outblack("			 PB: $Pb - GB: $Gb")
						CommUtils.outblack("			 LedState: $ledState")
						//genTimer( actor, state )
					}
					//After Lenzi Aug2002
					sysaction { //it:State
				 	 		stateTimer = TimerActor("timer_handleChanges", 
				 	 					  scope, context!!, "local_tout_wasteservicestatusgui_handleChanges", 20000.toLong() )
					}	 	 
					 transition(edgeName="t08",targetState="handleChanges",cond=whenTimeout("local_tout_wasteservicestatusgui_handleChanges"))   
				}	 
				state("deactivated") { //this:State
					action { //it:State
						delay(5000) 
						//genTimer( actor, state )
					}
					//After Lenzi Aug2002
					sysaction { //it:State
					}	 	 
					 transition( edgeName="goto",targetState="handleChanges", cond=doswitchGuarded({ active  
					}) )
					transition( edgeName="goto",targetState="deactivated", cond=doswitchGuarded({! ( active  
					) }) )
				}	 
			}
		}
}
