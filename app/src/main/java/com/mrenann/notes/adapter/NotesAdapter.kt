package com.mrenann.notes.adapter

import android.graphics.BitmapFactory
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mrenann.notes.databinding.ItemRvNotesBinding
import com.mrenann.notes.entities.Notes
import java.util.*
import kotlin.collections.ArrayList

class NotesAdapter() :
    RecyclerView.Adapter<NotesAdapter.NotesViewHolder>() {
    var listener: OnItemClickListener? = null
    var arrList = ArrayList<Notes>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotesViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemRvNotesBinding.inflate(layoutInflater, parent, false)
        return NotesViewHolder(binding,listener)
    }

    override fun getItemCount(): Int {
        return arrList.size
    }

    fun setData(arrNotesList: List<Notes>){
        arrList = arrNotesList as ArrayList<Notes>
    }

    fun setOnClickListener(listener1: OnItemClickListener){
        listener = listener1
    }

    override fun onBindViewHolder(holder: NotesViewHolder, position: Int) {
        holder.bind(arrList[position])
    }

    class NotesViewHolder( private val binding: ItemRvNotesBinding,private val listener: OnItemClickListener?) : RecyclerView.ViewHolder(binding.root){
        fun bind(notes: Notes) = with(binding) {
            setupValues(notes)
            binding.cardView.setOnClickListener {
                notes.id?.let { it1 -> listener?.onClicked(it1) }
            }
        }

        private fun setupValues(notes: Notes) {
            binding.apply {
                tvTitle.text = notes.title
                tvDesc.text = notes.noteText
                tvDateTime.text = notes.dateTime
            }

        }
    }

    interface OnItemClickListener{
        fun onClicked(noteId:Int)
    }

}