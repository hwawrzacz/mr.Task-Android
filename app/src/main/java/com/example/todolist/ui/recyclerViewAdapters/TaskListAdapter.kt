package com.example.todolist.ui.recyclerViewAdapters

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.core.graphics.drawable.DrawableCompat
import androidx.core.graphics.drawable.toDrawable
import androidx.recyclerview.widget.RecyclerView
import com.example.todolist.model.Task
import com.example.todolist.R
import com.example.todolist.TaskDetails
import com.example.todolist.model.Priority
import com.example.todolist.ui.manager.MyFragmentManager

class TaskListAdapter(val context: Context, var tasks: MutableList<Task>) : RecyclerView.Adapter<TaskListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.task_element, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return this.tasks.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val task = tasks[position]

        holder.TITLE.text = task.title
        holder.DESCRIPTION.text = task.description
        holder.PRIORITY.text = task.priority.value
        holder.STATUS.text = task.status.value
        holder.CREATION_DATE.text = task.creationDate
        holder.EXPIRATION_DATE.text = task.expirationDate
        holder.AUTHOR.text = task.authorLogin

        var icon = selectPriorityIcon(task.priority)
        holder.TITLE.setCompoundDrawablesWithIntrinsicBounds(icon, 0, 0,0)

        holder.itemView.setOnClickListener {
            this.openEditTaskActivity(task)
        }
    }

    fun selectPriorityIcon(priority: Priority): Int {
        val fullStar = R.drawable.round_star_24
        val halfStar = R.drawable.round_star_half_24
        val emptyStar = R.drawable.round_star_border_24

        return when (priority) {
            Priority.LOW -> emptyStar
            Priority.MEDIUM -> halfStar
            Priority.HIGH -> fullStar
        }
    }

    fun openEditTaskActivity(task: Task) {
        val intent = Intent(context, TaskDetails::class.java)
            .apply {
                putExtra("id", task.id)
                putExtra("title", task.title)
                putExtra("priority", task.priority.value)
                putExtra("status", task.status.value)
                putExtra("description", task.description)
                putExtra("expirationDate", task.expirationDate)
                putExtra("author", task.authorLogin)
                putExtra("receiver", task.receiverLogin)
            }

        startActivity(context, intent, null)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val TITLE = itemView.findViewById<TextView>(R.id.task_title)
        val DESCRIPTION = itemView.findViewById<TextView>(R.id.task_description)
        val PRIORITY = itemView.findViewById<TextView>(R.id.task_priority)
        val STATUS = itemView.findViewById<TextView>(R.id.task_status)
        val CREATION_DATE = itemView.findViewById<TextView>(R.id.task_creation_date)
        val EXPIRATION_DATE = itemView.findViewById<TextView>(R.id.task_expiration_date)
        val AUTHOR = itemView.findViewById<TextView>(R.id.task_author_login)
    }
}