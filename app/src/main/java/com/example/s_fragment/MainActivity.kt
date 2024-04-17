package com.example.s_fragment

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import com.example.s_fragment.databinding.ActivityMainBinding

// FragmentDataListener 를 상속받아 onDataReceived() 메소드 override 해 줘야 함
class MainActivity : AppCompatActivity(), FragmentDataListener {
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        // apply, run 사용해서 코드 가독성 높일 수 있음, binding. 을 일일히 안 붙여도 되게 하는 듯?
        binding.run {
            fragment1Btn.setOnClickListener {
                val dataToSend = "Hello First Fragment: \n From Activity"
                val fragment = FirstFragment.newInstance(dataToSend)
                setFragment(fragment)
            }
            fragment2Btn.setOnClickListener {
                val dataToSend = "Hello Second Fragment: \n From Activity"
                val fragment = SecondFragment.newInstance(dataToSend)
                setFragment(fragment)
            }
        }
        setFragment(FirstFragment()) // 프로그램이 시작될 때 첫 화면으로 뜰 프래그먼트 화면 뿌리기
    }

    private fun setFragment(frag: Fragment) {
        supportFragmentManager.commit {
            replace(R.id.frameLayout, frag) // 인수로 받은 프래그먼트를 R.id.frameLayout 에 replace 함
            setReorderingAllowed(true)
            addToBackStack("")
        }
    }

    // [3] SecondFragment -> Activity
    override fun onDataReceived(data: String) {
        // Fragment에서 받은 데이터 처리
        Toast.makeText(this, data, Toast.LENGTH_SHORT).show()
    }
}