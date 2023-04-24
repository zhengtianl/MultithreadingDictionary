# MultithreadingDictionary

## Introduction 
This task is to build the communication architecture between the server and the client, and realize the function of adding, deleting, modifying and checking the dictionary. This article introduces how the author uses TCP to realize the communication between the client and the server. In terms of troubleshooting, client and server issues were well managed and the system didn’t crash with common errors like connection, bind, and null pointer exceptions. At the same time, it also fully reflects the mistakes that have occurred so that they can be corrected. Also uses a special string to tell the server and client what to do at the moment. This report also includes critical thinking on class design and the whole system. It also point out the disadvantage of the project and Improvement direction.

## Components
Server, client and user interface are the three main components of the system. The server side handles sending requests from clients, manipulating dictionary files as they are read or written. The client is responsible for creating the client UI that the user interacts with. If the user provides accurate information, the request is forwarded to the server for processing, and the result is returned to the client.

## Structure
- [src](/Github/MultithreadingDictionary/src) #main code
- [bin](/Github/MultithreadingDictionary/bin) #modules need
- [report](/Github/MultithreadingDictionary/report) #the detailed introduction of the project

## Include
This project also includes two executable files
 
 run the Server by using:
```
java -jar DictionaryServer-7.jar
```
 run the Client by using:
```
java -jar DictionaryClient-7.jar
```
