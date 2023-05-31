/* Generated by AN DISI Unibo */ 
package it.unibo.transporttrolley

import it.unibo.kactor.*
import alice.tuprolog.*
import unibo.basicomm23.*
import unibo.basicomm23.interfaces.*
import unibo.basicomm23.utils.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
	
class Transporttrolley ( name: String, scope: CoroutineScope  ) : ActorBasicFsm( name, scope ){

	override fun getInitialState() : String{
		return "s0"
	}
	override fun getBody() : (ActorBasicFsm.() -> Unit){
		val interruptedStateTransitions = mutableListOf<Transition>()
		
				val name = "TransportTrolley"
				val version = "V1.0"
				
				var ID = ""
				var materialType = ""
				var pos = "" 		//pos : home, indoor, plasticbox, glassbox
		return { //this:ActionBasciFsm
				state("s0") { //this:State
					action { //it:State
						CommUtils.outblue("	 $name: Started! $version")
						//genTimer( actor, state )
					}
					//After Lenzi Aug2002
					sysaction { //it:State
					}	 	 
					 transition( edgeName="goto",targetState="waiting", cond=doswitch() )
				}	 
				state("waiting") { //this:State
					action { //it:State
						 pos = "home"  
						updateResourceRep( pos  
						)
						CommUtils.outblue("	 $name: TransportTrolley at Home!")
						CommUtils.outblue("	 $name: ready and waiting for pickupRequest!")
						//genTimer( actor, state )
					}
					//After Lenzi Aug2002
					sysaction { //it:State
					}	 	 
				}	 
				state("pickup") { //this:State
					action { //it:State
						CommUtils.outblue("	 $name: pickupRequest($ID) received!")
						CommUtils.outblue("	 $name: Sending cmd(MOVES) to BasicRobot22")
						CommUtils.outblue("	 $name: Robot going from $pos to Indoor")
						delay(10000) 
						//genTimer( actor, state )
					}
					//After Lenzi Aug2002
					sysaction { //it:State
					}	 	 
					 transition( edgeName="goto",targetState="depositPlastic", cond=doswitchGuarded({ materialType == "plastic"  
					}) )
					transition( edgeName="goto",targetState="depositGlass", cond=doswitchGuarded({! ( materialType == "plastic"  
					) }) )
				}	 
				state("depositPlastic") { //this:State
					action { //it:State
						CommUtils.outblue("	 $name: Going to PlasticBox!")
						delay(3000) 
						 pos = "plasticbox"  
						updateResourceRep( pos  
						)
						CommUtils.outblue("	 $name: Depositing Plastic!")
						delay(6000) 
						//genTimer( actor, state )
					}
					//After Lenzi Aug2002
					sysaction { //it:State
				 	 		stateTimer = TimerActor("timer_depositPlastic", 
				 	 					  scope, context!!, "local_tout_transporttrolley_depositPlastic", 500.toLong() )
					}	 	 
					 transition(edgeName="t10",targetState="goHome",cond=whenTimeout("local_tout_transporttrolley_depositPlastic"))   
				}	 
				state("depositGlass") { //this:State
					action { //it:State
						CommUtils.outblue("	 $name: Going to GlassBox!")
						delay(5000) 
						 pos = "glassbox"  
						updateResourceRep( pos  
						)
						CommUtils.outblue("	 $name: Depositing Glass!")
						delay(6000) 
						//genTimer( actor, state )
					}
					//After Lenzi Aug2002
					sysaction { //it:State
				 	 		stateTimer = TimerActor("timer_depositGlass", 
				 	 					  scope, context!!, "local_tout_transporttrolley_depositGlass", 500.toLong() )
					}	 	 
					 transition(edgeName="t21",targetState="goHome",cond=whenTimeout("local_tout_transporttrolley_depositGlass"))   
				}	 
				state("goHome") { //this:State
					action { //it:State
						CommUtils.outblue("	 $name: Finished Deposit - Going home")
						delay(7000) 
						//genTimer( actor, state )
					}
					//After Lenzi Aug2002
					sysaction { //it:State
					}	 	 
					 transition( edgeName="goto",targetState="waiting", cond=doswitch() )
				}	 
			}
		}
}
