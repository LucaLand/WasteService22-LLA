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
						 Pos = "home"  
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
						CommUtils.outblue("	 $name: Going to Indoor!")
						CommUtils.outblue("	 $name: Robot going from $Pos to Indoor")
						request("move", "move($Pos,indoor)" ,"custompathexecutor" )  
						//genTimer( actor, state )
					}
					//After Lenzi Aug2002
					sysaction { //it:State
					}	 	 
					 transition(edgeName="t13",targetState="pickupDone",cond=whenReply("moveDone"))
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
					}	 	 
					 transition( edgeName="goto",targetState="goDeposit", cond=doswitch() )
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
					 transition(edgeName="t14",targetState="depositDone",cond=whenReply("moveDone"))
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
					 transition(edgeName="t25",targetState="goHome",cond=whenTimeout("local_tout_transporttrolley_depositDone"))   
					transition(edgeName="t26",targetState="handlePickupReq",cond=whenRequest("pickupReq"))
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
					 transition(edgeName="t37",targetState="waiting",cond=whenReply("moveDone"))
					transition(edgeName="t38",targetState="handlePickupReq",cond=whenRequest("pickupReq"))
				}	 
			}
		}
}
