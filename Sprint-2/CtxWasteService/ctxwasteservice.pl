%====================================================================================
% ctxwasteservice description   
%====================================================================================
context(ctxraspberry, "127.0.0.1",  "TCP", "8076").
context(ctxbasicrobot, "127.0.0.1",  "TCP", "8020").
context(ctxwasteservice, "localhost",  "TCP", "8072").
 qactor( basicrobot, ctxbasicrobot, "external").
  qactor( sonar23, ctxraspberry, "external").
  qactor( led, ctxraspberry, "external").
  qactor( wasteservice, ctxwasteservice, "it.unibo.wasteservice.Wasteservice").
  qactor( transporttrolley, ctxwasteservice, "it.unibo.transporttrolley.Transporttrolley").
  qactor( custompathexecutor, ctxwasteservice, "it.unibo.custompathexecutor.Custompathexecutor").
  qactor( sonardatahandler, ctxwasteservice, "it.unibo.sonardatahandler.Sonardatahandler").
  qactor( ledstateupdater, ctxwasteservice, "it.unibo.ledstateupdater.Ledstateupdater").
