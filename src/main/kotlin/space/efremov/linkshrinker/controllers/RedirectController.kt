package space.efremov.linkshrinker.controllers

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import space.efremov.linkshrinker.services.KeyMapperService
import javax.servlet.http.HttpServletResponse

@Controller
class RedirectController {

    @Autowired
    lateinit var service: KeyMapperService

    @GetMapping("/")
    fun home() = "home"

    @RequestMapping("{key}")
    fun redirect(@PathVariable("key") key: String, response: HttpServletResponse) {
       val result = service.getLink(key)
        when (result) {
            is KeyMapperService.Get.Link -> {
                response.setHeader(HEADER_NAME, result.link)
                response.status = 302
            }
            is KeyMapperService.Get.NotFound -> {
                response.status = 404
            }
        }
    }

    companion object {
        private val HEADER_NAME = "Location"
    }
}