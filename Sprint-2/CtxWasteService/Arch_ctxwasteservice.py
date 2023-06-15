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
with Diagram('ctxwasteserviceArch', show=False, outformat='png', graph_attr=graphattr) as diag:
  with Cluster('env'):
     sys = Custom('','./qakicons/system.png')
     with Cluster('ctxsmartdevice', graph_attr=nodeattr):
          smartdevice=Custom('smartdevice(ext)','./qakicons/externalQActor.png')
     with Cluster('ctxbasicrobot', graph_attr=nodeattr):
          basicrobot=Custom('basicrobot(ext)','./qakicons/externalQActor.png')
     with Cluster('ctxwasteservice', graph_attr=nodeattr):
          wasteservice=Custom('wasteservice','./qakicons/symActorSmall.png')
          transporttrolley=Custom('transporttrolley','./qakicons/symActorSmall.png')
          custompathexecutor=Custom('custompathexecutor','./qakicons/symActorSmall.png')
          alarmsimulator=Custom('alarmsimulator','./qakicons/symActorSmall.png')
     wasteservice >> Edge(color='darkgreen', style='dashed', xlabel='loadrejecetd', fontcolor='darkgreen') >> smartdevice
     wasteservice >> Edge(color='magenta', style='solid', xlabel='pickupReq', fontcolor='magenta') >> transporttrolley
     transporttrolley >> Edge(color='magenta', style='solid', xlabel='move', fontcolor='magenta') >> custompathexecutor
     transporttrolley >> Edge(color='darkgreen', style='dashed', xlabel='pickupOk', fontcolor='darkgreen') >> wasteservice
     transporttrolley >> Edge(color='blue', style='solid', xlabel='toggleStop', fontcolor='blue') >> custompathexecutor
     custompathexecutor >> Edge(color='blue', style='solid', xlabel='cmd', fontcolor='blue') >> basicrobot
     custompathexecutor >> Edge(color='blue', style='solid', xlabel='coapUpdate', fontcolor='blue') >> custompathexecutor
     custompathexecutor >> Edge( xlabel='posEvent', **eventedgeattr, fontcolor='red') >> sys
     custompathexecutor >> Edge(color='darkgreen', style='dashed', xlabel='moveDone', fontcolor='darkgreen') >> transporttrolley
     alarmsimulator >> Edge(color='blue', style='solid', xlabel='alarm', fontcolor='blue') >> transporttrolley
     alarmsimulator >> Edge(color='blue', style='solid', xlabel='alarmStop', fontcolor='blue') >> transporttrolley
diag