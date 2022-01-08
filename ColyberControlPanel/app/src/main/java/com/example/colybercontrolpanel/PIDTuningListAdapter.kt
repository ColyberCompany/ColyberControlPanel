package com.example.colybercontrolpanel

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*

class PIDTuningListAdapter(context: Context, resource: Int, textViewResourceId: Int, objects: Array<String>) : ArrayAdapter<String>(context, resource, textViewResourceId, objects) {
    private val layout: Int = resource

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        super.getView(position, convertView, parent)

        val convertView = LayoutInflater.from(context).inflate(layout, parent, false)

        val viewHolder = PIDViewHolder(
            convertView.findViewById(R.id.pidPartName),
            convertView.findViewById(R.id.pidValueEditText),
            convertView.findViewById(R.id.pidSet0CheckBox),
            convertView.findViewById(R.id.pidIncreaseButton),
            convertView.findViewById(R.id.pidDecreaseButton),
            convertView.findViewById(R.id.pidValueSeekBar),
            convertView.findViewById(R.id.pidMinValueEditText),
            convertView.findViewById(R.id.pidMaxValueEditText)
        )

        convertView.tag = viewHolder

        // set pid controller name
        viewHolder.nameTextView.text = getItem(position)

        setOnClicks(viewHolder)

        return convertView
    }

    private fun setOnClicks(viewHolder: PIDViewHolder) {
        // Set 0 CheckBox
        viewHolder.setZeroCheckBox.setOnClickListener(View.OnClickListener {
            val isNotChecked = !viewHolder.setZeroCheckBox.isChecked
            viewHolder.nameTextView.isEnabled = isNotChecked
        })
    }


    data class PIDViewHolder (
        val nameTextView: TextView,
        val valueEditText: EditText,
        val setZeroCheckBox: CheckBox,
        val increaseButton: Button,
        val decreaseButton: Button,
        val valueSeekBar: SeekBar,
        val minValueEditText: EditText,
        val maxValueEditText: EditText
    )
}
