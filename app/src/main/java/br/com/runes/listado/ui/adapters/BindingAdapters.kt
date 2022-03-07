package br.com.runes.listado.ui.adapters

import android.graphics.Paint
import android.widget.TextView
import androidx.databinding.BindingAdapter
import br.com.runes.listado.R
import br.com.runes.listado.domain.model.Task
import br.com.runes.listado.extensions.dateToExtensiveDate

object BindingAdapters {

    @JvmStatic
    @BindingAdapter("isCompleteTask")
    fun TextView.strikeOutText(isComplete: Boolean?) {
        isComplete?.let {
            if (it) {
                this.paintFlags = paintFlags or Paint.STRIKE_THRU_TEXT_FLAG//Paint.STRIKE_THRU_TEXT_FLAG
            } else {
                this.paintFlags = paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
            }
        }
    }

    @JvmStatic
    @BindingAdapter("setDate")
    fun TextView.setExpandedDate(task: Task?) {
        task?.let {
            text = context.getString(R.string.formatted_date_hour, it.date.dateToExtensiveDate(), it.hour)
        }
    }

}