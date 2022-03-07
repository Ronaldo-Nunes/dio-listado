package br.com.runes.listado.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import br.com.runes.listado.R
import br.com.runes.listado.databinding.ItemTaskBinding
import br.com.runes.listado.domain.model.Task

class TaskListAdapter : ListAdapter<Task, TaskListAdapter.TaskViewHolder>(DiffCallback()) {

    var listenerChangeStatus : (Task) -> Unit = {}
    var listenerEdit : (Task) -> Unit = {}
    var listenerDelete : (Task) -> Unit = {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemTaskBinding.inflate(inflater, parent, false)
        return TaskViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class TaskViewHolder(
        private val binding: ItemTaskBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Task) {
            binding.task = item
            binding.ivMore.setOnClickListener {
                showPopup(item)
            }
        }

        private fun showPopup(item: Task) {
            val ivMore = binding.ivMore
            val popupMenu = PopupMenu(ivMore.context, ivMore)
            popupMenu.menuInflater.inflate(R.menu.popup_menu, popupMenu.menu)
            if (item.isComplete) {
                popupMenu.menu.findItem(R.id.action_complete).isVisible = false
                popupMenu.menu.findItem(R.id.action_resume).isVisible = true
            } else {
                popupMenu.menu.findItem(R.id.action_resume).isVisible = false
                popupMenu.menu.findItem(R.id.action_complete).isVisible = true
            }
            popupMenu.setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.action_complete -> listenerChangeStatus(item.copy(isComplete = true))
                    R.id.action_resume -> listenerChangeStatus(item.copy(isComplete = false))
                    R.id.action_edit -> listenerEdit(item)
                    R.id.action_delete -> listenerDelete(item)
                }
                return@setOnMenuItemClickListener true
            }
            popupMenu.show()
        }
    }
}

class DiffCallback : DiffUtil.ItemCallback<Task>() {
    override fun areItemsTheSame(oldItem: Task, newItem: Task) = oldItem == newItem
    override fun areContentsTheSame(oldItem: Task, newItem: Task) = oldItem.id == newItem.id
}