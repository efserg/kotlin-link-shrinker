package space.efremov.linkshrinker.services

import org.junit.Test
import org.junit.Assert.*

class DefaultKeyMapperServiceTest {

    private val service: KeyMapperService = DefaultKeyMapperService()

    private val KEY = "aAbBcCdD"
    private val LINK = "http://www.eveonline.com"
    private val LINK_NEW: String = "http://www.ru"

    @Test
    fun clientCanAddNewKeyWithLink() {
        assertEquals(KeyMapperService.Add.Success(KEY, LINK), service.add(KEY, LINK))
        assertEquals(KeyMapperService.Get.Link(LINK), service.getLink(KEY))
    }

    @Test
    fun clientCannotAddExistKey() {
        service.add(KEY, LINK)
        assertEquals(KeyMapperService.Add.AlreadyExist(KEY), service.add(KEY, LINK_NEW))
        assertEquals(KeyMapperService.Get.Link(LINK), service.getLink(KEY))
    }

    @Test
    fun clientCannotTakeLinkIfKeyIsNotFoundInService() {
        assertEquals(KeyMapperService.Get.NotFound(KEY), service.getLink(KEY))
    }
}