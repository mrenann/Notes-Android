package com.mrenann.notes.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.mrenann.notes.R
import com.mrenann.notes.database.NotesDatabase
import com.mrenann.notes.databinding.FragmentCreateNoteBinding
import com.mrenann.notes.databinding.FragmentHomeBinding
import com.mrenann.notes.entities.Notes
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class CreateNoteFragment : BaseFragment() {
    private lateinit var binding: FragmentCreateNoteBinding
    var currentDate:String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentCreateNoteBinding.inflate(layoutInflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val sdf = SimpleDateFormat("dd/M/yyyy hh:mm:ss")
        currentDate = sdf.format(Date())

        binding.apply {
            tvDateTime.text = currentDate

            imgDone.setOnClickListener {
                saveNote()
            }

            imgBack.setOnClickListener {
                replaceFragment(HomeFragment(),false)
            }

        }

    }

    private fun saveNote() {
        binding.apply {
            when {
                etNoteTitle.text.isNullOrEmpty() -> Toast.makeText(context,"Note Title is Required", Toast.LENGTH_SHORT).show()
                etNoteSubTitle.text.isNullOrEmpty() -> Toast.makeText(context,"Note Sub Title is Required",Toast.LENGTH_SHORT).show()
                etNoteDesc.text.isNullOrEmpty() -> Toast.makeText(context,"Note Description is Required",Toast.LENGTH_SHORT).show()
                else -> {
                    launch {
                        var notes = Notes()
                        notes.title = etNoteTitle.text.toString()
                        notes.subTitle = etNoteSubTitle.text.toString()
                        notes.noteText = etNoteDesc.text.toString()
                        notes.dateTime = currentDate
                        /*notes.color = selectedColor
                        notes.imgPath = selectedImagePath
                        notes.webLink = webLink*/
                        context?.let {
                            NotesDatabase.getDatabase(it).noteDao().insertNotes(notes)
                            etNoteTitle.setText("")
                            etNoteSubTitle.setText("")
                            etNoteDesc.setText("")
                            layoutImage.visibility = View.GONE
                            imgNote.visibility = View.GONE
                            tvWebLink.visibility = View.GONE
                            requireActivity().supportFragmentManager.popBackStack()
                        }
                    }
                }
            }
        }
    }

    private fun replaceFragment(fragment:Fragment, istransition:Boolean){
        val fragmentTransition = activity?.supportFragmentManager?.beginTransaction()

        if (istransition){
            fragmentTransition?.setCustomAnimations(android.R.anim.slide_out_right,android.R.anim.slide_in_left)
        }
        fragmentTransition?.replace(R.id.frame_layout,fragment)?.addToBackStack(fragment.javaClass.simpleName)?.commit()
    }
}