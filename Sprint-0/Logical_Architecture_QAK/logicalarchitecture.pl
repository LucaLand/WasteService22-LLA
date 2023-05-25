%====================================================================================
% logicalarchitecture description   
%====================================================================================
context(ctxgeneral, "localhost",  "TCP", "8092").
 qactor( transporttrolley, ctxgeneral, "it.unibo.transporttrolley.Transporttrolley").
  qactor( wasteservice, ctxgeneral, "it.unibo.wasteservice.Wasteservice").
  qactor( smartdevice, ctxgeneral, "it.unibo.smartdevice.Smartdevice").
  qactor( ledactor, ctxgeneral, "it.unibo.ledactor.Ledactor").
  qactor( sonaractorexemple, ctxgeneral, "it.unibo.sonaractorexemple.Sonaractorexemple").
  qactor( basicrobotexample, ctxgeneral, "it.unibo.basicrobotexample.Basicrobotexample").
  qactor( wasteserviceguiactor, ctxgeneral, "it.unibo.wasteserviceguiactor.Wasteserviceguiactor").
