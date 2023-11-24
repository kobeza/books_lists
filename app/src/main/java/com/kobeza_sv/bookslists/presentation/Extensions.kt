package com.kobeza_sv.bookslists.presentation

import android.annotation.SuppressLint
import android.view.View
import androidx.annotation.DimenRes
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.kobeza_sv.bookslists.R
import com.kobeza_sv.bookslists.presentation.recycler.SpaceItemDecoration
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.format.DateTimeFormatter
import java.util.Date
import java.util.Locale

fun View.makeGone() {
    this.visibility = View.GONE
}

fun <P : ViewModel?> provideViewModel(predicate: () -> P): ViewModelProvider.Factory =
    object : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return predicate.invoke() as T
        }
    }

fun RecyclerView.addSpacingDecoration(
    @DimenRes spacing: Int,
    @RecyclerView.Orientation orientation: Int,
    includeEdges: Boolean = false,
) {
    val offset = context.resources.getDimensionPixelSize(spacing)
    val edges =
        if (includeEdges) offset else context.resources.getDimensionPixelSize(R.dimen.spacing_0)
    addItemDecoration(SpaceItemDecoration(edges, (offset / 2f).toInt(), orientation))
}

@SuppressLint("NewApi")
fun String.isoToSimpleFormat(): String {
    return kotlin.runCatching {
        val timeFormatter = DateTimeFormatter.ISO_DATE_TIME
        val accessor = timeFormatter.parse(this)
        val data = Date.from(Instant.from(accessor))
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        dateFormat.format(data)
    }.getOrElse { this }
}