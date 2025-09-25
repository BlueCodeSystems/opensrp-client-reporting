package org.smartregister.reporting.view;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.test.core.app.ApplicationProvider;

import org.junit.Assert;
import org.junit.Test;
import org.smartregister.reporting.BaseUnitTest;
import org.smartregister.reporting.R;
import org.smartregister.reporting.domain.PieChartIndicatorVisualization;
import org.smartregister.reporting.domain.PieChartSlice;
import org.smartregister.reporting.factory.PieChartFactory;
import org.smartregister.reporting.listener.PieChartSelectListener;

import java.util.Arrays;

import lecho.lib.hellocharts.view.PieChartView;

public class PieChartFactoryTest extends BaseUnitTest {

    private final Context context = ApplicationProvider.getApplicationContext();
    private final PieChartFactory pieChartFactory = new PieChartFactory();

    @Test
    public void getPieChartIndicatorViewReturnsCorrectView() {
        PieChartSlice first = new PieChartSlice(10f, "First", Color.RED, "first");
        PieChartSlice second = new PieChartSlice(5f, "Second", Color.BLUE, "second");
        PieChartSelectListener listener = slice -> { /* no-op */ };

        PieChartIndicatorVisualization visualization = new PieChartIndicatorVisualization.PieChartIndicatorVisualizationBuilder()
                .indicatorLabel("ANC Coverage")
                .indicatorNote("FY2025")
                .chartHasLabels(true)
                .chartHasLabelsOutside(false)
                .chartHasCenterCircle(true)
                .chartSlices(Arrays.asList(first, second))
                .chartListener(listener)
                .build();

        View view = pieChartFactory.getIndicatorView(visualization, context);

        Assert.assertNotNull(view);
        Assert.assertTrue(view instanceof LinearLayout);

        TextView label = view.findViewById(R.id.pie_indicator_label);
        TextView note = view.findViewById(R.id.pie_note_label);
        PieChartView pieChartView = view.findViewById(R.id.pie_chart);

        Assert.assertEquals("ANC Coverage", label.getText().toString());
        Assert.assertEquals("FY2025", note.getText().toString());
        Assert.assertEquals(View.VISIBLE, pieChartView.getVisibility());
    }
}
