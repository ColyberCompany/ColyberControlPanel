package com.example.colybercontrolpanel

import android.annotation.SuppressLint
import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*

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

        setOnClicks(viewHolder)

        return convertView
    }

    private fun setOnClicks(viewHolder: PIDViewHolder) {
        // Value EditText
        viewHolder.valueEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(p0: Editable?) {
                Log.e("asdf", "value + ${viewHolder.valueEditText.text}")
            }

        })

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
            override fun onProgressChanged(p0: SeekBar?, progress: Int, p2: Boolean) {
                val min = viewHolder.minValueEditText.text.toString().toFloat()
                val max = viewHolder.maxValueEditText.text.toString().toFloat()
                viewHolder.valueEditText.setText(seekBarToValue(viewHolder.valueSeekBar, min, max).toString())
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {
            }

            override fun onStopTrackingTouch(p0: SeekBar?) {
            }

        })

        // Min value EditText

        // Max value EditText

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

    fun seekBarToValue(seekBar: SeekBar, min: Float, max: Float): Float {
        val seekBarMax = seekBar.max
        val seekBarPercent: Float = seekBar.progress.toFloat() / seekBarMax
        val dist = max - min
        return (seekBarPercent * dist) + min
    }
}
