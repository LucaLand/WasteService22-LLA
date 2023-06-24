%====================================================================================
% ctxwasteservice description   
%====================================================================================
context(ctxbasicrobot, "127.0.0.1",  "TCP", "8020").
context(ctxwasteservice, "localhost",  "TCP", "8072").
 qactor( basicrobot, ctxbasicrobot, "external").
  qactor( wasteservice, ctxwasteservice, "it.unibo.wasteservice.Wasteservice").
  qactor( transporttrolley, ctxwasteservice, "it.unibo.transporttrolley.Transporttrolley").
  qactor( custompathexecutor, ctxwasteservice, "it.unibo.custompathexecutor.Custompathexecutor").
