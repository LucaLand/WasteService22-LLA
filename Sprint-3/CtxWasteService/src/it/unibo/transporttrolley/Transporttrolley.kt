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
				var MaterialType = ""
				var Pos = "" 		//Pos : home, indoor, plasticbox, glassbox
				
				var RobotState = "athome"
				var PreviousState = ""
		return { //this:ActionBasciFsm
				state("s0") { //this:State
					action { //it:State
						CommUtils.outblue("	 $name: Started! $version")
						updateResourceRep( "started"  
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
						 Pos = "home"  
						 RobotState = "athome"  
						updateResourceRep( "robotState($RobotState)"  
						)
						CommUtils.outblue("	 $name: TransportTrolley at Home!")
						CommUtils.outblue("	 $name: ready and waiting for pickupRequest!")
						//genTimer( actor, state )
					}
					//After Lenzi Aug2002
					sysaction { //it:State
					}	 	 
					 transition(edgeName="t02",targetState="handlePickupReq",cond=whenRequest("pickupReq"))
				}	 
				state("handlePickupReq") { //this:State
					action { //it:State
						 MaterialType =  "null"  
						if( checkMsgContent( Term.createTerm("pickupReq(ID,TYPE)"), Term.createTerm("pickupReq(ID,TYPE)"), 
						                        currentMsg.msgContent()) ) { //set msgArgList
								 
								            	ID = payloadArg(0)
								            	MaterialType = payloadArg(1) 
								CommUtils.outblue("	 $name: pickupRequest received! ($ID, $MaterialType)")
						}
						//genTimer( actor, state )
					}
					//After Lenzi Aug2002
					sysaction { //it:State
					}	 	 
					 transition( edgeName="goto",targetState="waiting", cond=doswitchGuarded({ MaterialType == "null"  
					}) )
					transition( edgeName="goto",targetState="goPickUp", cond=doswitchGuarded({! ( MaterialType == "null"  
					) }) )
				}	 
				state("goPickUp") { //this:State
					action { //it:State
						 RobotState = "moving"  
						updateResourceRep( "robotState($RobotState)"  
						)
						CommUtils.outblue("	 $name: Going to Indoor!")
						CommUtils.outblue("	 $name: Robot going from $Pos to Indoor")
						request("move", "move($Pos,indoor)" ,"custompathexecutor" )  
						//genTimer( actor, state )
					}
					//After Lenzi Aug2002
					sysaction { //it:State
					}	 	 
					 transition(edgeName="t13",targetState="pickupDone",cond=whenReply("moveDone"))
					interrupthandle(edgeName="t14",targetState="handleAlarm",cond=whenDispatch("alarm"),interruptedStateTransitions)
				}	 
				state("pickupDone") { //this:State
					action { //it:State
						 Pos = "indoor"  
						CommUtils.outblue("	 $name: Picking up...")
						delay(5000) 
						CommUtils.outblue("	 $name: Pickup Finished!")
						answer("pickupReq", "pickupOk", "pickupOk(0)","wasteservice"   )  
						//genTimer( actor, state )
					}
					//After Lenzi Aug2002
					sysaction { //it:State
				 	 		stateTimer = TimerActor("timer_pickupDone", 
				 	 					  scope, context!!, "local_tout_transporttrolley_pickupDone", 100.toLong() )
					}	 	 
					 transition(edgeName="t25",targetState="goDeposit",cond=whenTimeout("local_tout_transporttrolley_pickupDone"))   
					interrupthandle(edgeName="t26",targetState="handleAlarm",cond=whenDispatch("alarm"),interruptedStateTransitions)
				}	 
				state("goDeposit") { //this:State
					action { //it:State
						discardMessages = false
						if(  MaterialType == "plastic"  
						 ){CommUtils.outblue("	 $name: Going to PlasticBox!")
						request("move", "move($Pos,plasticbox)" ,"custompathexecutor" )  
						}
						if(  MaterialType == "glass"  
						 ){CommUtils.outblue("	 $name: Going to GlassBox!")
						request("move", "move($Pos,glassbox)" ,"custompathexecutor" )  
						}
						//genTimer( actor, state )
					}
					//After Lenzi Aug2002
					sysaction { //it:State
					}	 	 
					 transition(edgeName="t17",targetState="depositDone",cond=whenReply("moveDone"))
					interrupthandle(edgeName="t18",targetState="handleAlarm",cond=whenDispatch("alarm"),interruptedStateTransitions)
				}	 
				state("depositDone") { //this:State
					action { //it:State
						if( checkMsgContent( Term.createTerm("moveDone(NewPos)"), Term.createTerm("moveDone(NewPos)"), 
						                        currentMsg.msgContent()) ) { //set msgArgList
								 val Arg = payloadArg(0)  
								if(  Arg == "plasticbox" || Arg == "glassbox" || Arg == "2" || Arg == "3" 
								 ){ Pos = Arg  
								CommUtils.outblue("	 $name: Depositing $MaterialType!")
								delay(6000) 
								CommUtils.outblue("	 $name: DepositDone in $Pos!")
								}
								else
								 {CommUtils.outblue("$name: ERROR! [State: depositDone (ARG=$Arg)]!")
								 }
						}
						//genTimer( actor, state )
					}
					//After Lenzi Aug2002
					sysaction { //it:State
				 	 		stateTimer = TimerActor("timer_depositDone", 
				 	 					  scope, context!!, "local_tout_transporttrolley_depositDone", 100.toLong() )
					}	 	 
					 transition(edgeName="t29",targetState="goHome",cond=whenTimeout("local_tout_transporttrolley_depositDone"))   
					transition(edgeName="t210",targetState="handlePickupReq",cond=whenRequest("pickupReq"))
					interrupthandle(edgeName="t211",targetState="handleAlarm",cond=whenDispatch("alarm"),interruptedStateTransitions)
				}	 
				state("goHome") { //this:State
					action { //it:State
						discardMessages = false
						CommUtils.outblue("	 $name: Going home")
						request("move", "move($Pos,home)" ,"custompathexecutor" )  
						//genTimer( actor, state )
					}
					//After Lenzi Aug2002
					sysaction { //it:State
					}	 	 
					 transition(edgeName="t312",targetState="waiting",cond=whenReply("moveDone"))
					transition(edgeName="t313",targetState="handlePickupReq",cond=whenRequest("pickupReq"))
					interrupthandle(edgeName="t314",targetState="handleAlarm",cond=whenDispatch("alarm"),interruptedStateTransitions)
				}	 
				state("handleAlarm") { //this:State
					action { //it:State
						 
									PreviousState = RobotState 
									RobotState = "stopped"
						CommUtils.outblue("	 $name: Robot Stopped!")
						forward("toggleStop", "toggleStop(stop)" ,"custompathexecutor" ) 
						updateResourceRep( "robotState($RobotState)"  
						)
						//genTimer( actor, state )
					}
					//After Lenzi Aug2002
					sysaction { //it:State
					}	 	 
					 transition(edgeName="t315",targetState="resume",cond=whenDispatch("alarmStop"))
				}	 
				state("resume") { //this:State
					action { //it:State
						 RobotState = PreviousState  
						CommUtils.outblue("	 $name: Resumed execution!")
						forward("toggleStop", "toggleStop(resume)" ,"custompathexecutor" ) 
						updateResourceRep( "robotState($RobotState)"  
						)
						returnFromInterrupt(interruptedStateTransitions)
						//genTimer( actor, state )
					}
					//After Lenzi Aug2002
					sysaction { //it:State
					}	 	 
				}	 
			}
		}
}
