package org.smartregister.reporting;

import net.sqlcipher.database.SQLiteDatabase;

import org.junit.Test;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.robolectric.util.ReflectionHelpers;
import org.smartregister.Context;
import org.smartregister.commonregistry.CommonFtsObject;
import org.smartregister.reporting.repository.IndicatorQueryRepository;
import org.smartregister.reporting.repository.IndicatorRepository;
import org.smartregister.reporting.TestTimber;
import org.smartregister.repository.Repository;

import static org.junit.Assert.assertNotNull;

@RunWith(MockitoJUnitRunner.class)
public class ReportingLibraryTest extends BaseUnitTest {

    @Mock
    private Context context;

    @Mock
    private Repository repository;

    @Mock
    private IndicatorRepository indicatorRepository;

    @Mock
    private IndicatorQueryRepository indicatorQueryRepository;

    @Mock
    private CommonFtsObject commonFtsObject;

    @Mock
    private SQLiteDatabase sqliteDatabase;

    private int appVersion = 1;
    private int dbVersion = 1;

    @Before
    public void setUp() {
        TestTimber.plant();
    }

    @Test
    public void testInit() {
        ReportingLibrary.init(context, repository, commonFtsObject, appVersion, dbVersion);
        assertNotNull(ReportingLibrary.getInstance());
    }

    @Test
    public void testThatAllRepositoriesAreInitialized() {
        ReportingLibrary.init(context, repository, commonFtsObject, appVersion, dbVersion);
        ReportingLibrary reportingLibrary = ReportingLibrary.getInstance();
        assertNotNull(ReportingLibrary.getInstance());
        assertNotNull(reportingLibrary.dailyIndicatorCountRepository());
        assertNotNull(reportingLibrary.indicatorQueryRepository());
        assertNotNull(reportingLibrary.eventClientRepository());
        assertNotNull(reportingLibrary.dailyIndicatorCountRepository());

    }

    @Test
    public void truncateIndicatorTablesWithDBInvokesRepositoryTruncate() {
        ReportingLibrary.init(context, repository, commonFtsObject, appVersion, dbVersion);
        assertNotNull(ReportingLibrary.getInstance());
        ReportingLibrary reportingLibrary = ReportingLibrary.getInstance();
        ReflectionHelpers.setField(reportingLibrary, "indicatorRepository", indicatorRepository);
        ReflectionHelpers.setField(reportingLibrary, "indicatorQueryRepository", indicatorQueryRepository);

        reportingLibrary.truncateIndicatorDefinitionTables(sqliteDatabase);
        Mockito.verify(indicatorRepository, Mockito.times(1)).truncateTable(sqliteDatabase);
        Mockito.verify(indicatorQueryRepository, Mockito.times(1)).truncateTable(sqliteDatabase);
    }
}
