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
          sonarqak22=Custom('sonarqak22','./qakicons/symActorSmall.png')
          sonardatahandler=Custom('sonardatahandler','./qakicons/symActorSmall.png')
          sonarsimulator=Custom('sonarsimulator','./qakicons/symActorSmall.png')
          sonarsimulator=Custom('sonarsimulator(coded)','./qakicons/codedQActor.png')
          sonardatasource=Custom('sonardatasource(coded)','./qakicons/codedQActor.png')
          datacleaner=Custom('datacleaner(coded)','./qakicons/codedQActor.png')
     sys >> Edge(color='red', style='dashed', xlabel='robotStateEvent', fontcolor='red') >> led
     sonarqak22 >> Edge(color='blue', style='solid', xlabel='sonaractivate', fontcolor='blue') >> sonarsimulator
     sonarqak22 >> Edge(color='blue', style='solid', xlabel='sonaractivate', fontcolor='blue') >> sonardatasource
     sys >> Edge(color='red', style='dashed', xlabel='sonar', fontcolor='red') >> sonarqak22
     sonarqak22 >> Edge( xlabel='sonardata', **eventedgeattr, fontcolor='red') >> sys
     sonardatahandler >> Edge(color='blue', style='solid', xlabel='alarm', fontcolor='blue') >> transporttrolley
     sonardatahandler >> Edge(color='blue', style='solid', xlabel='alarmStop', fontcolor='blue') >> transporttrolley
     sys >> Edge(color='red', style='dashed', xlabel='sonardata', fontcolor='red') >> sonardatahandler
     sonarsimulator >> Edge(color='blue', style='solid', xlabel='sonaractivate', fontcolor='blue') >> sonarqak22
diag
