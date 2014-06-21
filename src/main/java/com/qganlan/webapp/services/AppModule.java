package com.qganlan.webapp.services;

import java.io.IOException;

import org.apache.tapestry5.MarkupWriter;
import org.apache.tapestry5.SymbolConstants;
import org.apache.tapestry5.ValidationDecorator;
import org.apache.tapestry5.ValueEncoder;
import org.apache.tapestry5.ioc.MappedConfiguration;
import org.apache.tapestry5.ioc.OrderedConfiguration;
import org.apache.tapestry5.ioc.ServiceBinder;
import org.apache.tapestry5.ioc.annotations.Contribute;
import org.apache.tapestry5.ioc.annotations.Symbol;
import org.apache.tapestry5.services.ClasspathAssetAliasManager;
import org.apache.tapestry5.services.ComponentSource;
import org.apache.tapestry5.services.Environment;
import org.apache.tapestry5.services.ExceptionReporter;
import org.apache.tapestry5.services.MarkupRenderer;
import org.apache.tapestry5.services.MarkupRendererFilter;
import org.apache.tapestry5.services.RequestExceptionHandler;
import org.apache.tapestry5.services.ResponseRenderer;
import org.apache.tapestry5.services.ValueEncoderFactory;
import org.apache.tapestry5.services.ValueEncoderSource;
import org.apache.tapestry5.services.javascript.JavaScriptStack;
import org.apache.tapestry5.upload.services.UploadSymbols;
import org.appfuse.model.Role;
import org.appfuse.model.User;
import org.appfuse.service.RoleManager;
import org.appfuse.service.UserManager;
import org.slf4j.Logger;

import com.qganlan.webapp.AppFuseSymbolConstants;
import com.qganlan.webapp.data.FileData;
import com.qganlan.webapp.services.impl.BootstrapValidationDecorator;
import com.qganlan.webapp.services.impl.CountryServiceImpl;
import com.qganlan.webapp.services.impl.EmailServiceImpl;
import com.qganlan.webapp.services.impl.FileDataEncoder;
import com.qganlan.webapp.services.impl.RoleEncoder;
import com.qganlan.webapp.services.impl.SelectIdModelFactoryImpl;
import com.qganlan.webapp.services.impl.SpringSecurityContext;
import com.qganlan.webapp.services.impl.UserEncoder;
import com.qganlan.webapp.services.javascript.BootstrapJavaScriptStack;

public class AppModule {

    public static void bind(ServiceBinder binder) {
        binder.bind(SecurityContext.class, SpringSecurityContext.class);
        binder.bind(CountryService.class, CountryServiceImpl.class);
        binder.bind(EmailService.class, EmailServiceImpl.class);
        binder.bind(SelectIdModelFactory.class, SelectIdModelFactoryImpl.class);
    }

    public static void contributeApplicationDefaults(MappedConfiguration<String, String> configuration) {
        configuration.add(SymbolConstants.SUPPORTED_LOCALES,
                "de,en,es,fr,it,ko,nl,no,pt_BR,pt,tr,zh_CN,zh_TW,en_US");

        // Turn off GZip Compression since it causes issues with SiteMesh
        configuration.add(SymbolConstants.GZIP_COMPRESSION_ENABLED, "false");

        // The factory default is true but during the early stages of an application
        // overriding to false is a good idea. In addition, this is often overridden
        // on the command line as -Dtapestry.production-mode=false
        configuration.add(SymbolConstants.PRODUCTION_MODE, "false");

        // Maximum upload size is 2MB (size is in bytes)
        configuration.add(UploadSymbols.FILESIZE_MAX, "2048000");

        // HHAC recommended for better security as of Tapestry 5.3.6
        configuration.add(SymbolConstants.HMAC_PASSPHRASE, "AppFuse Tapestry is Great");

        // Workaround for Bootstrap buttons tied together
        // See thread on SO here: http://stackoverflow.com/questions/9689584/buttons-run-together-in-bootstrap-2-0-1
        // Commented out as this break the unit tests
        // configuration.add(SymbolConstants.COMPRESS_WHITESPACE, "false");

        // Spring Security
        configuration.add(AppFuseSymbolConstants.SECURITY_URL, "/j_security_check");

    }

    @Contribute(ClasspathAssetAliasManager.class)
    public static void provideClasspathAssetAliases(MappedConfiguration<String, String> configuration) {
        configuration.add("webjars", "META-INF/resources/webjars");
    }

    @Contribute(ValueEncoderSource.class)
    public static void provideEncoders(
            MappedConfiguration<Class, ValueEncoderFactory> configuration,
            UserManager userManager,
            RoleManager roleManager) {

        contributeEncoder(configuration, User.class, new UserEncoder(userManager));
        contributeEncoder(configuration, Role.class, new RoleEncoder(roleManager));
        contributeEncoder(configuration, FileData.class, new FileDataEncoder());


    }

    public static void contributeJavaScriptStackSource(MappedConfiguration<String, JavaScriptStack> configuration) {
        configuration.addInstance(AppFuseSymbolConstants.BOOTSTRAP_STACK, BootstrapJavaScriptStack.class);
    }

    private static <T> void contributeEncoder(MappedConfiguration<Class, ValueEncoderFactory> configuration,
                                              Class<T> clazz, final ValueEncoder<T> encoder) {

        ValueEncoderFactory<T> factory = new ValueEncoderFactory<T>() {

            public ValueEncoder<T> create(Class<T> clazz) {
                return encoder;
            }
        };

        configuration.add(clazz, factory);
    }

    public void contributeMarkupRenderer(OrderedConfiguration<MarkupRendererFilter> configuration,
                                         final Environment environment) {
        MarkupRendererFilter bootstrapValidationDecorator = new MarkupRendererFilter() {

            public void renderMarkup(MarkupWriter writer, MarkupRenderer renderer) {
                environment.push(ValidationDecorator.class, new BootstrapValidationDecorator(environment, writer));
                renderer.renderMarkup(writer);
                environment.pop(ValidationDecorator.class);
            }
        };

        configuration.override("ValidationDecorator", bootstrapValidationDecorator);

    }


    /**
     * Decorate Error page
     *
     * @param logger
     * @param renderer
     * @param componentSource
     * @param productionMode
     * @param service
     * @return
     */
    public RequestExceptionHandler decorateRequestExceptionHandler(
            final Logger logger,
            final ResponseRenderer renderer,
            final ComponentSource componentSource,
            @Symbol(SymbolConstants.PRODUCTION_MODE)
            boolean productionMode,
            Object service) {
        if (!productionMode) {
            return null;
        }

        return new RequestExceptionHandler() {
            public void handleRequestException(Throwable exception) throws IOException {
                logger.error("Unexpected runtime exception: " + exception.getMessage(), exception);
                ExceptionReporter error = (ExceptionReporter) componentSource.getPage("Error");
                error.reportException(exception);
                renderer.renderPageMarkupResponse("Error");
            }
        };
    }

//    @Startup
//    public static void scheduleJobs(PeriodicExecutor executor, final JobManager jobManager) {
//    	executor.addJob(new IntervalSchedule(60*60000L), "1 HOUR JOB", new Runnable() {
//			public void run() {
//				jobManager.checkModifiedTaobaoItems();
//				jobManager.checkGoods();
//			}
//		});
//    }

}
