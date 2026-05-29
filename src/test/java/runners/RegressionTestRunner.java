package runners;

import io.cucumber.junit.platform.engine.Constants;
import org.junit.platform.suite.api.ConfigurationParameter;
import org.junit.platform.suite.api.IncludeEngines;
import org.junit.platform.suite.api.SelectClasspathResource;
import org.junit.platform.suite.api.Suite;

/**
 * RegressionTestRunner — menjalankan semua @regression test.
 * Meliputi: BVA, Equivalence Partitioning, dan skenario negatif.
 */
@Suite
@IncludeEngines("cucumber")
@SelectClasspathResource("features")
@ConfigurationParameter(
    key   = Constants.GLUE_PROPERTY_NAME,
    value = "stepDefinitions"
)
@ConfigurationParameter(
    key   = Constants.FILTER_TAGS_PROPERTY_NAME,
    value = "@regression"
)
@ConfigurationParameter(
    key   = Constants.PLUGIN_PROPERTY_NAME,
    value = "pretty, json:target/cucumber-reports/regression.json, html:target/cucumber-reports/regression-report.html"
)
public class RegressionTestRunner {
}
