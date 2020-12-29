package hhh.qaq.ovo.databinding

import android.graphics.drawable.Drawable
import androidx.databinding.ObservableField

/**
 * @By Journey 2020/12/28
 * @Description
 */
class CommonTitleModel(
    var leftText:String?=null,
    var rightText:String?=null,
    var rightAction:(()->Unit)?=null
)

//class TextIconModel(
//    var leftText: String = "",
//    var rightDrawable:Drawable?=null
//){
//    var mTitle = ObservableField(leftText)
//    var mRightDrawable = ObservableField(rightDrawable)
//}