package space.efremov.linkshrinker

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class LinkShrinkerApplication

fun main(args: Array<String>) {
    runApplication<LinkShrinkerApplication>(*args)
}
