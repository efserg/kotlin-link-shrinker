package space.efremov.linkshrinker.services

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import space.efremov.linkshrinker.model.Link
import space.efremov.linkshrinker.repositories.LinkRepository

@Component
class DefaultKeyMapperService : KeyMapperService {

    @Autowired
    lateinit var converter: KeyConverterService

    @Autowired
    lateinit var repo: LinkRepository

    @Transactional
    override fun add(link: String): String =
            converter.idToKey(repo.save(Link(link)).id)

    override fun getLink(key: String): KeyMapperService.Get {

        var result: Link? = repo.findById(converter.keyToId(key))

        return if (result != null)
            KeyMapperService.Get.Link(result.text)
        else
            KeyMapperService.Get.NotFound(key)
    }

}