package com.demo.personalfinancemanager.ui.screens.home.components

import android.graphics.Color as AndroidColor
import android.content.Context
import android.widget.TextView
import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.demo.personalfinancemanager.data.model.BalanceDataPoint
import com.demo.personalfinancemanager.data.model.ChartPeriod
import com.demo.personalfinancemanager.ui.theme.ChartLine
import com.demo.personalfinancemanager.R
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.ValueFormatter
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.components.MarkerView
import com.github.mikephil.charting.utils.MPPointF
import kotlin.math.max
import kotlin.math.min
import java.text.NumberFormat
import java.util.Locale

/**
 * Chart card component displaying balance history over time
 * Uses MPAndroidChart for the line chart visualization
 * 
 * @param balanceHistory List of balance data points to display
 * @param selectedPeriod Currently selected time period
 * @param onPeriodSelected Callback when a time period is selected
 */
@Composable
fun ChartCard(
    balanceHistory: List<BalanceDataPoint>,
    selectedPeriod: ChartPeriod,
    onPeriodSelected: (ChartPeriod) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp)
            .height(280.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = androidx.compose.ui.graphics.Color(0xFF1A1A1A)
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            // Chart visualization
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {
                if (balanceHistory.isNotEmpty()) {
                    BalanceLineChart(balanceHistory = balanceHistory)
                }
            }
            
            // Period selector buttons
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                PeriodButton("1D", selectedPeriod == ChartPeriod.ONE_DAY) { onPeriodSelected(ChartPeriod.ONE_DAY) }
                PeriodButton("5D", selectedPeriod == ChartPeriod.FIVE_DAYS) { onPeriodSelected(ChartPeriod.FIVE_DAYS) }
                PeriodButton("1M", selectedPeriod == ChartPeriod.ONE_MONTH) { onPeriodSelected(ChartPeriod.ONE_MONTH) }
                PeriodButton("3M", selectedPeriod == ChartPeriod.THREE_MONTHS) { onPeriodSelected(ChartPeriod.THREE_MONTHS) }
                PeriodButton("6M", selectedPeriod == ChartPeriod.SIX_MONTHS) { onPeriodSelected(ChartPeriod.SIX_MONTHS) }
                PeriodButton("1Y", selectedPeriod == ChartPeriod.ONE_YEAR) { onPeriodSelected(ChartPeriod.ONE_YEAR) }
            }
        }
    }
}

/**
 * Line chart using MPAndroidChart
 */
@Composable
private fun BalanceLineChart(balanceHistory: List<BalanceDataPoint>) {
    val chartLineColor = ChartLine.toArgb()
    
    // Use remember to ensure chart instance survives recompositions
    AndroidView(
        modifier = Modifier.fillMaxSize(),
        factory = { context ->
            LineChart(context).apply {
                // Configure chart appearance
                setDrawGridBackground(false)
                description.isEnabled = false
                legend.isEnabled = false
                setTouchEnabled(true)
                isDragEnabled = true
                setScaleEnabled(false)
                setPinchZoom(false)
                setDrawBorders(false)
                setNoDataText("")
                extraBottomOffset = 8f
                extraTopOffset = 8f
                
                // Configure X axis
                xAxis.apply {
                    setDrawGridLines(false)
                    setDrawAxisLine(false)
                    setDrawLabels(false) // We use period chips instead
                    position = XAxis.XAxisPosition.BOTTOM
                }
                
                // Configure left Y axis
                axisLeft.apply {
                    setDrawGridLines(false)
                    setDrawAxisLine(false)
                    setDrawLabels(true)
                    textColor = AndroidColor.parseColor("#B0B0B0")
                    textSize = 10f
                    setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART)
                }
                
                // Configure right Y axis
                axisRight.apply {
                    setDrawGridLines(false)
                    setDrawAxisLine(false)
                    setDrawLabels(false)
                }
                
                // Set background color
                setBackgroundColor(AndroidColor.TRANSPARENT)
                setDrawMarkers(true)
                marker = BalanceMarkerView(context)
            }
        },
        update = { chart ->
            // Convert balance history to chart entries
            val entries = balanceHistory.mapIndexed { index, dataPoint ->
                Entry(index.toFloat(), dataPoint.balance.toFloat())
            }
            
            if (entries.isEmpty()) {
                chart.clear()
                return@AndroidView
            }

            // Compute axis bounds with a little padding
            var minY = entries.minOf { it.y }
            var maxY = entries.maxOf { it.y }
            if (minY == maxY) { // guard against flat line
                minY -= 100f
                maxY += 100f
            }
            val padding = (maxY - minY) * 0.1f
            chart.axisLeft.axisMinimum = minY - padding
            chart.axisLeft.axisMaximum = maxY + padding
            chart.axisLeft.setLabelCount(6, true)

            // Currency formatter for Y axis
            val currencyFormatter = object : ValueFormatter() {
                private val nf = NumberFormat.getCurrencyInstance(Locale.US).apply {
                    maximumFractionDigits = 0
                    minimumFractionDigits = 0
                }
                override fun getAxisLabel(value: Float, axis: AxisBase?): String = nf.format(value.toDouble())
            }
            chart.axisLeft.valueFormatter = currencyFormatter

            // Create dataset
            val dataSet = LineDataSet(entries, "Balance").apply {
                color = chartLineColor
                lineWidth = 2.5f
                setDrawCircles(false)
                setDrawValues(false)
                setDrawFilled(false)
                mode = LineDataSet.Mode.CUBIC_BEZIER
                cubicIntensity = 0.2f
                highLightColor = AndroidColor.TRANSPARENT // we use custom marker only
                setDrawHorizontalHighlightIndicator(false)
                setDrawVerticalHighlightIndicator(false)
            }
            
            // Set data and refresh
            chart.data = LineData(dataSet)
            chart.highlightValue(null)
            chart.invalidate()
        }
    )
}

/**
 * Custom marker view shown when a data point is selected.
 * Displays the balance in a red rounded badge above the point.
 */
private class BalanceMarkerView(context: Context) : MarkerView(context, R.layout.marker_balance) {
    private val textView: TextView = findViewById(R.id.tvValue)
    private val nf = NumberFormat.getCurrencyInstance(Locale.US).apply {
        maximumFractionDigits = 0
        minimumFractionDigits = 0
    }

    override fun refreshContent(e: Entry?, highlight: Highlight?) {
        if (e != null) {
            textView.text = nf.format(e.y.toDouble())
        }
        super.refreshContent(e, highlight)
    }

    override fun getOffset(): MPPointF = MPPointF(-(width / 2f), -height.toFloat() - 12f)
}

/**
 * Individual period button
 */
@Composable
private fun PeriodButton(
    label: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Text(
        text = label,
        modifier = Modifier
            .clip(RoundedCornerShape(6.dp))
            .background(
                if (isSelected) androidx.compose.ui.graphics.Color(0xFF2C2C2C)
                else androidx.compose.ui.graphics.Color.Transparent
            )
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = LocalIndication.current,
                onClick = onClick
            )
            .padding(horizontal = 10.dp, vertical = 6.dp),
        style = MaterialTheme.typography.labelSmall,
        color = if (isSelected) androidx.compose.ui.graphics.Color.White
        else androidx.compose.ui.graphics.Color(0xFF808080)
    )
}
