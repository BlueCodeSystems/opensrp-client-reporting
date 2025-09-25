package org.smartregister.reporting.dao;

import net.sqlcipher.database.SQLiteDatabase;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import org.smartregister.Context;
import org.smartregister.CoreLibrary;
import org.smartregister.reporting.ReportingLibrary;
import org.smartregister.reporting.TestTimber;
import org.smartregister.reporting.domain.IndicatorQuery;
import org.smartregister.reporting.domain.ReportIndicator;
import org.smartregister.reporting.repository.DailyIndicatorCountRepository;
import org.smartregister.reporting.repository.IndicatorQueryRepository;
import org.smartregister.reporting.repository.IndicatorRepository;
import org.smartregister.reporting.util.Constants;
import org.smartregister.repository.AllSharedPreferences;
import org.smartregister.repository.Repository;
import org.smartregister.util.AppProperties;
import org.robolectric.util.ReflectionHelpers;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;

@RunWith(RobolectricTestRunner.class)
@Config(sdk = 33, application = org.smartregister.reporting.TestApplication.class)
public class DaoTest {

    @Mock
    private Context context;

    @Mock
    private ReportingLibrary reportingLibrary;

    @Mock
    private Repository repository;

    @Mock
    private IndicatorQueryRepository indicatorQueryRepository;

    @Mock
    private IndicatorRepository indicatorRepository;

    @Mock
    private DailyIndicatorCountRepository dailyIndicatorCountRepository;

    @Mock
    private SQLiteDatabase sqLiteDatabase;

    @Mock
    private AllSharedPreferences sharedPreferences;

    private ReportIndicatorDaoImpl daoSpy;

    @Mock
    private CoreLibrary coreLibrary;

    @Mock
    private AppProperties appProperties;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        TestTimber.plant();
        daoSpy = Mockito.spy(new ReportIndicatorDaoImpl());
        daoSpy.setIndicatorRepository(indicatorRepository);
        daoSpy.setIndicatorQueryRepository(indicatorQueryRepository);
        daoSpy.setDailyIndicatorCountRepository(dailyIndicatorCountRepository);
    }

    @After
    public void tearDown() {
        ReflectionHelpers.setStaticField(CoreLibrary.class, "instance", null);
        ReflectionHelpers.setStaticField(ReportingLibrary.class, "instance", null);
    }

    @Test
    public void addReportIndicatorInvokesIndicatorRepositoryAdd() {
        ReportIndicator reportIndicator = Mockito.mock(ReportIndicator.class);
        ArgumentCaptor<ReportIndicator> argumentCaptor = ArgumentCaptor.forClass(ReportIndicator.class);

        daoSpy.addReportIndicator(reportIndicator);
        Mockito.verify(indicatorRepository, Mockito.times(1)).add(argumentCaptor.capture());
        Assert.assertEquals(reportIndicator, argumentCaptor.getValue());
    }

    @Test
    public void addIndicatorQueryInvokesQueryRepositoryAdd() {
        IndicatorQuery indicatorQuery = Mockito.mock(IndicatorQuery.class);
        ArgumentCaptor<IndicatorQuery> argumentCaptor = ArgumentCaptor.forClass(IndicatorQuery.class);

        daoSpy.addIndicatorQuery(indicatorQuery);
        Mockito.verify(indicatorQueryRepository, Mockito.times(1)).add(argumentCaptor.capture());
        Assert.assertEquals(indicatorQuery, argumentCaptor.getValue());
    }

    @Test
    public void generateDailyIndicatorTalliesSetsLastProcessedDatePreference() throws Exception {
        // Test data
        String lastProcessedDate = "2019-01-01 00:00";
        LinkedHashMap<String, Date> reportEventDates = new LinkedHashMap<>();
        reportEventDates.put("20190413", new SimpleDateFormat("yyyy-MM-dd hh:mm:ss", Locale.ENGLISH).parse("2019-04-13 12:19:37"));
        reportEventDates.put("20190513", new SimpleDateFormat("yyyy-MM-dd hh:mm:ss", Locale.ENGLISH).parse("2019-05-13 12:19:37"));

        Map<String, IndicatorQuery> indicatorQueries = new HashMap<>();
        indicatorQueries.put("INDI-100", new IndicatorQuery(1L, "INDI-100", "select count(*) from table", 4, false, null));

        ReflectionHelpers.setStaticField(CoreLibrary.class, "instance", coreLibrary);
        ReflectionHelpers.setStaticField(ReportingLibrary.class, "instance", reportingLibrary);
        Mockito.when(coreLibrary.context()).thenReturn(context);
        Mockito.when(context.getAppProperties()).thenReturn(appProperties);
        Mockito.when(appProperties.hasProperty(Constants.ReportingConfig.SHOULD_ALLOW_ZERO_TALLIES)).thenReturn(true);
        Mockito.when(appProperties.getPropertyBoolean(Constants.ReportingConfig.SHOULD_ALLOW_ZERO_TALLIES)).thenReturn(true);

        Mockito.when(reportingLibrary.getRepository()).thenReturn(repository);
        Mockito.when(reportingLibrary.getContext()).thenReturn(context);
        Mockito.when(repository.getWritableDatabase()).thenReturn(sqLiteDatabase);
        Mockito.doReturn(reportEventDates).when(daoSpy).getReportEventDates(ArgumentMatchers.any(Date.class), ArgumentMatchers.anyString(), ArgumentMatchers.any(SQLiteDatabase.class));
        Mockito.when(indicatorQueryRepository.getAllIndicatorQueries()).thenReturn(indicatorQueries);
        Mockito.when(context.allSharedPreferences()).thenReturn(sharedPreferences);

        daoSpy.generateDailyIndicatorTallies(lastProcessedDate);
        Mockito.verify(sharedPreferences, Mockito.times(1)).savePreference(ArgumentMatchers.anyString(), ArgumentMatchers.anyString());

    }

}
