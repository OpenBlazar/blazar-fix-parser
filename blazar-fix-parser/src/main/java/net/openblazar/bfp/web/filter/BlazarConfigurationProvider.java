package net.openblazar.bfp.web.filter;

import net.openblazar.bfp.web.util.BlazarURL;
import org.ocpsoft.rewrite.annotation.RewriteConfiguration;
import org.ocpsoft.rewrite.config.Configuration;
import org.ocpsoft.rewrite.config.ConfigurationBuilder;
import org.ocpsoft.rewrite.servlet.config.HttpConfigurationProvider;
import org.ocpsoft.rewrite.servlet.config.rule.Join;

import javax.servlet.ServletContext;

/**
 * @author Wojciech Zankowski
 */
@RewriteConfiguration
public class BlazarConfigurationProvider extends HttpConfigurationProvider {

    @Override
    public Configuration getConfiguration(ServletContext servletContext) {
        return ConfigurationBuilder.begin()
                .addRule(Join.path(BlazarURL.PARSER_URL).to(BlazarURL.PARSER_FULL_URL))
                .addRule(Join.path(BlazarURL.SIGNIN_URL).to(BlazarURL.SIGNIN_FULL_URL))
                .addRule(Join.path(BlazarURL.SIGNUP_URL).to(BlazarURL.SIGNUP_FULL_URL))
                .addRule(Join.path(BlazarURL.FILEPARSER_URL).to(BlazarURL.FILEPARSER_FULL_URL))
                .addRule(Join.path(BlazarURL.HISTORY_URL).to(BlazarURL.HISTORY_FULL_URL))
                .addRule(Join.path(BlazarURL.HELP_URL).to(BlazarURL.HELP_FULL_URL));
    }

    @Override
    public int priority() {
        return 0;
    }
}