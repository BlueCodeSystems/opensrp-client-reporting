package org.smartregister.reporting.model;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.smartregister.reporting.ReportingLibrary;
import org.smartregister.reporting.dao.ReportIndicatorDaoImpl;
import org.smartregister.reporting.domain.BaseReportIndicatorsModel;
import org.smartregister.reporting.domain.IndicatorQuery;
import org.smartregister.reporting.domain.ReportIndicator;
import org.smartregister.reporting.repository.DailyIndicatorCountRepository;
import org.smartregister.reporting.repository.IndicatorQueryRepository;
import org.smartregister.reporting.repository.IndicatorRepository;

@RunWith(MockitoJUnitRunner.class)
public class BaseReportIndicatorsModelTest {

    @Mock
    private ReportingLibrary reportingLibrary;

    @Mock
    private IndicatorRepository indicatorRepository;

    @Mock
    private IndicatorQueryRepository indicatorQueryRepository;

    @Mock
    private DailyIndicatorCountRepository dailyIndicatorCountRepository;

    @Mock
    private ReportIndicatorDaoImpl dao;

    private BaseReportIndicatorsModel model;

    @Before
    public void setUp() {
        model = new BaseReportIndicatorsModel(reportingLibrary, dao);
    }

    @Test
    public void addIndicatorInvokesDaoWithCorrectParams() {
        ReportIndicator reportIndicator = Mockito.mock(ReportIndicator.class);
        ArgumentCaptor<ReportIndicator> argumentCaptor = ArgumentCaptor.forClass(ReportIndicator.class);
        Mockito.when(reportingLibrary.indicatorRepository()).thenReturn(indicatorRepository);

        model.addIndicator(reportIndicator);

        Mockito.verify(dao).setIndicatorRepository(indicatorRepository);
        Mockito.verify(dao).addReportIndicator(argumentCaptor.capture());
        Assert.assertEquals(reportIndicator, argumentCaptor.getValue());
    }

    @Test
    public void addIndicatorQueryInvokesDaoWithCorrectParams() {
        IndicatorQuery indicatorQuery = Mockito.mock(IndicatorQuery.class);
        ArgumentCaptor<IndicatorQuery> argumentCaptor = ArgumentCaptor.forClass(IndicatorQuery.class);
        Mockito.when(reportingLibrary.indicatorQueryRepository()).thenReturn(indicatorQueryRepository);

        model.addIndicatorQuery(indicatorQuery);

        Mockito.verify(dao).setIndicatorQueryRepository(indicatorQueryRepository);
        Mockito.verify(dao).addIndicatorQuery(argumentCaptor.capture());
        Assert.assertEquals(indicatorQuery, argumentCaptor.getValue());
    }

    @Test
    public void getIndicatorsDailyTalliesInvokesDao() {
        Mockito.when(reportingLibrary.dailyIndicatorCountRepository()).thenReturn(dailyIndicatorCountRepository);

        model.getIndicatorsDailyTallies();

        Mockito.verify(dao).setDailyIndicatorCountRepository(dailyIndicatorCountRepository);
        Mockito.verify(dao).getIndicatorsDailyTallies();
    }

}
