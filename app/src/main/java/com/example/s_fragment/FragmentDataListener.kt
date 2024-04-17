package com.example.s_fragment

// [3] SecondFragment -> Activity 로 보내려면 interface 가 필요함
interface FragmentDataListener {
    fun onDataReceived(data: String)
}
