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
with Diagram('raspberryArch', show=False, outformat='png', graph_attr=graphattr) as diag:
  with Cluster('env'):
     sys = Custom('','./qakicons/system.png')
     with Cluster('ctxwasteservice', graph_attr=nodeattr):
          transporttrolley=Custom('transporttrolley(ext)','./qakicons/externalQActor.png')
     with Cluster('ctxraspberry', graph_attr=nodeattr):
          led=Custom('led','./qakicons/symActorSmall.png')
          sonar23=Custom('sonar23','./qakicons/symActorSmall.png')
          sonardatahandler=Custom('sonardatahandler','./qakicons/symActorSmall.png')
          sonar=Custom('sonar(coded)','./qakicons/codedQActor.png')
          datacleaner=Custom('datacleaner(coded)','./qakicons/codedQActor.png')
          distancefilter=Custom('distancefilter(coded)','./qakicons/codedQActor.png')
     sys >> Edge(color='red', style='dashed', xlabel='robotStateEvent', fontcolor='red') >> led
     sys >> Edge(color='red', style='dashed', xlabel='sonardata', fontcolor='red') >> sonar23
     sys >> Edge(color='red', style='dashed', xlabel='obstacle', fontcolor='red') >> sonar23
     sonar23 >> Edge( xlabel='sonardataAppl', **eventedgeattr, fontcolor='red') >> sys
     sonar23 >> Edge( xlabel='alarm', **eventedgeattr, fontcolor='red') >> sys
     sonardatahandler >> Edge(color='blue', style='solid', xlabel='alarm', fontcolor='blue') >> transporttrolley
     sonardatahandler >> Edge(color='blue', style='solid', xlabel='alarmStop', fontcolor='blue') >> transporttrolley
     sys >> Edge(color='red', style='dashed', xlabel='sonardataAppl', fontcolor='red') >> sonardatahandler
diag
