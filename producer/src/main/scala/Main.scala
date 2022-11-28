import akka.actor.{ActorRef, ActorSystem, Props}

import java.net.InetSocketAddress
import scala.io.StdIn

object Main {
  def main(args: Array[String]): Unit = {
    val host = "localhost"
    val port = 8080
    println(s"Started client! connecting to $host:$port")

    val actorSystem: ActorSystem = ActorSystem.create("myClientActorSystem")
    val clientProps = Server.props(new InetSocketAddress(host, port), null)
    val clientActor: ActorRef = actorSystem.actorOf(clientProps)
    val clientListener = actorSystem.actorOf(Props(new ClientListener(clientActor)), name = "clientListener")

    while (true) {
      Thread.sleep(3000)
      clientListener ! "post to t1: hello t1"
      Thread.sleep(3000)
      clientListener ! "post to t2: hello t2"
    }

    var msg = StdIn.readLine()
    while (!msg.equals("stop")) {
      clientListener ! msg
      msg = StdIn.readLine()
    }
  }
}