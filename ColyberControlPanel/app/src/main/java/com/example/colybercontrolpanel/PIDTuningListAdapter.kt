package com.example.colybercontrolpanel

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import kotlin.math.round

class PIDTuningListAdapter(context: Context, resource: Int, textViewResourceId: Int, objects: Array<String>) : ArrayAdapter<String>(context, resource, textViewResourceId, objects) {
    private val layout: Int = resource

    @SuppressLint("ViewHolder") // TODO: suppressed [...].inflate(layout, parent, false). Fix this
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        super.getView(position, convertView, parent)

        if (convertView != null)
            return convertView

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

        setUpOnClicks(viewHolder)

        return convertView
    }

    private fun setUpOnClicks(viewHolder: PIDViewHolder) {
        // Some helper functions

        fun getMin(): Float {
            return viewHolder.minValueEditText.text.toString().toFloat()
        }

        fun getMax(): Float {
            return viewHolder.maxValueEditText.text.toString().toFloat()
        }

        fun getEditTextValue(): Float {
            return viewHolder.valueEditText.text.toString().toFloat()
        }

        fun getSeekBarValue(): Float {
            val seekBarPercent: Float = viewHolder.valueSeekBar.progress.toFloat() / viewHolder.valueSeekBar.max
            val dist = getMax() - getMin()
            return (seekBarPercent * dist) + getMin()
        }

        fun updateSeekBarFromEditTexts() {
            val newValue = valueToPercent(getEditTextValue(), getMin(), getMax()) * viewHolder.valueSeekBar.max
            viewHolder.valueSeekBar.progress = round(newValue).toInt()
        }

        fun updateValueEditTextFromSeekBar() {
            val newValue = getSeekBarValue()
            viewHolder.valueEditText.setText((round(newValue * 100) / 100).toString())
        }


        // Set 0 CheckBox
        viewHolder.setZeroCheckBox.setOnClickListener {
            val isNotChecked = !viewHolder.setZeroCheckBox.isChecked
            viewHolder.nameTextView.isEnabled = isNotChecked
            viewHolder.valueEditText.isEnabled = isNotChecked
            viewHolder.increaseButton.isEnabled = isNotChecked
            viewHolder.decreaseButton.isEnabled = isNotChecked
            viewHolder.valueSeekBar.isEnabled = isNotChecked
            viewHolder.minValueEditText.isEnabled = isNotChecked
            viewHolder.maxValueEditText.isEnabled = isNotChecked
        }

        // Increase Button
        viewHolder.increaseButton.setOnClickListener {
            viewHolder.valueSeekBar.progress += 1
        }

        // Decrease Button
        viewHolder.decreaseButton.setOnClickListener {
            viewHolder.valueSeekBar.progress -= 1
        }

        // Value SeekBar
        viewHolder.valueSeekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onStartTrackingTouch(p0: SeekBar?) {}
            override fun onStopTrackingTouch(p0: SeekBar?) {}

            override fun onProgressChanged(p0: SeekBar?, progress: Int, p2: Boolean) {
                updateValueEditTextFromSeekBar()
            }
        })

        // Min value EditText
        viewHolder.minValueEditText.onFocusChangeListener =
            View.OnFocusChangeListener { _, _ ->
                val newMin = viewHolder.minValueEditText.text.toString().toFloat()
                val max = viewHolder.maxValueEditText.text.toString().toFloat()
                if (newMin >= max)
                    viewHolder.minValueEditText.setText((max - 1).toString())
                updateSeekBarFromEditTexts()
            }

        // Max value EditText
        viewHolder.maxValueEditText.onFocusChangeListener =
            View.OnFocusChangeListener { _, _ ->
                val newMax = viewHolder.maxValueEditText.text.toString().toFloat()
                val min = viewHolder.minValueEditText.text.toString().toFloat()
                if (newMax <= min)
                    viewHolder.maxValueEditText.setText((min + 1).toString())
                updateSeekBarFromEditTexts()
            }
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


    companion object {
        // [0.0 - 1.0]
        fun valueToPercent(value: Float, min: Float, max: Float): Float {
            if (max <= min)
                throw Exception("Max have to be greater than min!")
            return (value - min) / (max - min)
        }
    }

}
