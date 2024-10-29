package com.dicoding.dicodingevents.ui.upcoming

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
import com.dicoding.dicodingevents.databinding.FragmentUpcomingBinding

class UpcomingFragment : Fragment() {
    companion object {
    }

    private lateinit var fragmentUpcomingBinding: FragmentUpcomingBinding
    private val UpcomingViewModel: UpcomingViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        fragmentUpcomingBinding = FragmentUpcomingBinding.inflate(layoutInflater, container, false)
        return fragmentUpcomingBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        UpcomingViewModel.listEvent.observe(viewLifecycleOwner){ eventData ->
            setUpcomingEventData(eventData)
        }
        UpcomingViewModel.isLoading.observe(viewLifecycleOwner){
            showLoading(it)
        }
        UpcomingViewModel.toastText.observe(viewLifecycleOwner){ toastText ->
            Toast.makeText(activity, toastText, Toast.LENGTH_SHORT).show()
        }

        val layoutManager = LinearLayoutManager(requireContext())
        fragmentUpcomingBinding.rvUpcomingEvent.layoutManager = layoutManager
    }

    private fun setUpcomingEventData(eventData: List<ListEventsItem>){
        val adapter = EventAdapter()
        adapter.submitList(eventData)
        fragmentUpcomingBinding.rvUpcomingEvent.adapter = adapter
    }

    private fun showLoading(isLoading: Boolean){
        fragmentUpcomingBinding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}