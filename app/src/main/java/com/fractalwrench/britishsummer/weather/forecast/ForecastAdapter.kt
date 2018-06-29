package com.fractalwrench.britishsummer.weather.forecast

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.fractalwrench.britishsummer.R

class WeatherForecastAdapter(private val dataset: Array<String>)
    : RecyclerView.Adapter<WeatherForecastAdapter.ViewHolder>() {

    class ViewHolder(val textView: TextView) : RecyclerView.ViewHolder(textView)

    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): WeatherForecastAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.forecast_view, parent, false)

        val textView = view.findViewById(R.id.forecast_text) as TextView
        return ViewHolder(textView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.textView.text = dataset[position]
    }

    override fun getItemCount() = dataset.size
}