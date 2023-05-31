%====================================================================================
% logicalarchitecture description   
%====================================================================================
context(ctxraspberry, "localhost",  "TCP", "8078").
context(ctxsmartdevice, "localhots",  "TCP", "8074").
context(ctxddrobot, "localhost",  "TCP", "8070").
context(ctxwasteservice, "localhost",  "TCP", "8076").
 qactor( transporttrolley, ctxwasteservice, "it.unibo.transporttrolley.Transporttrolley").
  qactor( wasteservice, ctxwasteservice, "it.unibo.wasteservice.Wasteservice").
  qactor( smartdevice, ctxsmartdevice, "it.unibo.smartdevice.Smartdevice").
  qactor( led, ctxraspberry, "it.unibo.led.Led").
  qactor( sonarqak22, ctxraspberry, "it.unibo.sonarqak22.Sonarqak22").
  qactor( basicrobot22, ctxddrobot, "it.unibo.basicrobot22.Basicrobot22").
  qactor( wasteservicestatusgui, ctxwasteservice, "it.unibo.wasteservicestatusgui.Wasteservicestatusgui").
