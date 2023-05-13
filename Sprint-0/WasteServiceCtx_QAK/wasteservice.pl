%====================================================================================
% wasteservice description   
%====================================================================================
context(ctxraspberry, "127.0.0.1",  "TCP", "8076").
context(ctxwasteservice, "localhost",  "TCP", "8072").
 qactor( led, ctxraspberry, "external").
  qactor( depositrequesthandler, ctxwasteservice, "it.unibo.depositrequesthandler.Depositrequesthandler").
  qactor( transporttrolley, ctxwasteservice, "it.unibo.transporttrolley.Transporttrolley").
