package com.example.s_fragment

import android.content.Context
import android.location.GnssAntennaInfo.Listener
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.s_fragment.databinding.FragmentFirstBinding
import com.example.s_fragment.databinding.FragmentSecondBinding
import java.lang.RuntimeException

private const val ARG_PARAM1 = "param1"

class SecondFragment : Fragment() {
    private var param1: String? = null

    private var _binding: FragmentSecondBinding? = null
    private val  binding get() = _binding!!

    // [3] 에 필요
    private var listener: FragmentDataListener? = null

    // onAttach() MainActivity 와 SecondFragment 를 연결시키기 위함
    // context 는 MainAcitivy 에서 온 것임
    override fun onAttach(context: Context) {
        super.onAttach(context)

        // [3] SecondFragment -> Activity
        if (context is FragmentDataListener) { // is 구문은 타입을 체크함, Main 안에 FragmentDataListener 가 있는지 확인
            listener = context
        } else {
            throw RuntimeException("$context must implement FragmentDataListener")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSecondBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // [2] Fragment -> Fragment
        binding.tvSecondFragment.text = param1

        // [3] SecondFragment -> Activity
        binding.sendActivity.setOnClickListener {
            val dataToSend = "Hello from SecondFragment" // 전달할 데이터
            listener?.onDataReceived(dataToSend)
        // listener 는 main 에서 온 context 가 할당되어 있음, onDataReceived() 는 Main 상속받고 있고 거기로 전달됨
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String) =
            // [1] Activity -> FirstFragment
            SecondFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                }
            }

    }

    override fun onDestroy() {
        super.onDestroy()
        // Binding 객체 해제
        _binding = null
        listener = null
    }
}