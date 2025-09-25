package org.smartregister.reporting.service;

import android.content.Intent;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.smartregister.reporting.TestTimber;
import org.smartregister.reporting.dao.ReportIndicatorDaoImpl;
import org.smartregister.repository.AllSharedPreferences;

@RunWith(MockitoJUnitRunner.class)
public class IndicatorGeneratorIntentServiceTest {

    @Mock
    private Intent intent;

    @Mock
    private ReportIndicatorDaoImpl reportIndicatorDao;

    @Test
    public void testOnHandleIntent() {
        TestTimber.plant();
        IndicatorGeneratorIntentService service = Mockito.spy(new IndicatorGeneratorIntentService());

        AllSharedPreferences allSharedPreferences = Mockito.mock(AllSharedPreferences.class);
        Mockito.when(allSharedPreferences.getPreference(ReportIndicatorDaoImpl.REPORT_LAST_PROCESSED_DATE)).thenReturn("123456");

        service.setAllSharedPreferences(allSharedPreferences);
        service.setReportIndicatorDao(reportIndicatorDao);

        service.onHandleIntent(intent);

        Mockito.verify(reportIndicatorDao).generateDailyIndicatorTallies("123456");
    }
}
