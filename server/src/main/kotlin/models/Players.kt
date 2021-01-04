package server.models

import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient
import org.litote.kmongo.*

@Serializable
data class Players(@Transient val _id: Id<Players> = newId(), val username: String, val elo: Int = 1500) : Comparable<Players>
{
    override fun compareTo(other: Players): Int {
        return this.elo.compareTo(other.elo)
    }
}