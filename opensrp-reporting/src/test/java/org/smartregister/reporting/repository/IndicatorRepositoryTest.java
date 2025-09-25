package org.smartregister.reporting.repository;

import android.content.ContentValues;

import net.sqlcipher.database.SQLiteDatabase;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import org.smartregister.reporting.TestApplication;
import org.smartregister.reporting.TestTimber;
import org.smartregister.reporting.domain.ReportIndicator;
import org.smartregister.repository.Repository;
import org.smartregister.view.activity.DrishtiApplication;


@RunWith(RobolectricTestRunner.class)
@Config(sdk = 33, application = org.smartregister.reporting.TestApplication.class)
public class IndicatorRepositoryTest {
    @Mock
    private Repository repository;

    @Mock
    private DrishtiApplication application;

    @Mock
    private SQLiteDatabase sqLiteDatabase;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        TestTimber.plant();

        Mockito.when(application.getRepository()).thenReturn(repository);
        TestApplication.setInstance(application);
    }

    @Test
    public void addReportIndicatorInvokesWritableDBInsert() {
        ReportIndicator indicator = Mockito.mock(ReportIndicator.class);
        IndicatorRepository indicatorRepositorySpy = Mockito.spy(new IndicatorRepository());
        Mockito.when(indicatorRepositorySpy.getWritableDatabase()).thenReturn(sqLiteDatabase);
        indicatorRepositorySpy.add(indicator);
        Mockito.verify(sqLiteDatabase, Mockito.times(1)).insert(ArgumentMatchers.anyString(), ArgumentMatchers.isNull(String.class), ArgumentMatchers.any(ContentValues.class));
    }

}
