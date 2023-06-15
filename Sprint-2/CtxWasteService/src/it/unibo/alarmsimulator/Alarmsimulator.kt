/* Generated by AN DISI Unibo */ 
package it.unibo.alarmsimulator

import it.unibo.kactor.*
import alice.tuprolog.*
import unibo.basicomm23.*
import unibo.basicomm23.interfaces.*
import unibo.basicomm23.utils.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
	
class Alarmsimulator ( name: String, scope: CoroutineScope  ) : ActorBasicFsm( name, scope ){

	override fun getInitialState() : String{
		return "s0"
	}
	override fun getBody() : (ActorBasicFsm.() -> Unit){
		val interruptedStateTransitions = mutableListOf<Transition>()
		return { //this:ActionBasciFsm
				state("s0") { //this:State
					action { //it:State
						delay(10000) 
						CommUtils.outred("AlarmSimulated: ALARM - Stopping TransportTrolley!")
						forward("alarm", "alarm(stop)" ,"transporttrolley" ) 
						//genTimer( actor, state )
					}
					//After Lenzi Aug2002
					sysaction { //it:State
				 	 		stateTimer = TimerActor("timer_s0", 
				 	 					  scope, context!!, "local_tout_alarmsimulator_s0", 8000.toLong() )
					}	 	 
					 transition(edgeName="t025",targetState="s1",cond=whenTimeout("local_tout_alarmsimulator_s0"))   
				}	 
				state("s1") { //this:State
					action { //it:State
						delay(3000) 
						CommUtils.outred("AlarmSimulated: alarm stopped - Resuming TransportTrolley!")
						forward("alarmStop", "alarmStop(resume)" ,"transporttrolley" ) 
						//genTimer( actor, state )
					}
					//After Lenzi Aug2002
					sysaction { //it:State
				 	 		stateTimer = TimerActor("timer_s1", 
				 	 					  scope, context!!, "local_tout_alarmsimulator_s1", 12000.toLong() )
					}	 	 
					 transition(edgeName="t026",targetState="s0",cond=whenTimeout("local_tout_alarmsimulator_s1"))   
				}	 
			}
		}
}
