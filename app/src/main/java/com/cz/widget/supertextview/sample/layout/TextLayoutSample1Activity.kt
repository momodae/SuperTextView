package com.cz.widget.supertextview.sample.layout

import android.graphics.BlurMaskFilter
import android.graphics.Color
import android.graphics.EmbossMaskFilter
import android.graphics.Typeface
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.text.Spannable
import android.view.LayoutInflater
import com.cz.widget.supertextview.library.span.*
import com.cz.widget.supertextview.library.spannable.SpannableString
import com.cz.widget.supertextview.library.style.ReplacementSpan
import com.cz.widget.supertextview.sample.R
import com.cz.widget.supertextview.sample.linedecoration.HighlightLineDecoration
import com.cz.widget.supertextview.sample.template.ToolBarActivity
import kotlinx.android.synthetic.main.activity_text_layout_sample1.*

class TextLayoutSample1Activity : ToolBarActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_text_layout_sample1)
        //初始化span列表
        val spanList= mutableMapOf<String,Any>()
        //        //添加View
        val layoutInflater = LayoutInflater.from(this)
        val imageLayout1 = layoutInflater.inflate(R.layout.image_layout1, textLayout, false)
        spanList.put("VewSpan 添加View体1", ViewSpan(imageLayout1))
        val imageLayout2 = layoutInflater.inflate(R.layout.progress_layout1, textLayout, false)
        spanList.put("VewSpan 添加View体2", ViewSpan(imageLayout2))
        val d1 = resources.getDrawable(R.mipmap.ic_launcher)
        d1.setBounds(0, 0, 100, 100)
        spanList.put("ImageSpan 图片1", ImageSpan(d1))
        spanList.put("BackgroundColorSpan", BackgroundColorSpan(Color.RED))
        spanList.put("ForegroundColorSpan", ForegroundColorSpan(Color.YELLOW))
        spanList.put("RelativeSizeSpan 相对大小（文本字体）", RelativeSizeSpan(2.5f))
        spanList.put("MaskFilterSpan 修饰效果",
            MaskFilterSpan(BlurMaskFilter(2f, BlurMaskFilter.Blur.OUTER))
        )
        spanList.put("浮雕(EmbossMaskFilter)",
            MaskFilterSpan(EmbossMaskFilter(floatArrayOf(1f, 1f, 2f), 1.5f, 8f, 3f))
        )
        spanList.put("StrikethroughSpan 删除线（中划线）", StrikethroughSpan())
        spanList.put("UnderlineSpan 下划线", UnderlineSpan())
        spanList.put("AbsoluteSizeSpan 绝对大小（文本字体）", AbsoluteSizeSpan(20, true))
        val drawableSpan = object : DynamicDrawableSpan() {
            override fun getDrawable(): Drawable {
                val d = resources.getDrawable(R.mipmap.ic_launcher)
                d.setBounds(0, 0, 100, 100)
                return d
            }
        }
        spanList.put("DynamicDrawableSpan",drawableSpan)
        spanList.put("RelativeSizeSpan 相对大小（文本字体）", RelativeSizeSpan(2f))
        spanList.put("ScaleXSpan 基于x轴缩放", ScaleXSpan(2f))
        spanList.put("StyleSpan 字体样式：粗体、斜体等", StyleSpan(Typeface.BOLD_ITALIC))
        spanList.put("SuperscriptSpan", SuperscriptSpan())
//

        val text=assets.open("dynamic_chapter1").bufferedReader().readText()
        val spannableString = SpannableString(text)
        var start=0
        spanList.entries.forEachIndexed { _, (title,spanItem)->
            var index=text.indexOf(" ",start+1)
            if(spanItem is ReplacementSpan){
                spannableString.setSpan(spanItem,index, Math.min(text.length,index+1), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
            } else {
                spannableString.setSpan(spanItem, index, Math.min(text.length,index+5), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
            }
            start=index
        }
        textLayout.setLineDecoration(HighlightLineDecoration(this))
        textLayout.setText(spannableString)
    }

}
