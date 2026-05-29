package runners;

import io.cucumber.junit.platform.engine.Constants;
import org.junit.platform.suite.api.ConfigurationParameter;
import org.junit.platform.suite.api.IncludeEngines;
import org.junit.platform.suite.api.SelectClasspathResource;
import org.junit.platform.suite.api.Suite;

/**
 * E2ETestRunner — menjalankan full end-to-end flow.
 * Admin login → Generate QR → Customer scan → Menu → Cart → Orders → Checkout.
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
    value = "@e2e"
)
@ConfigurationParameter(
    key   = Constants.PLUGIN_PROPERTY_NAME,
    value = "pretty, json:target/cucumber-reports/e2e.json, html:target/cucumber-reports/e2e-report.html"
)
public class E2ETestRunner {
}
