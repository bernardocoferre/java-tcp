# Java TCP

> A simple Java TCP message processing built on top of Apache Mina Framework (https://mina.apache.org/).

[English](/README.md) | [Portuguese](/README.pt-BR.md)

## Installation

```shell
$ git clone https://github.com/bernardocoferre/java-tcp.git
$ cd java-tcp
$ mvn install
$ mvn spring-boot:run
```

## Application Lifecicle
- On first run, Spring will setup our TCP server using the `ServerStartupRunner` class
- The TCP server will be ready to receive connections at port `8090`
- A bunch of clients will be created, sending  dummy data for our server using `ClientStartupRunner` class
- The Messages Monitor front-end will be available at `http://localhost:8080/` where we can view all received messages, or inspect a single message 
- H2 console is enabled on `http://localhost:8080/h2` where we can directly interact with the database
```java
public class ServerStartupRunner implements ApplicationRunner {

    @Override
    public void run(ApplicationArguments args) throws Exception {
        ...
        acceptor.bind(new InetSocketAddress(Parameters.PORT));
    }

}
```

```java
public class ClientStartupRunner implements ApplicationRunner {

    @Override
    public void run(ApplicationArguments args) throws Exception {
        LOGGER.info(new Str.Builder("Starting [{}] clients..").build(Parameters.CLIENTS_AMOUNT));
        this.client.start(Parameters.CLIENTS_AMOUNT);
    }

}
```

## Features
- Java TCP server for handle incoming messages
- A simple TCP client to send dummy messages to server
- Front-end Messages Monitor available at `http://localhost:8080/`
    - View and inspect all received messages
    - Follow messages statistics
    - Clean-up all the stored messages on server
- A simple REST API to view all messages or inspect a specific message
    - Available at `/api/messages` and `/api/messages/{id}`
    
![Messages Monitor](/monitor.png?raw=true "Messages Monitor")
<br>
<br>
![Message Inspector](/message.png?raw=true "Message Inspector")
