%====================================================================================
% smartdevice description   
%====================================================================================
context(ctxwasteservice, "127.0.0.1",  "TCP", "8072").
context(ctxsmartdevice, "localhost",  "TCP", "8074").
 qactor( wasteservice, ctxwasteservice, "external").
  qactor( smartdevice, ctxsmartdevice, "it.unibo.smartdevice.Smartdevice").
