package com.example.todolist.ui.recyclerViewAdapters

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.todolist.model.Task
import com.example.todolist.R
import com.example.todolist.enums.Priority
import com.example.todolist.ui.manager.MyFragmentManager
import com.example.todolist.ui.task_details.TaskDetailsFragment

class TaskListAdapter(private val context: Context, private var tasks: List<Task>) : RecyclerView.Adapter<TaskListAdapter.ViewHolder>() {



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
        holder.AUTHOR.text = task.author.login

        val icon = selectPriorityIcon(task.priority)
        holder.TITLE.setCompoundDrawablesWithIntrinsicBounds(icon, 0, 0,0)

        holder.itemView.setOnClickListener {
            this.openEditTaskActivity(task)
        }
    }

    private fun selectPriorityIcon(priority: Priority): Int {
        val fullStar = R.drawable.round_star_24
        val halfStar = R.drawable.round_star_half_24
        val emptyStar = R.drawable.round_star_border_24

        return when (priority) {
            Priority.LOW -> emptyStar
            Priority.MEDIUM -> halfStar
            Priority.HIGH -> fullStar
        }
    }

    private fun openEditTaskActivity(task: Task) {
        val myFragmentManager = MyFragmentManager(context as FragmentActivity, R.id.fragment_container)
        val fragment = TaskDetailsFragment()
        val bundle = Bundle()
        bundle.putInt("id", task.id!!)
        fragment.arguments = bundle
        myFragmentManager.replaceWithSubFragment(fragment)
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