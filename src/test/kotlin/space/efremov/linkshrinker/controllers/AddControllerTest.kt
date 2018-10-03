package space.efremov.linkshrinker.controllers

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.hamcrest.Matchers
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.context.TestPropertySource
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner
import org.springframework.test.context.web.WebAppConfiguration
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.context.WebApplicationContext
import space.efremov.linkshrinker.LinkShrinkerApplication
import space.efremov.linkshrinker.services.KeyMapperService
import space.efremov.linkshrinker.whenever

@RunWith(SpringJUnit4ClassRunner::class)
@SpringBootTest(classes = [LinkShrinkerApplication::class])
@TestPropertySource(locations = ["classpath:repository-test.properties"])
@WebAppConfiguration
class AddControllerTest {

    @Autowired
    lateinit var webApplicationContext: WebApplicationContext

    lateinit var mockMvc: MockMvc

    @Mock
    lateinit var service: KeyMapperService

    @Autowired
    @InjectMocks
    lateinit var controller: AddController

    private val KEY: String = "key"
    private val LINK: String = "link"

    @Before
    fun init() {
        MockitoAnnotations.initMocks(this)
        mockMvc = MockMvcBuilders
                .webAppContextSetup(webApplicationContext)
                .build()
        whenever(service.add(LINK)).thenReturn(KEY)
    }

    @Test
    fun whenUserAddLinkHeTakesKey() {
        mockMvc.perform(post("/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jacksonObjectMapper().writeValueAsString(AddController.AddRequest(LINK))))
                .andExpect(MockMvcResultMatchers.jsonPath("$.key", Matchers.equalTo(KEY)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.link", Matchers.equalTo(LINK)))

    }

    @Test
    fun whenUserAddLinkByFormHeTakesWebPage() {
        mockMvc.perform(MockMvcRequestBuilders.post("/addhtml")
                .param("link", LINK)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(MockMvcResultMatchers.status().isOk)
                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString(KEY)))
                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString(LINK)))
    }
}