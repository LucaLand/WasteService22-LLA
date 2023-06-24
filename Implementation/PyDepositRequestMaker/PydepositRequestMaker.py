#!/usr/bin/env python
# coding: utf-8

# In[1]:


from warnings import warn

import socket
import sys
from warnings import catch_warnings

host = '127.0.0.1'

sock = socket.socket(socket.AF_INET, socket.SOCK_STREAM)


def wantconn():
    print("Inserisci la porta l'indirizzo IP\n")
    host = input("Host (Ex. 127.0.0.1):")
    ports = input("Port: (8072) \n")
    connect(host, ports)


def connect(host, port):
    if (port == "" or port is None):
        print("No port!")
        sys.exit(1)

    port = int(port)
    server_address = (host, port)
    print(server_address)
    try:
        sock.connect(server_address)
        print("CONNECTED ", server_address)
    except:
        raise Warning(f"Connection Exception - Addr-> http://{host}:{port}")


def request(message):
    print("Sent Request: ", message)
    msg = message + "\n"
    byt = msg.encode()  # required in Python3
    sock.send(byt)
    handleAnswer()


def generateRequest():
    print("Generating Request!...")
    type = input("Deposit Type: (plastic/glass)\n")
    if (type == "" or type is None):
        print("No type!")
        sys.exit(1)

    if (type == "glass" or type == "plastic"):
        truckLoad = input("Truck Load (kg):\n")
        depositRequest = f"msg(depositRequest,request,python,wasteservice,depositRequest({type},{truckLoad}),1)"
        print(f"Truck Load: {truckLoad}")
        request(depositRequest)
    else:
        print("Type not valid!")
        generateRequest()


def handleAnswer():
    print("Waiting Answer...")
    while True:  ##client wants to maintain the connection
        reply = ''
        while True:
            answer = sock.recv(50)
            ## print("answer len=", len(answer))
            if len(answer) <= 0:
                break
            reply += answer.decode("utf-8")
            ## print("reply=", reply)
            if reply.endswith("\n"):
                break
        print("Answer=", reply)
        break
    #terminate()
    generateRequest()


def terminate():
    sock.close()
    print("BYE")


wantconn()
generateRequest()

# In[ ]:
