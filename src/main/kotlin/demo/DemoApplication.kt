package demo

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import org.springframework.data.repository.CrudRepository
import org.springframework.http.ResponseEntity
import org.springframework.http.ResponseEntity.ok
import org.springframework.stereotype.Controller
import org.springframework.stereotype.Repository
import org.springframework.stereotype.Service
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody

@SpringBootApplication
class DemoApplication

fun main(args: Array<String>) {
    runApplication<DemoApplication>(*args)
}

@Controller
class MessageController(val messageService: MessageService) {

    @GetMapping("/message")
    fun getMessages(): ResponseEntity<MutableIterable<Message>> {
        return ok(messageService.getAllMessages())
    }

    @PostMapping("/message")
    fun createMessage(@RequestBody message: Message): ResponseEntity<Any> {
        messageService.createMessage(message)
        return ok().build()
    }
}

@Service
class MessageService(val messageRepository: MessageRepository) {

    fun createMessage(message: Message) {
        messageRepository.save(message)
    }

    fun getAllMessages(): MutableIterable<Message> {
        return messageRepository.findAll()
    }
}

@Repository
interface MessageRepository : CrudRepository<Message, String>

@Table("MESSAGES")
class Message(@Id var id: String?, var text: String)


