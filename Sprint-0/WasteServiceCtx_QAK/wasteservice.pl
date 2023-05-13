%====================================================================================
% wasteservice description   
%====================================================================================
context(ctxsmartdevice, "127.0.0.1",  "TCP", "8074").
context(ctxraspberry, "127.0.0.1",  "TCP", "8076").
context(ctxwasteservice, "localhost",  "TCP", "8072").
 qactor( smartdevice, ctxsmartdevice, "external").
  qactor( led, ctxraspberry, "external").
  qactor( depositrequesthandler, ctxwasteservice, "it.unibo.depositrequesthandler.Depositrequesthandler").
  qactor( transporttrolley, ctxwasteservice, "it.unibo.transporttrolley.Transporttrolley").
