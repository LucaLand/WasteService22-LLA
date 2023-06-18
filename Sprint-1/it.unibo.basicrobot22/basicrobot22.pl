%====================================================================================
% basicrobot22 description   
%====================================================================================
context(ctxwasteservice, "127.0.0.1",  "TCP", "8072").
context(ctxbasicrobot, "localhost",  "TCP", "8020").
 qactor( datacleaner, ctxbasicrobot, "rx.dataCleaner").
  qactor( distancefilter, ctxbasicrobot, "rx.distanceFilter").
  qactor( custompathexecutor, ctxwasteservice, "external").
  qactor( basicrobot, ctxbasicrobot, "it.unibo.basicrobot.Basicrobot").
  qactor( envsonarhandler, ctxbasicrobot, "it.unibo.envsonarhandler.Envsonarhandler").
  qactor( coapdispatcher, ctxbasicrobot, "it.unibo.coapdispatcher.Coapdispatcher").
