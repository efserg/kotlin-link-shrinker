package space.efremov.linkshrinker.controllers

import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.TestPropertySource
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner
import org.springframework.test.context.web.WebAppConfiguration
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.header
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.context.WebApplicationContext
import space.efremov.linkshrinker.LinkShrinkerApplication
import space.efremov.linkshrinker.services.KeyMapperService
import javax.servlet.http.HttpServletResponse.SC_MOVED_TEMPORARILY
import javax.servlet.http.HttpServletResponse.SC_NOT_FOUND

@RunWith(SpringJUnit4ClassRunner::class)
@SpringBootTest(classes = [LinkShrinkerApplication::class])
@TestPropertySource(locations = ["classpath:repository-test.properties"])
@WebAppConfiguration
class RedirectControllerTest {

    @Autowired
    lateinit var webApplicationContext: WebApplicationContext

    lateinit var mockMvc: MockMvc

    @Mock
    lateinit var service: KeyMapperService

    @Autowired
    @InjectMocks
    lateinit var controller: RequestController

    @Before
    fun init() {
        MockitoAnnotations.initMocks(this)
        mockMvc = MockMvcBuilders
                .webAppContextSetup(webApplicationContext)
                .build()
        Mockito.`when`(service.getLink(PATH)).thenReturn(KeyMapperService.Get.Link(HEADER_VALUE))
        Mockito.`when`(service.getLink(BAD_PATH)).thenReturn(KeyMapperService.Get.NotFound(BAD_PATH))
    }

    private val PATH = "aAbBcCdD"
    private val BAD_PATH = "bad_path"
    private val HEADER_NAME = "Location"
    private val HEADER_VALUE = "http://www.eveonline.com"

    @Test
    fun controllerMustRedirectUsWhenRequestIsSuccessful() {
        mockMvc.perform(get("/$PATH"))
                .andExpect(status().`is`(SC_MOVED_TEMPORARILY))
                .andExpect(header().string(HEADER_NAME, HEADER_VALUE))
    }

    @Test
    fun controllerMustReturn404IfBadKey() {
        mockMvc.perform(get("/$BAD_PATH"))
                .andExpect(status().`is`(SC_NOT_FOUND))
    }

}