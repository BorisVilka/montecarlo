package com.monte.carlo

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.monte.carlo.databinding.ListItemBinding
import java.util.*

class SpinAdapter: RecyclerView.Adapter<SpinAdapter.MyViewHolder>(


) {

    private val arr: Array<Int> = arrayOf(R.mipmap.it1_foreground,R.mipmap.it2_foreground,R.mipmap.it3_foreground,R.mipmap.it4_foreground)
    private val random: Random = Random()

    class MyViewHolder(
        public val binding: ListItemBinding
    ) :
        RecyclerView.ViewHolder(binding.root) {
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(ListItemBinding.bind(LayoutInflater.from(parent.context).inflate(R.layout.list_item,parent,false)))
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.binding.icon.setImageResource(arr[random.nextInt(arr.size)])
    }

    override fun getItemCount(): Int {
        return 1100;
    }
}