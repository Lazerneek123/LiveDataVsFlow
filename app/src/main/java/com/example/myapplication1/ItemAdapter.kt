package com.example.myapplication1

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication1.databinding.ItemImageBinding
import com.example.myapplication1.databinding.ItemUserBinding
import com.example.myapplication1.models.Image
import com.example.myapplication1.models.User


class ItemAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var list = ArrayList<InterfaceAdapter>()

    class ItemUserHolder(
        private val binding: ItemUserBinding
    ) :
        RecyclerView.ViewHolder(binding.root), OnMessageClick {

        @SuppressLint("SetTextI18n")
        fun bind(user: InterfaceAdapter) = with(binding) {
            user as User
            userName.text = user.name
            userStatus.text = user.status + " | " + user.email + " | " + user.onlineStatus
            imageUser.setImageResource(R.drawable.ic_launcher_foreground)

            onClick()
            onLongClick()
        }

        override fun onClick() {
            binding.root.setOnClickListener {

            }
        }

        override fun onLongClick() {
            binding.root.setOnLongClickListener {
                return@setOnLongClickListener true
            }
        }
    }

    class ItemImageHolder(
        private val binding: ItemImageBinding
    ) :
        RecyclerView.ViewHolder(binding.root), OnMessageClick {

        fun bind(image: InterfaceAdapter) = with(binding) {
            image as Image
            imageOwner.text = image.name
            imageTime.text = image.time
            imageUser.setImageResource(R.mipmap.ic_time)

            onClick()
            onLongClick()
        }

        override fun onClick() {
            binding.root.setOnClickListener {

            }
        }

        override fun onLongClick() {
            binding.root.setOnLongClickListener {
                return@setOnLongClickListener true
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            InterfaceAdapter.USER_TYPE -> {
                val binding = ItemUserBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                ItemUserHolder(binding)
            }
            InterfaceAdapter.IMAGE_TYPE -> {
                val binding = ItemImageBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                ItemImageHolder(binding)
            }
            else -> throw IllegalArgumentException()
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (list[position].getType()) {
            InterfaceAdapter.USER_TYPE ->
                (holder as ItemUserHolder).bind(list[position])
            InterfaceAdapter.IMAGE_TYPE ->
                (holder as ItemImageHolder).bind(list[position])
        }
    }

    override fun getItemViewType(position: Int): Int {
        return list[position].getType()
    }

    override fun getItemCount() = list.size

    fun setList(listUser: ArrayList<InterfaceAdapter>) {
        list = listUser
    }
}