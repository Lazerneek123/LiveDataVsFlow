package com.example.myapplication1.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication1.MainActivity
import com.example.myapplication1.databinding.ItemUserBinding
import com.example.myapplication1.models.User

class ItemAdapter :
    ListAdapter<User, ItemAdapter.ItemHolder>(ItemHolder.ItemComparator()) {

    class ItemHolder(
        val binding: ItemUserBinding
    ) :
        RecyclerView.ViewHolder(binding.root), OnAdapterClick {

        @SuppressLint("SetTextI18n")
        fun bind(user: User) = with(binding) {
            userName.text = user.name
            userStatus.text =
                user.status + " | " + user.phoneNumber + " | " + user.age + " | " + user.onlineStatus

            floatingActionButtonDelete.setOnClickListener {
                val builder = android.app.AlertDialog.Builder(binding.root.context)
                builder.setTitle("Are you sure you want to delete this user?")
                    .setNegativeButton("Yes") { dialog, _ ->
                        (binding.root.context as MainActivity).viewModel.deleteUser(user)
                        dialog.dismiss()
                    }
                    .setPositiveButton("No") { dialog, _ ->
                        dialog.dismiss()
                    }.show()
            }

            onClick()
            onLongClick()
        }

        override fun onClick() {
            // code
        }

        override fun onLongClick() {
            binding.root.setOnLongClickListener {
                // code
                return@setOnLongClickListener true
            }
        }

        companion object {
            fun create(
                parent: ViewGroup
            ): ItemHolder {
                return ItemHolder(
                    ItemUserBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            }
        }

        class ItemComparator : DiffUtil.ItemCallback<User>() {
            override fun areItemsTheSame(oldItem: User, newItem: User): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: User, newItem: User): Boolean {
                return oldItem == newItem
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        return ItemHolder.create(parent)
    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        holder.bind(getItem(position))
    }
}