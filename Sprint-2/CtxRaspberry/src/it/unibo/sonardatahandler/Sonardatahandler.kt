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
		return { //this:ActionBasciFsm
				state("s0") { //this:State
					action { //it:State
						//genTimer( actor, state )
					}
					//After Lenzi Aug2002
					sysaction { //it:State
					}	 	 
					 transition( edgeName="goto",targetState="handleSonarData", cond=doswitch() )
				}	 
				state("handleSonarData") { //this:State
					action { //it:State
						if( checkMsgContent( Term.createTerm("distance(V)"), Term.createTerm("distance(V)"), 
						                        currentMsg.msgContent()) ) { //set msgArgList
								 val distance = payloadArg(0)  
								if(  distance.toInt() <= DLIMIT && stopped == false  
								 ){forward("alarm", "alarm(stop)" ,"transporttrolley" ) 
								CommUtils.outblack("	 $name: Sending toggleStop(stop)!")
								}
								if(  distance.toInt() > DLIMIT && stopped == true  
								 ){forward("alarmStop", "alarmStop(resume)" ,"transporttrolley" ) 
								CommUtils.outblack("	 $name: Sending toggleStop(resume)!")
								}
								CommUtils.outblack("	 $name: Handling SonarData($distance) - Robot Stopped: $stopped")
						}
						//genTimer( actor, state )
					}
					//After Lenzi Aug2002
					sysaction { //it:State
					}	 	 
					 transition(edgeName="t07",targetState="handleSonarData",cond=whenEvent("sonardata"))
				}	 
			}
		}
}