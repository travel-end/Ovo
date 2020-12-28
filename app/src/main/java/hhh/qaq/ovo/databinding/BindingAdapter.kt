package hhh.qaq.ovo.databinding

import androidx.appcompat.widget.AppCompatEditText
import androidx.databinding.BindingAdapter
import hhh.qaq.ovo.utils.showKeyBoard

/**
 * @By Journey 2020/12/28
 * @Description
 */

@BindingAdapter
fun editTextSelection(editText: AppCompatEditText,position:Int) {
    editText.setSelection(position)
}

@BindingAdapter
fun editShowKeyboard(editText: AppCompatEditText,isShow:Boolean){
    editText.showKeyBoard()
}