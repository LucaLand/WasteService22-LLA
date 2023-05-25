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
with Diagram('logicalarchitectureArch', show=False, outformat='png', graph_attr=graphattr) as diag:
  with Cluster('env'):
     sys = Custom('','./qakicons/system.png')
     with Cluster('ctxgeneral', graph_attr=nodeattr):
          transporttrolley=Custom('transporttrolley','./qakicons/symActorSmall.png')
          wasteservice=Custom('wasteservice','./qakicons/symActorSmall.png')
          smartdevice=Custom('smartdevice','./qakicons/symActorSmall.png')
          ledactor=Custom('ledactor','./qakicons/symActorSmall.png')
          sonaractorexemple=Custom('sonaractorexemple','./qakicons/symActorSmall.png')
          basicrobotexample=Custom('basicrobotexample','./qakicons/symActorSmall.png')
          wasteserviceguiactor=Custom('wasteserviceguiactor','./qakicons/symActorSmall.png')
     transporttrolley >> Edge( xlabel='robotStateEvent', **eventedgeattr, fontcolor='red') >> sys
     transporttrolley >> Edge(color='blue', style='solid', xlabel='cmd', fontcolor='blue') >> basicrobotexample
     wasteservice >> Edge(color='magenta', style='solid', xlabel='pickupReq', fontcolor='magenta') >> transporttrolley
     smartdevice >> Edge(color='magenta', style='solid', xlabel='depositRequest', fontcolor='magenta') >> wasteservice
     sys >> Edge(color='red', style='dashed', xlabel='robotStateEvent', fontcolor='red') >> ledactor
     transporttrolley >> Edge(color='blue', style='solid', xlabel='coapUpdate', fontcolor='blue') >> wasteserviceguiactor
     ledactor >> Edge(color='blue', style='solid', xlabel='coapUpdate', fontcolor='blue') >> wasteserviceguiactor
     sys >> Edge(color='red', style='dashed', xlabel='robotStateEvent', fontcolor='red') >> wasteserviceguiactor
diag
