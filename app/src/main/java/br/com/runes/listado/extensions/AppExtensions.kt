package br.com.runes.listado.extensions

import android.content.Context
import android.widget.Toast
import com.google.android.material.textfield.TextInputLayout
import java.text.SimpleDateFormat
import java.util.*

private val locale = Locale("pt", "BR")

fun String.dateToExtensiveDate() : String? {
    val date = SimpleDateFormat("dd/MM/yyyy", locale).parse(this)
    return date?.formatExtended()
}

fun Date.formatExtended() : String {
    return SimpleDateFormat("EE, dd 'de' MMM 'de' yyyy", locale).format(this)
}

fun Date.format() : String {
    return SimpleDateFormat("dd/MM/yyyy", locale).format(this)
}

fun Context.shortToast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

var TextInputLayout.text : String
    get() = editText?.text?.toString() ?: ""
    set(value) {
        editText?.setText(value)
    }