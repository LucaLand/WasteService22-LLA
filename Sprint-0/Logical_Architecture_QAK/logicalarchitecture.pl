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
  qactor( ledactor, ctxraspberry, "it.unibo.ledactor.Ledactor").
  qactor( sonaractorexemple, ctxraspberry, "it.unibo.sonaractorexemple.Sonaractorexemple").
  qactor( basicrobotexample, ctxddrobot, "it.unibo.basicrobotexample.Basicrobotexample").
  qactor( wasteservicestatusguiactor, ctxwasteservice, "it.unibo.wasteservicestatusguiactor.Wasteservicestatusguiactor").
