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
          led=Custom('led','./qakicons/symActorSmall.png')
          sonarqak22=Custom('sonarqak22','./qakicons/symActorSmall.png')
     with Cluster('ctxsmartdevice', graph_attr=nodeattr):
          smartdevice=Custom('smartdevice','./qakicons/symActorSmall.png')
     with Cluster('ctxddrobot', graph_attr=nodeattr):
          basicrobot22=Custom('basicrobot22','./qakicons/symActorSmall.png')
     with Cluster('ctxwasteservice', graph_attr=nodeattr):
          transporttrolley=Custom('transporttrolley','./qakicons/symActorSmall.png')
          wasteservice=Custom('wasteservice','./qakicons/symActorSmall.png')
          wasteservicestatusgui=Custom('wasteservicestatusgui','./qakicons/symActorSmall.png')
     wasteservice >> Edge(color='darkgreen', style='dashed', xlabel='loadrejecetd', fontcolor='darkgreen') >> smartdevice
     wasteservice >> Edge(color='darkgreen', style='dashed', xlabel='loadaccept', fontcolor='darkgreen') >> smartdevice
     smartdevice >> Edge(color='magenta', style='solid', xlabel='depositRequest', fontcolor='magenta') >> wasteservice
diag
