package space.efremov.linkshrinker.repositories

import org.springframework.data.repository.Repository
import space.efremov.linkshrinker.model.Link

interface LinkRepository : Repository<Link, Long> {

    fun findById(id: Long?): Link?

    fun save(link: Link): Link

    fun findAll(): List<Link>

}