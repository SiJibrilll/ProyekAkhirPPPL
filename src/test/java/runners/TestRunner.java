package runners;

import io.cucumber.junit.platform.engine.Constants;
import org.junit.platform.suite.api.ConfigurationParameter;
import org.junit.platform.suite.api.IncludeEngines;
import org.junit.platform.suite.api.SelectClasspathResource;
import org.junit.platform.suite.api.Suite;

/**
 * TestRunner — menjalankan semua @smoke test.
 *
 * Untuk mengubah scope test, ganti value FILTER_TAGS:
 *   "@smoke"                    → hanya happy path
 *   "@regression"               → hanya negatif / edge case
 *   "@smoke or @regression"     → semua test
 *   "@e2e"                      → hanya end-to-end flow
 *   "@qr"                       → hanya test QR table
 *   "@login"                    → hanya login
 *   "@menu"                     → hanya katalog menu
 *   "@cart or @orders"          → cart dan orders
 *   "@checkout"                 → hanya checkout
 *   "@admin"                    → hanya admin panel
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
    value = "@smoke"
)
@ConfigurationParameter(
    key   = Constants.PLUGIN_PROPERTY_NAME,
    value = "pretty, json:target/cucumber-reports/smoke.json, html:target/cucumber-reports/smoke-report.html"
)
public class TestRunner {
}
