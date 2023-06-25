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
				
				
				var Pos = 0
				var PosToGo = 0
				
		//		enum class positions(val v : String) = {home(0), indoor(1), plasticbox(2), glassbox(3)}
				
		//		enum class routes(val v : Int) = {
		//			HI(1),
		//			IP(1),
		//			IG(2),
		//			PI(-1),
		//			GI(-2),
		//			PH(2),
		//			GH(1)
		//		}
				
				var stepToDo = 0
				var pathToDo = ""
				var pathStep = 0
				var pathLenght = -1
				var turnBack = false
				
				val IncPosPath = "wl"
				val DecPosPath = "lwrr"
				val goBackPath = "hllwrr"
				val goBackPath2 = "hllwrrr" 
		return { //this:ActionBasciFsm
				state("s0") { //this:State
					action { //it:State
						CommUtils.outblue("	 $name: Started! $version")
						CoapObserverSupport(myself, "192.168.1.7","8020","ctxbasicrobot","basicrobot")
						updateResourceRep( "started"  
						)
						updateResourceRep( "robotPos($Pos)"  
						)
						//genTimer( actor, state )
					}
					//After Lenzi Aug2002
					sysaction { //it:State
					}	 	 
					 transition( edgeName="goto",targetState="waiting", cond=doswitch() )
				}	 
				state("waiting") { //this:State
					action { //it:State
						//genTimer( actor, state )
					}
					//After Lenzi Aug2002
					sysaction { //it:State
					}	 	 
					 transition(edgeName="t016",targetState="handleMoveRequest",cond=whenRequest("move"))
				}	 
				state("handleMoveRequest") { //this:State
					action { //it:State
						if( checkMsgContent( Term.createTerm("move(CurrentPos,NewPos)"), Term.createTerm("move(Pos,NewPos)"), 
						                        currentMsg.msgContent()) ) { //set msgArgList
								
												val NewPos = payloadArg(1)
												pathStep = 0
								if(  NewPos == "home"  
								 ){ PosToGo = 0  
								}
								if(  NewPos == "indoor"  
								 ){ PosToGo = 1  
								}
								if(  NewPos == "plasticbox"  
								 ){ PosToGo = 2  
								}
								if(  NewPos == "glassbox"  
								 ){ PosToGo = 3  
								}
								CommUtils.outblack("	 $name: doing move($Pos, $NewPos)")
						}
						
									stepToDo = PosToGo - Pos
									if(stepToDo <= -3 ){
										stepToDo += 4
									}else if(stepToDo >= 3){
										stepToDo += -4
									}
						CommUtils.outblack("	 $name: stepToDo=$stepToDo")
						//genTimer( actor, state )
					}
					//After Lenzi Aug2002
					sysaction { //it:State
					}	 	 
					 transition( edgeName="goto",targetState="working", cond=doswitch() )
				}	 
				state("working") { //this:State
					action { //it:State
						 pathStep = 0
									var backPath = ""
						if(  stepToDo > 0  
						 ){ pathToDo = IncPosPath
										backPath = goBackPath
						}
						if(  stepToDo < 0  
						 ){ pathToDo = DecPosPath
										backPath = goBackPath2
						}
						if(  turnBack  
						 ){ pathToDo = backPath.plus(pathToDo)  
						}
						 pathLenght = pathToDo.lastIndex  
						//genTimer( actor, state )
					}
					//After Lenzi Aug2002
					sysaction { //it:State
					}	 	 
					 transition( edgeName="goto",targetState="execStep", cond=doswitchGuarded({ stepToDo != 0  
					}) )
					transition( edgeName="goto",targetState="pathDone", cond=doswitchGuarded({! ( stepToDo != 0  
					) }) )
				}	 
				state("execStep") { //this:State
					action { //it:State
						 var skip = false  
						if( checkMsgContent( Term.createTerm("coapUpdate(RESOURCE,VALUE)"), Term.createTerm("coapUpdate(RESOURCE,VALUE)"), 
						                        currentMsg.msgContent()) ) { //set msgArgList
								
												val Resource = payloadArg(0)
												val Value = payloadArg(1)
												if(Value.contains("obstacle") && pathToDo.get(pathStep) == 'w'){
													pathStep++
												}
												if(Value == "moveactivated(w)"){
													skip = true
												}
						}
						if(  !skip  
						 ){if(  pathStep <= pathLenght  
						 ){
											val CMD = pathToDo.get(pathStep)
						if(  CMD == 'w'  
						 ){forward("cmd", "cmd($CMD)" ,"basicrobot" ) 
						}
						if(  CMD == 'l' || CMD == 'r' || CMD == 'h'  
						 ){forward("cmd", "cmd($CMD)" ,"basicrobot" ) 
						delay(1000) 
						 pathStep++  
						}
						}
						else
						 {forward("coapUpdate", "coapUpdate(custompathexecutor,stepDone)" ,"custompathexecutor" ) 
						 }
						}
						//genTimer( actor, state )
					}
					//After Lenzi Aug2002
					sysaction { //it:State
				 	 		stateTimer = TimerActor("timer_execStep", 
				 	 					  scope, context!!, "local_tout_custompathexecutor_execStep", 1200.toLong() )
					}	 	 
					 transition(edgeName="t117",targetState="execStep",cond=whenTimeout("local_tout_custompathexecutor_execStep"))   
					transition(edgeName="t118",targetState="execStep",cond=whenDispatchGuarded("coapUpdate",{ pathStep <= pathLenght  
					}))
					transition(edgeName="t119",targetState="stepDone",cond=whenDispatchGuarded("coapUpdate",{ pathStep > pathLenght  
					}))
					interrupthandle(edgeName="t120",targetState="stopped",cond=whenDispatch("toggleStop"),interruptedStateTransitions)
				}	 
				state("stepDone") { //this:State
					action { //it:State
						if( checkMsgContent( Term.createTerm("coapUpdate(RESOURCE,VALUE)"), Term.createTerm("coapUpdate(RESOURCE,VALUE)"), 
						                        currentMsg.msgContent()) ) { //set msgArgList
								if(  stepToDo > 0 && !turnBack  
								 ){
													stepToDo--
													Pos = (Pos+1)%4
								}
								if(  stepToDo < 0  && !turnBack  
								 ){
													stepToDo++
													Pos = (Pos-1)%4
								}
								if(  turnBack  
								 ){ turnBack = false  
								CommUtils.outblack("	 $name: Turned Back! - Pos: $Pos")
								}
								CommUtils.outblack("	 $name: StepDone! - Pos: $Pos")
								updateResourceRep( "robotPos($Pos)"  
								)
						}
						//genTimer( actor, state )
					}
					//After Lenzi Aug2002
					sysaction { //it:State
				 	 		stateTimer = TimerActor("timer_stepDone", 
				 	 					  scope, context!!, "local_tout_custompathexecutor_stepDone", 100.toLong() )
					}	 	 
					 transition(edgeName="t221",targetState="working",cond=whenTimeout("local_tout_custompathexecutor_stepDone"))   
					transition(edgeName="t222",targetState="handleMoveRequest",cond=whenRequest("move"))
				}	 
				state("pathDone") { //this:State
					action { //it:State
						CommUtils.outblack("	 $name: Path done in $Pos")
						answer("move", "moveDone", "moveDone($Pos)","transporttrolley"   )  
						//genTimer( actor, state )
					}
					//After Lenzi Aug2002
					sysaction { //it:State
					}	 	 
					 transition( edgeName="goto",targetState="waiting", cond=doswitch() )
				}	 
				state("stopped") { //this:State
					action { //it:State
						if( checkMsgContent( Term.createTerm("toggleStop(ARG)"), Term.createTerm("toggleStop(ARG)"), 
						                        currentMsg.msgContent()) ) { //set msgArgList
								forward("cmd", "cmd(h)" ,"basicrobot" ) 
								delay(100) 
								CommUtils.outblack("	 $name: STOPPED!")
						}
						//genTimer( actor, state )
					}
					//After Lenzi Aug2002
					sysaction { //it:State
					}	 	 
					 transition(edgeName="t323",targetState="resume",cond=whenDispatch("toggleStop"))
				}	 
				state("resume") { //this:State
					action { //it:State
						if( checkMsgContent( Term.createTerm("toggleStop(ARG)"), Term.createTerm("toggleStop(ARG)"), 
						                        currentMsg.msgContent()) ) { //set msgArgList
								CommUtils.outblack("	 $name: RESUMED!")
						}
						returnFromInterrupt(interruptedStateTransitions)
						//genTimer( actor, state )
					}
					//After Lenzi Aug2002
					sysaction { //it:State
					}	 	 
				}	 
				state("handleMoveRequestInMovement") { //this:State
					action { //it:State
						 turnBack = true  
						//genTimer( actor, state )
					}
					//After Lenzi Aug2002
					sysaction { //it:State
					}	 	 
					 transition( edgeName="goto",targetState="handleMoveRequest", cond=doswitch() )
				}	 
			}
		}
}
