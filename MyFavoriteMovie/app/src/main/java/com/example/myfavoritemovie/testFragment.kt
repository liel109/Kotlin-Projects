package com.example.myfavoritemovie

import android.app.TimePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.myfavoritemovie.databinding.TestBinding
import java.util.Locale


class testFragment : Fragment() {
    private var _binding: TestBinding? = null
    private val binding get() = _binding!!

    var selectedHours: Int = 0
    var selectedMinutes: Int = 0
    var str : String = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {



        _binding = TestBinding.inflate(inflater, container, false)


        binding.buttonTest.setOnClickListener {
            val timePickerDialog = TimePickerDialog(
                requireContext(),
                TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute ->
                    // Save the selected time
                    selectedHours = hourOfDay
                    selectedMinutes = minute

                    // Display the selected time to the user
                    val timeString = String.format(Locale.getDefault(), "%02d:%02d", hourOfDay, minute)
                    binding.textTest.text = String.format(Locale.getDefault(), "%02d:%02d", hourOfDay, minute)
                    Toast.makeText(requireContext(), "Selected Time: $timeString", Toast.LENGTH_SHORT).show()
                },
                0, // Initial hour
                0, // Initial minute
                true // 24-hour format
            )

            timePickerDialog.show()
        }

        return binding.root




    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}