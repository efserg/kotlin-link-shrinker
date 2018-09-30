package space.efremov.linkshrinker.services

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotEquals
import org.junit.Before
import org.junit.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import space.efremov.linkshrinker.model.Link
import space.efremov.linkshrinker.repositories.LinkRepository
import space.efremov.linkshrinker.whenever

class DefaultKeyMapperServiceTest {

    @InjectMocks
    private val service: KeyMapperService = DefaultKeyMapperService()

    @Mock
    lateinit var converter: KeyConverterService

    @Mock
    lateinit var repo: LinkRepository

    @Before
    fun init() {
        MockitoAnnotations.initMocks(this)

        whenever(converter.keyToId(KEY_A)).thenReturn(ID_A)
        whenever(converter.idToKey(ID_A)).thenReturn(KEY_A)
        whenever(converter.keyToId(KEY_B)).thenReturn(ID_B)
        whenever(converter.idToKey(ID_B)).thenReturn(KEY_B)

        whenever(repo.findById(Mockito.any())).thenReturn(null)
        whenever(repo.save(Link(LINK_A))).thenReturn(LINK_OBJECT_A)
        whenever(repo.save(Link(LINK_B))).thenReturn(LINK_OBJECT_B)
        whenever(repo.findById(ID_A)).thenReturn(LINK_OBJECT_A)
        whenever(repo.findById(ID_B)).thenReturn(LINK_OBJECT_B)
    }

    private val KEY = "aAbBcCdD"
    private val LINK_A = "http://www.google.com"
    private val LINK_B: String = "http://www.amazon.com"
    private val KEY_A = "abc"
    private val ID_A = 10_000_000L
    private val KEY_B = "cde"
    private val ID_B = 10_000_001L
    private val LINK_OBJECT_A: Link = Link(LINK_A, ID_A)
    private val LINK_OBJECT_B: Link = Link(LINK_B, ID_B)

    @Test
    fun clientCannotAddLink() {
        val keyA: String = service.add(LINK_A)
        assertEquals(KeyMapperService.Get.Link(LINK_A), service.getLink(keyA))
        val keyB: String = service.add(LINK_B)
        assertEquals(KeyMapperService.Get.Link(LINK_B), service.getLink(keyB))
        assertNotEquals(keyA, keyB)
    }

    @Test
    fun clientCannotTakeLinkIfKeyIsNotFoundInService() {
        assertEquals(KeyMapperService.Get.NotFound(KEY), service.getLink(KEY))
    }
}