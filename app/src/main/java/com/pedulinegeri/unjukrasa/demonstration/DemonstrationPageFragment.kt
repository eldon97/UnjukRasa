package com.pedulinegeri.unjukrasa.demonstration

import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.tabs.TabLayoutMediator
import com.pedulinegeri.unjukrasa.R
import com.pedulinegeri.unjukrasa.databinding.FragmentDemonstrationPageBinding
import com.pedulinegeri.unjukrasa.demonstration.discussion.DiscussionListAdapter
import com.pedulinegeri.unjukrasa.demonstration.module.ProgressListAdapter
import com.pedulinegeri.unjukrasa.demonstration.person.AddPersonBottomSheetDialog
import com.pedulinegeri.unjukrasa.demonstration.person.PersonListAdapter


class DemonstrationPageFragment : Fragment() {

    private var fragmentBinding: FragmentDemonstrationPageBinding? = null

    private lateinit var participateBottomSheetDialog: ParticipateBottomSheetDialog
    private lateinit var addPersonBottomSheetDialog: AddPersonBottomSheetDialog

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragmentBinding = FragmentDemonstrationPageBinding.inflate(inflater, container, false)
        return fragmentBinding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = fragmentBinding!!

        binding.toolbar.setNavigationOnClickListener { view ->
            requireActivity().onBackPressed()
        }

        binding.vpImages.adapter = DemonstrationImageAdapter(listOf(R.drawable.cat_caviar, R.drawable.cat_caviar, R.drawable.cat_caviar, R.drawable.cat_caviar))
        TabLayoutMediator(binding.intoTabLayout, binding.vpImages) { _, _ ->}.attach()

        binding.rvDiscussion.apply {
            this.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            this.adapter = DiscussionListAdapter(arrayListOf("abcde", "abcde", "abcde", "abcde", "abcde", "abcde"))
        }

        binding.nsv.setOnScrollChangeListener { _, _, scrollY, _, oldScrollY ->
            if (scrollY > oldScrollY) {
                binding.fabUpvote.hide()
                binding.fabDownvote.hide()
                binding.fabShare.hide()
                binding.fabParticipate.hide()
            } else if (scrollY < oldScrollY || scrollY <= 0) {
                binding.fabUpvote.show()
                binding.fabDownvote.show()
                binding.fabShare.show()
                binding.fabParticipate.show()
            }
        }

        binding.rvProgress.apply {
            this.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            this.adapter = ProgressListAdapter(arrayListOf("abcde", "abcde", "abcde", "abcde", "abcde", "abcde"))
        }

        binding.rvPerson.apply {
            this.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            this.adapter = PersonListAdapter(arrayListOf("Inisiator", "Koordinator", "Dukung", "Ikut", "Koordinator", "Koordinator"))
        }

        binding.fabParticipate.setOnClickListener {
            if (!this::participateBottomSheetDialog.isInitialized) {
                participateBottomSheetDialog = ParticipateBottomSheetDialog()
            }

            participateBottomSheetDialog.show(parentFragmentManager, "ParticipateBottomSheet")
        }

        binding.fabShare.setOnClickListener {
            val sendIntent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, "This is my text to send.")
                type = "text/plain"
            }

            val shareIntent = Intent.createChooser(sendIntent, null)
            startActivity(shareIntent)
        }

        binding.fabUpvote.setOnClickListener {
            Toast.makeText(requireContext(), "Terima kasih telah mendukung!", Toast.LENGTH_SHORT).show()
        }

        binding.fabDownvote.setOnClickListener {
            Toast.makeText(requireContext(), "Anda sudah menolak.", Toast.LENGTH_SHORT).show()
        }

        binding.toolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.action_edit -> {
                    findNavController().navigate(R.id.action_demonstrationPageFragment_to_newDemonstrationPageFragment)
                }
                R.id.action_add_person -> {
                    addPersonBottomSheetDialog = AddPersonBottomSheetDialog()
                    addPersonBottomSheetDialog.show(parentFragmentManager, "AddPersonBottomSheet")
                }
            }

            return@setOnMenuItemClickListener true
        }

        binding.cvAddProgress.setOnClickListener {
            findNavController().navigate(R.id.action_demonstrationPageFragment_to_addProgressPageFragment)
        }
    }

    override fun onDestroyView() {
        fragmentBinding = null
        super.onDestroyView()
    }
}