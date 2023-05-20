package com.example.coffee

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Switch
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.coffee.databinding.ListItemBinding
import kotlinx.coroutines.NonDisposableHandle.parent

class ListAdapter(private val context: Context):
    RecyclerView.Adapter<ListAdapter.ViewHolder>() {


    // (1) 아이템 레이아웃과 결합
    var datas = mutableListOf<ListItem>()

    interface OnItemClickListener{
        fun onItemClick(v:View,data:ListItem,pos:Int)
    }
    private var listener:OnItemClickListener?=null

    fun setOnItemClickListener(listener: OnItemClickListener){
        this.listener=listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val view = LayoutInflater.from(context).inflate(R.layout.list_item, parent, false)

        val cl_btn=view.findViewById<Button>(R.id.close_btn)

        cl_btn.setOnClickListener {
        }

        return ViewHolder(view)
    }
    // (2) 리스트 내 아이템 개수
    override fun getItemCount(): Int {
        return datas.size
    }


    // (3) View에 내용 입력
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
       holder.bind(datas[position])

    }
    // (4) 레이아웃 내 View 연결
    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view) {

        private val name: TextView = itemView.findViewById(R.id.name)
        private val time: TextView = itemView.findViewById(R.id.time)
        private val date: TextView = itemView.findViewById(R.id.date)
        private val cl_btn: Button = itemView.findViewById(R.id.close_btn)
        private val al_switch:Switch=itemView.findViewById(R.id.al_switch)



        fun bind(item:ListItem){
            name.text=item.name
            time.text=item.time
            date.text=item.date

            val pos=adapterPosition
            if(pos!=RecyclerView.NO_POSITION)
            {
                itemView.setOnClickListener{
                    listener?.onItemClick(itemView,item,pos)
                }
            }
            cl_btn.setOnClickListener {
                datas.removeAt(pos)
                notifyDataSetChanged()
            }
        }
    }
}