package org.smartregister.reporting.view;

import android.content.Context;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.test.core.app.ApplicationProvider;

import org.junit.Assert;
import org.junit.Test;
import org.smartregister.reporting.BaseUnitTest;
import org.smartregister.reporting.R;
import org.smartregister.reporting.domain.NumericIndicatorVisualization;
import org.smartregister.reporting.factory.NumericDisplayFactory;

public class NumericDisplayFactoryTest extends BaseUnitTest {

    private final Context context = ApplicationProvider.getApplicationContext();
    private final NumericDisplayFactory numericDisplayFactory = new NumericDisplayFactory();

    @Test
    public void getNumericDisplayIndicatorViewReturnsCorrectView() {
        NumericIndicatorVisualization visualization = new NumericIndicatorVisualization();
        visualization.setIndicatorLabel("ANC Visits");
        visualization.setValue(12.5f);

        View view = numericDisplayFactory.getIndicatorView(visualization, context);

        Assert.assertNotNull(view);
        Assert.assertTrue(view instanceof RelativeLayout);

        TextView label = view.findViewById(R.id.numeric_indicator_label);
        TextView value = view.findViewById(R.id.numeric_indicator_value);

        Assert.assertEquals("ANC Visits", label.getText().toString());
        Assert.assertEquals("12.5", value.getText().toString());
    }
}
