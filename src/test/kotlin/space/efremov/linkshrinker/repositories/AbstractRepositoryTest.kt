package space.efremov.linkshrinker.repositories

import com.github.springtestdbunit.DbUnitTestExecutionListener
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.annotation.DirtiesContext
import org.springframework.test.context.TestExecutionListeners
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests
import space.efremov.linkshrinker.LinkShrinkerApplication

@TestExecutionListeners(DbUnitTestExecutionListener::class)
@SpringBootTest(classes = [LinkShrinkerApplication::class])
@DirtiesContext
abstract class AbstractRepositoryTest : AbstractTransactionalJUnit4SpringContextTests()