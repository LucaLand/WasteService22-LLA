%====================================================================================
% generalarchitecturesprint1 description   
%====================================================================================
context(ctxsmartdevice, "localhost",  "TCP", "8074").
context(ctxbasicrobot, "localhost",  "TCP", "8020").
context(ctxwasteservice, "localhost",  "TCP", "8072").
 qactor( basicrobot, ctxbasicrobot, "external").
  qactor( wasteservice, ctxwasteservice, "it.unibo.wasteservice.Wasteservice").
  qactor( transporttrolley, ctxwasteservice, "it.unibo.transporttrolley.Transporttrolley").
  qactor( custompathexecutor, ctxwasteservice, "it.unibo.custompathexecutor.Custompathexecutor").
  qactor( smartdevice, ctxsmartdevice, "it.unibo.smartdevice.Smartdevice").
