# Java TCP

> A simple Java TCP message processing built on top of Apache Mina Framework (https://mina.apache.org/).

[Inglês](/README.md) | [Português](/README.pt-BR.md)

## Instalação

```shell
$ git clone https://github.com/bernardocoferre/java-tcp.git
$ cd java-tcp
$ mvn install
$ mvn spring-boot:run
```

## Ciclo de Vida da Aplicação
- Na fase de inicialização, o Spring Framework iniciará o servidor TCP através da classe `ServerStartupRunner`
- O servidor TCP será iniciado e estará pronto para receber conexões na porta `8090`
- Uma massa de clientes será criada, que enviarão automaticamente simualações de mensagens para o nosso servidor através da classe `ClientStartupRunner`
- O página de Monitor de Mensagens ficará disponível no endereço `http://localhost:8080/`, onde poderemos verificar todas as mensagens recebidas ou inspecionar os dados uma mensagem específica 
- O console do banco de dados H2 está habilitado no endereço `http://localhost:8080/h2`, onde podermos interagir diretamente com a base de dados
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

## Recursos
- Servidor TCP java para processamento de mensagens
- Cliente TCP básico para enviar simulações de mensagens para o servidor
- Página de Monitor de Mensagens disponível no endereço `http://localhost:8080/`, onde é possível:
    - Visualizar e inspecionar todas as mensagens recebidas e enviadas
    - Acompanhar estatísticas das mensagens
    - Limpar todas as mensagens armazenadas no servidor
- REST API para verificar todas as mensagens recebidas ou inspecionar os dados de uma única mensagem
    - Disponível em `/api/messages` e `/api/messages/{id}`, respectivamente
    
![Messages Monitor](/monitor.png?raw=true "Messages Monitor")
<br>
<br>
![Message Inspector](/message.png?raw=true "Message Inspector")
