%====================================================================================
% depositrequesthandler description   
%====================================================================================
context(ctxsmartdevice, "127.0.0.1",  "TCP", "8074").
context(ctxwasteservice, "localhost",  "TCP", "8072").
 qactor( smartdevice, ctxsmartdevice, "external").
  qactor( depositrequesthandler, ctxwasteservice, "it.unibo.depositrequesthandler.Depositrequesthandler").
