/* Generated by AN DISI Unibo */ 
package it.unibo.custompathexecutor

import it.unibo.kactor.*
import alice.tuprolog.*
import unibo.basicomm23.*
import unibo.basicomm23.interfaces.*
import unibo.basicomm23.utils.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
	
class Custompathexecutor ( name: String, scope: CoroutineScope  ) : ActorBasicFsm( name, scope ){

	override fun getInitialState() : String{
		return "s0"
	}
	override fun getBody() : (ActorBasicFsm.() -> Unit){
		val interruptedStateTransitions = mutableListOf<Transition>()
		 
				val name = "CustomPathExecutor"
				val version = "1"
				
				
				var Pos = ""
				var NewPos = ""
		
				var pathToDo = ""
				var pathStep = 0
				var pathLenght = -1
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
						CommUtils.outblack("	 $name: waiting for move requests!")
						//genTimer( actor, state )
					}
					//After Lenzi Aug2002
					sysaction { //it:State
					}	 	 
					 transition(edgeName="t08",targetState="handleMoveRequest",cond=whenRequest("move"))
				}	 
				state("handleMoveRequest") { //this:State
					action { //it:State
						if( checkMsgContent( Term.createTerm("move(CurrentPos,NewPos)"), Term.createTerm("move(Pos,NewPos)"), 
						                        currentMsg.msgContent()) ) { //set msgArgList
								 
												Pos = payloadArg(0)
												NewPos = payloadArg(1)
												pathStep = 0
								CommUtils.outblack("	 $name: doing move($Pos, $NewPos)")
								if(  Pos == "home"  && NewPos == "indoor"  
								 ){ pathToDo = "wl"  
								}
								if(  Pos == "indoor"  && NewPos == "plasticbox"  
								 ){ pathToDo = "wl"  
								}
								if(  Pos == "indoor"  && NewPos == "glassbox"  
								 ){ pathToDo = "wlwl"  
								}
								if(  Pos == "plasticbox"  && NewPos == "home"  
								 ){ pathToDo = "wlwl"  
								}
								if(  Pos == "glassbox"  && NewPos == "home"  
								 ){ pathToDo = "wl"  
								}
								if(  Pos == "glassbox"  && NewPos == "indoor"  
								 ){ pathToDo = "wl"  
								}
								if(  Pos == "plasticbox"  && NewPos == "indoor"  
								 ){ pathToDo = "wl"  
								}
								if(  NewPos == "indoor" && Pos == "platicbox"  
								 ){ pathToDo = "lwll"  
								}
								if(  NewPos == "indoor" && Pos == "glassbox"  
								 ){ pathToDo = "lwrwll"  
								}
								 pathLenght = pathToDo.lastIndex  
						}
						//genTimer( actor, state )
					}
					//After Lenzi Aug2002
					sysaction { //it:State
					}	 	 
					 transition( edgeName="goto",targetState="working", cond=doswitch() )
				}	 
				state("working") { //this:State
					action { //it:State
						if( checkMsgContent( Term.createTerm("coapUpdate(RESOURCE,VALUE)"), Term.createTerm("coapUpdate(RESOURCE,VALUE)"), 
						                        currentMsg.msgContent()) ) { //set msgArgList
								
												val Resource = payloadArg(0)
												val Value = payloadArg(1)
												if(Value.contains("obstacle"))
													pathStep++
								CommUtils.outblack("	 $name: coapUpdate($Resource, $Value)")
						}
						if(  pathStep <= pathLenght  
						 ){
										val CMD = pathToDo.get(pathStep)
						CommUtils.outblack("	 $name: Forwarding to BasicRobot cmd($CMD)")
						if(  CMD == 'w'  
						 ){forward("cmd", "cmd($CMD)" ,"basicrobot" ) 
						}
						if(  CMD == 'l' || CMD == 'r'  
						 ){forward("cmd", "cmd($CMD)" ,"basicrobot" ) 
						delay(1000) 
						 pathStep++  
						}
						}
						//genTimer( actor, state )
					}
					//After Lenzi Aug2002
					sysaction { //it:State
				 	 		stateTimer = TimerActor("timer_working", 
				 	 					  scope, context!!, "local_tout_custompathexecutor_working", 2000.toLong() )
					}	 	 
					 transition(edgeName="t19",targetState="working",cond=whenTimeout("local_tout_custompathexecutor_working"))   
					transition(edgeName="t110",targetState="working",cond=whenDispatchGuarded("coapUpdate",{ pathStep <= pathLenght  
					}))
					transition(edgeName="t111",targetState="pathDone",cond=whenDispatchGuarded("coapUpdate",{ pathStep > pathLenght  
					}))
					interrupthandle(edgeName="t112",targetState="stop",cond=whenDispatch("stop"),interruptedStateTransitions)
				}	 
				state("pathDone") { //this:State
					action { //it:State
						CommUtils.outblack("	 $name: Path done in $NewPos")
						answer("move", "moveDone", "moveDone($NewPos)","transporttrolley"   )  
						//genTimer( actor, state )
					}
					//After Lenzi Aug2002
					sysaction { //it:State
					}	 	 
					 transition( edgeName="goto",targetState="waiting", cond=doswitch() )
				}	 
				state("stop") { //this:State
					action { //it:State
						if( checkMsgContent( Term.createTerm("goBack(ARG)"), Term.createTerm("goBack(ARG)"), 
						                        currentMsg.msgContent()) ) { //set msgArgList
								 pathToDo = "llwr" 
						}
						//genTimer( actor, state )
					}
					//After Lenzi Aug2002
					sysaction { //it:State
					}	 	 
				}	 
			}
		}
}
