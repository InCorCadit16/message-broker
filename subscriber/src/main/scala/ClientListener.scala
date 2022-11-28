import akka.actor.{Actor, ActorRef}
import akka.util.ByteString

class ClientListener(client: ActorRef) extends Actor {
  def receive: Receive = {
    case msg: String =>
      client ! ByteString(msg)
  }
}