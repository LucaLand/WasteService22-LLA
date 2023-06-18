/* Generated by AN DISI Unibo */ 
package it.unibo.sonar23

import it.unibo.kactor.*
import alice.tuprolog.*
import unibo.basicomm23.*
import unibo.basicomm23.interfaces.*
import unibo.basicomm23.utils.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
	
class Sonar23 ( name: String, scope: CoroutineScope  ) : ActorBasicFsm( name, scope ){

	override fun getInitialState() : String{
		return "s0"
	}
	override fun getBody() : (ActorBasicFsm.() -> Unit){
		val interruptedStateTransitions = mutableListOf<Transition>()
		 var  ApplAlso = sysUtil.getActor("appl") != null  
		return { //this:ActionBasciFsm
				state("s0") { //this:State
					action { //it:State
						CommUtils.outblack("sonar23 | start with appl: $ApplAlso")
						 subscribeToLocalActor("distancefilter").subscribeToLocalActor("datacleaner").subscribeToLocalActor("sonar")  
						//genTimer( actor, state )
					}
					//After Lenzi Aug2002
					sysaction { //it:State
					}	 	 
					 transition( edgeName="goto",targetState="work", cond=doswitch() )
				}	 
				state("work") { //this:State
					action { //it:State
						updateResourceRep( "sonar23 waiting ..."  
						)
						//genTimer( actor, state )
					}
					//After Lenzi Aug2002
					sysaction { //it:State
					}	 	 
					 transition(edgeName="t02",targetState="handlesonardata",cond=whenEvent("sonardata"))
					transition(edgeName="t03",targetState="handleobstacle",cond=whenEvent("obstacle"))
				}	 
				state("handlesonardata") { //this:State
					action { //it:State
						CommUtils.outcyan("$name in ${currentState.stateName} | $currentMsg | ${Thread.currentThread().getName()} n=${Thread.activeCount()}")
						 	   
						updateResourceRep( "sonar23 handles $currentMsg"  
						)
						if( checkMsgContent( Term.createTerm("distance(D)"), Term.createTerm("distance(D)"), 
						                        currentMsg.msgContent()) ) { //set msgArgList
								 val D = payloadArg(0)  
								emit("sonardataAppl", "distance($D)" ) 
						}
						//genTimer( actor, state )
					}
					//After Lenzi Aug2002
					sysaction { //it:State
					}	 	 
					 transition( edgeName="goto",targetState="work", cond=doswitch() )
				}	 
				state("handleobstacle") { //this:State
					action { //it:State
						if( checkMsgContent( Term.createTerm("obstacle(D)"), Term.createTerm("obstacle(D)"), 
						                        currentMsg.msgContent()) ) { //set msgArgList
								CommUtils.outmagenta("$name handleobstacle ALARM ${payloadArg(0)}")
								emit("alarm", "alarm(obstacle)" ) 
						}
						//genTimer( actor, state )
					}
					//After Lenzi Aug2002
					sysaction { //it:State
					}	 	 
					 transition( edgeName="goto",targetState="sonar23", cond=doswitchGuarded({ ApplAlso == true  
					}) )
					transition( edgeName="goto",targetState="work", cond=doswitchGuarded({! ( ApplAlso == true  
					) }) )
				}	 
				state("sonar23") { //this:State
					action { //it:State
						CommUtils.outblack("$name BYE")
						//genTimer( actor, state )
					}
					//After Lenzi Aug2002
					sysaction { //it:State
					}	 	 
				}	 
			}
		}
}
