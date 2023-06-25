from diagrams import Cluster, Diagram, Edge
from diagrams.custom import Custom
import os
os.environ['PATH'] += os.pathsep + 'C:/Program Files/Graphviz/bin/'

graphattr = {     #https://www.graphviz.org/doc/info/attrs.html
    'fontsize': '22',
}

nodeattr = {   
    'fontsize': '22',
    'bgcolor': 'lightyellow'
}

eventedgeattr = {
    'color': 'red',
    'style': 'dotted'
}
with Diagram('generalarchitecturesprint3Arch', show=False, outformat='png', graph_attr=graphattr) as diag:
  with Cluster('env'):
     sys = Custom('','./qakicons/system.png')
     with Cluster('ctxraspberry', graph_attr=nodeattr):
          led=Custom('led','./qakicons/symActorSmall.png')
          sonar23=Custom('sonar23','./qakicons/symActorSmall.png')
     with Cluster('ctxbasicrobot', graph_attr=nodeattr):
          basicrobot=Custom('basicrobot(ext)','./qakicons/externalQActor.png')
     with Cluster('ctxwasteservice', graph_attr=nodeattr):
          wasteservice=Custom('wasteservice','./qakicons/symActorSmall.png')
          transporttrolley=Custom('transporttrolley','./qakicons/symActorSmall.png')
          custompathexecutor=Custom('custompathexecutor','./qakicons/symActorSmall.png')
          sonardatahandler=Custom('sonardatahandler','./qakicons/symActorSmall.png')
          ledstateupdater=Custom('ledstateupdater','./qakicons/symActorSmall.png')
          guiupdater=Custom('guiupdater','./qakicons/symActorSmall.png')
     wasteservice >> Edge(color='magenta', style='solid', xlabel='pickupReq', fontcolor='magenta') >> transporttrolley
     transporttrolley >> Edge(color='magenta', style='solid', xlabel='move', fontcolor='magenta') >> custompathexecutor
     transporttrolley >> Edge(color='darkgreen', style='dashed', xlabel='pickupOk', fontcolor='darkgreen') >> wasteservice
     transporttrolley >> Edge(color='blue', style='solid', xlabel='toggleStop', fontcolor='blue') >> custompathexecutor
     basicrobot >> Edge(color='blue', style='solid', xlabel='coapUpdate', fontcolor='blue') >> custompathexecutor
     custompathexecutor >> Edge(color='blue', style='solid', xlabel='cmd', fontcolor='blue') >> basicrobot
     sonar23 >> Edge(color='blue', style='solid', xlabel='coapUpdate', fontcolor='blue') >> sonardatahandler
     sonardatahandler >> Edge(color='blue', style='solid', xlabel='alarm', fontcolor='blue') >> transporttrolley
     sonardatahandler >> Edge(color='blue', style='solid', xlabel='alarmStop', fontcolor='blue') >> transporttrolley
     transporttrolley >> Edge(color='blue', style='solid', xlabel='coapUpdate', fontcolor='blue') >> ledstateupdater
     ledstateupdater >> Edge(color='blue', style='solid', xlabel='ledStateUpdate', fontcolor='blue') >> led
     wasteservice >> Edge(color='blue', style='solid', xlabel='coapUpdate', fontcolor='blue') >> guiupdater
     transporttrolley >> Edge(color='blue', style='solid', xlabel='coapUpdate', fontcolor='blue') >> guiupdater
     custompathexecutor >> Edge(color='blue', style='solid', xlabel='coapUpdate', fontcolor='blue') >> guiupdater
     led >> Edge(color='blue', style='solid', xlabel='coapUpdate', fontcolor='blue') >> guiupdater
     sys >> Edge(color='red', style='dashed', xlabel='sonardata', fontcolor='red') >> sonar23
diag
