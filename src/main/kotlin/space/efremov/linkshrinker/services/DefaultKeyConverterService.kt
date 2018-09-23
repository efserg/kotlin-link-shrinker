package space.efremov.linkshrinker.services

import org.springframework.stereotype.Component

@Component
class DefaultKeyConverterService : KeyConverterService {

    val chars = "qwertyuiopasdfghjklzxcvbnmQWERTYUIOPASDFGHJKLZXCVBNM1234567890".toCharArray()
    val charToLong = chars.mapIndexed { index, c -> Pair(c, index.toLong()) }.toMap()

    override fun idToKey(id: Long): String {
        var n = id
        val builder = StringBuilder()
        while (n != 0L) {
            builder.append(chars[(n % chars.size).toInt()])
            n /= chars.size
        }
        return builder.reverse().toString()
    }

    override fun keyToId(key: String): Long = key.map { charToLong[it]!! }.fold(0L) { a, b -> a * chars.size + b }

}
