%====================================================================================
% raspberry description   
%====================================================================================
context(ctxraspberry, "localhost",  "TCP", "8076").
 qactor( sonardatasource, ctxraspberry, "sensors.sonarHCSR04SupportActor").
  qactor( datalogger, ctxraspberry, "rx.dataLogger").
  qactor( datacleaner, ctxraspberry, "rx.dataCleaner").
  qactor( distancefilter, ctxraspberry, "rx.distanceFilter").
  qactor( led, ctxraspberry, "it.unibo.led.Led").
  qactor( sonar23, ctxraspberry, "it.unibo.sonar23.Sonar23").
