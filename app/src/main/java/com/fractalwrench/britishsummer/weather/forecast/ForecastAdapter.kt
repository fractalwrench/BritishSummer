package com.fractalwrench.britishsummer.weather.forecast

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.fractalwrench.britishsummer.R
import com.fractalwrench.britishsummer.convertFromUnixToJavaEpoch
import com.fractalwrench.britishsummer.response.WeatherPrediction
import java.util.Date

class ForecastAdapter(private val dataset: List<WeatherPrediction>)
    : RecyclerView.Adapter<ForecastAdapter.ViewHolder>() {

    class ViewHolder(val textView: TextView) : RecyclerView.ViewHolder(textView)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ForecastAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.forecast_view, parent, false)

        val textView = view.findViewById(R.id.forecast_text) as TextView
        return ViewHolder(textView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = dataset[position]
        holder.textView.text = Date(data.dt!!.convertFromUnixToJavaEpoch()).toString()
    }

    override fun getItemCount() = dataset.size
}