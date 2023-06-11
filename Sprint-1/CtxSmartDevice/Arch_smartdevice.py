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
with Diagram('smartdeviceArch', show=False, outformat='png', graph_attr=graphattr) as diag:
  with Cluster('env'):
     sys = Custom('','./qakicons/system.png')
     with Cluster('ctxwasteservice', graph_attr=nodeattr):
          wasteservice=Custom('wasteservice(ext)','./qakicons/externalQActor.png')
     with Cluster('ctxsmartdevice', graph_attr=nodeattr):
          smartdevice=Custom('smartdevice','./qakicons/symActorSmall.png')
     smartdevice >> Edge(color='magenta', style='solid', xlabel='depositRequest', fontcolor='magenta') >> wasteservice
diag
