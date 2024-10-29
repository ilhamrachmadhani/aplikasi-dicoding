package com.dicoding.dicodingevents.ui.finished

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.dicodingevents.EventAdapter
import com.dicoding.dicodingevents.data.response.ListEventsItem
import com.dicoding.dicodingevents.databinding.FragmentFinishedBinding

class FinishedFragment : Fragment() {
    companion object {
    }

    private lateinit var fragmentFinishedBinding: FragmentFinishedBinding
    private val finishedViewModel: FinishedViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        fragmentFinishedBinding = FragmentFinishedBinding.inflate(layoutInflater, container, false)
        return fragmentFinishedBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        finishedViewModel.listEvent.observe(viewLifecycleOwner){ eventData ->
            setFinishedEventData(eventData)
        }
        finishedViewModel.isLoading.observe(viewLifecycleOwner){
            showLoading(it)
        }
        finishedViewModel.toastText.observe(viewLifecycleOwner){ toastText ->
            Toast.makeText(activity, toastText, Toast.LENGTH_SHORT).show()
        }

        val layoutManager = LinearLayoutManager(requireContext())
        fragmentFinishedBinding.rvFinishedEvent.layoutManager = layoutManager
    }

    private fun setFinishedEventData(eventData: List<ListEventsItem>){
        val adapter = EventAdapter()
        adapter.submitList(eventData)
        fragmentFinishedBinding.rvFinishedEvent.adapter = adapter
    }

    private fun showLoading(isLoading: Boolean){
        fragmentFinishedBinding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}