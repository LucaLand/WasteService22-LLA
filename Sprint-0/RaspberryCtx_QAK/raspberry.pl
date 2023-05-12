%====================================================================================
% raspberry description   
%====================================================================================
context(ctxraspberry, "localhost",  "TCP", "8076").
 qactor( led, ctxraspberry, "it.unibo.led.Led").
  qactor( sonar, ctxraspberry, "it.unibo.sonar.Sonar").
