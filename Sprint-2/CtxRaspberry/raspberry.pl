%====================================================================================
% raspberry description   
%====================================================================================
context(ctxwasteservice, "127.0.0.1",  "TCP", "8072").
context(ctxraspberry, "localhost",  "TCP", "8076").
 qactor( sonarsimulator, ctxraspberry, "sonarSimulator").
  qactor( sonardatasource, ctxraspberry, "sonarHCSR04Support2021").
  qactor( datacleaner, ctxraspberry, "dataCleaner").
  qactor( transporttrolley, ctxwasteservice, "external").
  qactor( led, ctxraspberry, "it.unibo.led.Led").
  qactor( sonarqak22, ctxraspberry, "it.unibo.sonarqak22.Sonarqak22").
  qactor( sonardatahandler, ctxraspberry, "it.unibo.sonardatahandler.Sonardatahandler").
  qactor( sonarsimulator, ctxraspberry, "it.unibo.sonarsimulator.Sonarsimulator").
