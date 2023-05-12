%====================================================================================
% transporttrolley description   
%====================================================================================
context(ctxwasteservice, "localhost",  "TCP", "8072").
 qactor( transporttrolley, ctxwasteservice, "it.unibo.transporttrolley.Transporttrolley").
  qactor( depositrequesthandler, ctxwasteservice, "it.unibo.depositrequesthandler.Depositrequesthandler").
