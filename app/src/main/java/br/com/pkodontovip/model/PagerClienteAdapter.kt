package br.com.pkodontovip.model

import android.support.v4.view.PagerAdapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import br.com.pkodontovip.R

class PagerClienteAdapter (private val list: List<String>) : PagerAdapter() {

    override fun isViewFromObject(v: View, `object`: Any): Boolean {
        // Return the current view
        return v === `object` as View
    }


    override fun getCount(): Int {
        // Count the items and return it
        return list.size
    }


    override fun instantiateItem(parent: ViewGroup, position: Int): Any {
        // Get the view from pager page layout
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.page_layout, parent, false)

        // Get the widgets reference from layout
        val tvLower: TextView = view.findViewById(R.id.tv_lower)

        // Set the text views text
        tvLower.text = list.get(position)

        // Add the view to the parent
        parent.addView(view)

        // Return the view
        return view
    }


    override fun destroyItem(parent: ViewGroup, position: Int, `object`: Any) {
        // Remove the view from view group specified position
        parent.removeView(`object` as View)
    }
}
