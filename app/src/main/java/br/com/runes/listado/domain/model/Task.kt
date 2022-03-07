package br.com.runes.listado.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Task(
    val title: String,
    val note: String,
    val isComplete: Boolean = false,
    val hour: String,
    val date: String,
    val id: Int = 0
) : Parcelable {

    fun isBlank(): Boolean {
        return title.isBlank() || date.isBlank() || hour.isBlank()
    }
}
