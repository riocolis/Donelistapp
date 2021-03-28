package com.example.donelistapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.example.donelistapp.R
import com.example.donelistapp.model.Todo

class TodoAdapter (context: Context, val todos : ArrayList<Todo>) : RecyclerView.Adapter<TodoAdapter.TodoViewHolder>() {

    val ctx = context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.list_textview, parent, false)
        return TodoViewHolder(v)
    }

    override fun getItemCount(): Int {
        return todos.size
    }

    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
        holder.textView.text = todos[position].description

    }
    inner class TodoViewHolder(view: View ) : RecyclerView.ViewHolder(view){
        var textView : AppCompatTextView = view.findViewById(R.id.txtListTodoName)
    }
}