package com.example.s_fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.s_fragment.databinding.FragmentFirstBinding

// 데이터 이름을 갖고 있어야 함
private const val ARG_PARAM1 = "param1"

class FirstFragment : Fragment() {
    private var param1: String? = null // 전역 변수
    private val binding by lazy { FragmentFirstBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // let 스코프 함수 null일 수도 있어서 사용 (null 이 아닐 때만)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1) // it 얘가 bundle 이세여
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // [1] Activity -> FirstFragment
        binding.tvFirstFragment.text = param1

        // [2] Fragment -> Fragment
        binding.goFrag2.setOnClickListener{
            val dataToSend = "Hello Fragment2! \n From Fragment1"
            val fragment2 = SecondFragment.newInstance(dataToSend)

            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.frameLayout, fragment2)
                .addToBackStack(null)
                .commit()
        }
    }

    // 데이터 받는 코드
    // newInstance를 만듦
    companion object {
        @JvmStatic
        fun newInstance(param1: String) =
            // [1] Activity -> FirstFragment
            FirstFragment().apply {
                arguments = Bundle().apply { // argument에 bundle로 데이터 넣음
                    putString(ARG_PARAM1, param1)
                }
            }
    }
}