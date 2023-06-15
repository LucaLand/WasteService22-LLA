%====================================================================================
% raspberry description   
%====================================================================================
context(ctxwasteservice, "127.0.0.1",  "TCP", "8072").
context(ctxraspberry, "localhost",  "TCP", "8076").
 qactor( sonar, ctxraspberry, "sonarSimulator").
  qactor( datacleaner, ctxraspberry, "rx.dataCleaner").
  qactor( distancefilter, ctxraspberry, "rx.distanceFilter").
  qactor( transporttrolley, ctxwasteservice, "external").
  qactor( led, ctxraspberry, "it.unibo.led.Led").
  qactor( sonar23, ctxraspberry, "it.unibo.sonar23.Sonar23").
  qactor( sonardatahandler, ctxraspberry, "it.unibo.sonardatahandler.Sonardatahandler").
