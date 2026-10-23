package com.example.lin_new_project_kotlin.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.lin_new_project_kotlin.databinding.ItemuserBinding
import com.example.lin_new_project_kotlin.room.entity.UserProfileEntity
import com.google.gson.Gson

class UserAdapter(
    private val context: Context?,
    private var array: MutableList<UserProfileEntity?>
) : RecyclerView.Adapter<RecyclerView.ViewHolder?>() {
    var isReverse: Boolean = false

    private var onItemClickListener: OnItemClickListener? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = ItemuserBinding.inflate(
            LayoutInflater.from(parent.getContext()), parent,
            false
        )
        return MyRecyclerViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val holder1 = holder as MyRecyclerViewHolder
        val ii = position + 1
        holder1.binding.tvText.setText(ii.toString() + "." + Gson().toJson(array.get(position)))
    }

    override fun getItemCount(): Int {
        return array.size
    }

    inner class MyRecyclerViewHolder internal constructor(var binding: ItemuserBinding) :
        RecyclerView.ViewHolder(binding.getRoot())
    {
        init {

            //            getBindingAdapterPosition() 得到的是元素位於當前綁定Adapter的位置，
//            而getAbsoluteAdapterPosition()方法得到的是元素位於合併後Adapter的絕對位置。
            binding.getRoot().setOnClickListener(View.OnClickListener { v: View? ->
                if (onItemClickListener != null) {
                    if (getAdapterPosition() >= RecyclerView.NO_POSITION) {
                        onItemClickListener!!.OnItemClick(array.get(getAdapterPosition()))
                    }
                }
            })
        }
    }

    fun setArray(array: MutableList<UserProfileEntity?>) {
        this.array = array
        notifyDataSetChanged()
    }

    fun setOnItemClickListener(onItemClickListener: OnItemClickListener?) {
        this.onItemClickListener = onItemClickListener
    }

    interface OnItemClickListener {
        /**
         * 介面中的點選每一項的實現方法，引數自己定義
         */
        fun OnItemClick(userProfileEntity: UserProfileEntity?)
    }
}
