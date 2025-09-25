# opensrp-client-reporting
OpenSRP client library for producing reporting indicators, visualizations, and widgets that can be embedded in Android dashboards and register flows.

## Project Status
- Toolchain: Gradle 8.7, Android Gradle Plugin 8.6.0, Java-only (no Kotlin DSL), JDK 17
- Continuous integration: GitHub Actions workflows (badge refresh and JitPack warm-up)
- Default branch: `master`
- Latest tag: `v0.2.0`

## Features
- Render numeric, pie chart, and tabular reporting widgets backed by OpenSRP data.
- Persist indicator queries and tallies for offline viewing through the reporting repositories.
- Helper factories for inflating reporting UIs inside existing activities or fragments.

## Requirements
- JDK 17+
- Gradle 8.7 with Android Gradle Plugin 8.6.0
- Android Studio Giraffe+ (or compatible)
- Min SDK: 28
- Compile/Target SDK: 35
- Kotlin: not required (library is Java-based)

## Install
Add the dependency from Maven Central. Replace `<version>` with the desired release (see [Releases](#releases)).

<details>
<summary>Groovy (build.gradle)</summary>

```groovy
repositories {
    mavenCentral()
}

dependencies {
    implementation 'org.smartregister:opensrp-client-reporting:<version>'
}
```
</details>

<details>
<summary>Kotlin DSL (build.gradle.kts)</summary>

```kotlin
repositories {
    mavenCentral()
}

dependencies {
    implementation("org.smartregister:opensrp-client-reporting:<version>")
}
```
</details>

## Initialize
Create an `Application` class that wires the reporting repositories before any indicators are accessed.

```java
public class ReportingApp extends Application {
  @Override
  public void onCreate() {
    super.onCreate();
    ReportingLibrary.init(
        org.smartregister.Context.getInstance(),
        getRepository(),
        getCommonFtsObject(),
        BuildConfig.VERSION_CODE,
        BuildConfig.VERSION_CODE
    );
  }
}
```

Replace `getRepository()` and `getCommonFtsObject()` with the OpenSRP components your project uses.

## Usage examples
### Numeric indicator widget
```java
NumericIndicatorVisualization visualization = new NumericIndicatorVisualization();
visualization.setIndicatorLabel(getString(R.string.total_children_registered));
visualization.setValue(42f);

View indicatorView = new NumericDisplayFactory()
    .getIndicatorView(visualization, context);
container.addView(indicatorView);
```

### Pie chart indicator
```java
PieChartIndicatorVisualization chart =
    new PieChartIndicatorVisualization.PieChartIndicatorVisualizationBuilder()
        .indicatorLabel(getString(R.string.immunization_breakdown))
        .chartHasLabels(true)
        .chartSlices(Arrays.asList(
            new PieChartSlice(60f, "Fully immunized", Color.GREEN, "immunized"),
            new PieChartSlice(40f, "Due", Color.RED, "due")))
        .build();

View chartView = new PieChartFactory().getIndicatorView(chart, context);
container.addView(chartView);
```

### Tabular reporting widget
```java
TableView tableView = findViewById(R.id.tableView);
tableView.setTableData(
    Arrays.asList("Indicator", "Gender", "Value"),
    Arrays.asList("Vitamin A", "Female", "102", "Vitamin A", "Male", "95")
);
```

## Optional configuration
| Property | Type | Description |
| --- | --- | --- |
| `SHOULD_ALLOW_ZERO_TALLIES` | boolean | When `true`, zero-value tallies are displayed; otherwise they are skipped. |

## Sample app
A sample module is available under `sample/`.

- Build and install: `./gradlew :sample:installDebug`
- You can also open the module in Android Studio and use the provided run configuration.

## Build & test
```
./gradlew clean assemble
./gradlew test
```

## Releases
See [GitHub Releases](https://github.com/BlueCodeSystems/opensrp-client-reporting/releases) for published versions and release notes.

## Contributing
Contributions are welcome! Please open issues or pull requests with clear descriptions, and ensure changes build with JDK 17, Gradle 8.7, and the stated Android toolchain. If a `CONTRIBUTING.md` guideline exists in your fork, follow it as well.

## License
This project is licensed under the terms described in [LICENSE](LICENSE).
