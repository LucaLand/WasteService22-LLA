%====================================================================================
% generalarchitecturesprint2 description   
%====================================================================================
context(ctxraspberry, "localhots",  "TCP", "8076").
context(ctxbasicrobot, "127.0.0.1",  "TCP", "8020").
context(ctxwasteservice, "localhost",  "TCP", "8072").
 qactor( sonar, ctxraspberry, "sonarSimulator").
  qactor( datacleaner, ctxraspberry, "rx.dataCleaner").
  qactor( basicrobot, ctxbasicrobot, "external").
  qactor( wasteservice, ctxwasteservice, "it.unibo.wasteservice.Wasteservice").
  qactor( transporttrolley, ctxwasteservice, "it.unibo.transporttrolley.Transporttrolley").
  qactor( custompathexecutor, ctxwasteservice, "it.unibo.custompathexecutor.Custompathexecutor").
  qactor( sonardatahandler, ctxwasteservice, "it.unibo.sonardatahandler.Sonardatahandler").
  qactor( led, ctxraspberry, "it.unibo.led.Led").
  qactor( sonar23, ctxraspberry, "it.unibo.sonar23.Sonar23").
