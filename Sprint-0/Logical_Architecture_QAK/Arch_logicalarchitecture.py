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
     with Cluster('ctxraspberry', graph_attr=nodeattr):
          ledactor=Custom('ledactor','./qakicons/symActorSmall.png')
          sonaractorexemple=Custom('sonaractorexemple','./qakicons/symActorSmall.png')
     with Cluster('ctxsmartdevice', graph_attr=nodeattr):
          smartdevice=Custom('smartdevice','./qakicons/symActorSmall.png')
     with Cluster('ctxddrobot', graph_attr=nodeattr):
          basicrobotexample=Custom('basicrobotexample','./qakicons/symActorSmall.png')
     with Cluster('ctxwasteservice', graph_attr=nodeattr):
          transporttrolley=Custom('transporttrolley','./qakicons/symActorSmall.png')
          wasteservice=Custom('wasteservice','./qakicons/symActorSmall.png')
          wasteservicestatusguiactor=Custom('wasteservicestatusguiactor','./qakicons/symActorSmall.png')
     transporttrolley >> Edge(color='blue', style='solid', xlabel='cmd', fontcolor='blue') >> basicrobotexample
     smartdevice >> Edge(color='magenta', style='solid', xlabel='depositRequest', fontcolor='magenta') >> wasteservice
diag
