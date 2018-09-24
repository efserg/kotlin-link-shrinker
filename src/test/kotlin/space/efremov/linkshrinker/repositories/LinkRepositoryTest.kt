package space.efremov.linkshrinker.repositories

import com.github.springtestdbunit.annotation.DatabaseOperation
import com.github.springtestdbunit.annotation.DatabaseSetup
import com.github.springtestdbunit.annotation.DatabaseTearDown
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.equalTo
import org.hamcrest.Matchers.hasSize
import org.junit.Test
import org.springframework.beans.factory.annotation.Autowired
import space.efremov.linkshrinker.model.Link

@DatabaseSetup(LinkRepositoryTest.DATASET)
@DatabaseTearDown(type = DatabaseOperation.DELETE_ALL, value = [LinkRepositoryTest.DATASET])
class LinkRepositoryTest : AbstractRepositoryTest() {

    @Autowired
    lateinit var repository: LinkRepository

    @Test
    fun findOneExisting() {
        val got: Link? = repository.findById(LINK_1_ID)
        assertThat(got != null, equalTo(true))
        val link: Link? = got!!
        assertThat(link, equalTo(Link(LINK_1_TEXT, LINK_1_ID)))
    }

    @Test
    fun findOneNotExisting() {
        val got: Link? = repository.findById(LINK_ID_NOT_EXIST)
        assertThat(got != null, equalTo(false))
    }

    @Test
    fun saveNew() {
        val toBeSaved = Link(LINK_2_TEXT)
        val got: Link = repository.save(toBeSaved)
        val links: List<Link> = repository.findAll()
        assertThat(links, hasSize(4))
        assertThat(got.text, equalTo(LINK_2_TEXT))
    }

    companion object {
        const val DATASET = "classpath:datasets/link-table.xml"
        private const val LINK_1_ID: Long = 100_500L
        private const val LINK_ID_NOT_EXIST: Long = 1L
        private const val LINK_1_TEXT: String = "http://www.eveonline.com"
        private const val LINK_2_TEXT: String = "http://www.spb.ru"
    }

}