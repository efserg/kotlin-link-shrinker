package space.efremov.linkshrinker.controllers

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import javax.servlet.http.HttpServletResponse
import javax.servlet.http.HttpServletResponse.*

@Controller
@RequestMapping("/{key}")
class RequestController {

    @RequestMapping()
    fun redirect(@PathVariable("key") key: String, response: HttpServletResponse) {
        if (key == "aAbBcCdD") {
            response.setHeader(HEADER_NAME, "http://www.eveonline.com")
            response.status = SC_MOVED_TEMPORARILY
        } else {
            response.status = SC_NOT_FOUND
        }
    }

    companion object {
        private val HEADER_NAME = "Location"
    }
}