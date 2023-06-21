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
     with Cluster('ctxbasicrobot', graph_attr=nodeattr):
          basicrobot=Custom('basicrobot(ext)','./qakicons/externalQActor.png')
     with Cluster('ctxwasteservice', graph_attr=nodeattr):
          wasteservice=Custom('wasteservice','./qakicons/symActorSmall.png')
          transporttrolley=Custom('transporttrolley','./qakicons/symActorSmall.png')
          custompathexecutor=Custom('custompathexecutor','./qakicons/symActorSmall.png')
     wasteservice >> Edge(color='magenta', style='solid', xlabel='pickupReq', fontcolor='magenta') >> transporttrolley
     transporttrolley >> Edge(color='magenta', style='solid', xlabel='move', fontcolor='magenta') >> custompathexecutor
     transporttrolley >> Edge(color='darkgreen', style='dashed', xlabel='pickupOk', fontcolor='darkgreen') >> wasteservice
     basicrobot >> Edge(color='blue', style='solid', xlabel='coapUpdate', fontcolor='blue') >> custompathexecutor
     custompathexecutor >> Edge(color='blue', style='solid', xlabel='cmd', fontcolor='blue') >> basicrobot
     custompathexecutor >> Edge(color='blue', style='solid', xlabel='coapUpdate', fontcolor='blue') >> custompathexecutor
     custompathexecutor >> Edge(color='darkgreen', style='dashed', xlabel='moveDone', fontcolor='darkgreen') >> transporttrolley
diag
