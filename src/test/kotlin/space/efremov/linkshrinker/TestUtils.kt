package space.efremov.linkshrinker

import org.mockito.Mockito

fun <T> whenever(call: T) = Mockito.`when`(call)