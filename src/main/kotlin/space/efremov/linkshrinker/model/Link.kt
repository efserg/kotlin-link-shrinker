package space.efremov.linkshrinker.model

import javax.persistence.*

@Entity
@Table(name = "links")
data class Link(
        var text: String = "",
        @Id
        @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "link_sequence")
        @SequenceGenerator(name = "link_sequence", sequenceName = "link_seq")
        var id: Long = 0L
)