package com.akmalinnuha.sqlitedemo

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class StudentAdapter : RecyclerView.Adapter<StudentAdapter.StudentViewHolder>() {
    private var stdList: ArrayList<StudentModel> = ArrayList()
    private var onClickItem : ((StudentModel) -> Unit)? = null
    private var onDeleteClickItem : ((StudentModel) -> Unit)? = null
    class StudentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var id = itemView.findViewById<TextView>(R.id.tvId)
        private var name = itemView.findViewById<TextView>(R.id.tvName)
        private var email = itemView.findViewById<TextView>(R.id.tvEmail)
        var btnDelete = itemView.findViewById<ImageButton>(R.id.btnDelete)

        fun bindView(std: StudentModel) {
            id.text = std.id.toString()
            name.text = std.name
            email.text = std.email
        }
    }

    fun addItems(items: ArrayList<StudentModel>) {
        this.stdList = items
        notifyDataSetChanged()
    }

    fun setOnclickItem(callback : (StudentModel) -> Unit) {
        this.onClickItem = callback
    }
    fun setOnclickDeleteItem(callback : (StudentModel) -> Unit) {
        this.onDeleteClickItem = callback
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = StudentViewHolder (
        LayoutInflater.from(parent.context).inflate(R.layout.cards_items_std, parent,false)
    )

    override fun onBindViewHolder(holder: StudentViewHolder, position: Int) {
        val std = stdList[position]
        holder.bindView(std)
        holder.itemView.setOnClickListener { onClickItem?.invoke(std) }
        holder.btnDelete.setOnClickListener { onDeleteClickItem?.invoke(std) }
    }

    override fun getItemCount(): Int {
        return stdList.size
    }
}