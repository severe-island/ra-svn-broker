package org.repoaggr.svnbrk;

import org.repoaggr.svnbrk.api.ApiOriginFilter;
import org.repoaggr.svnbrk.controller.LocalCacheController;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.ExitCodeGenerator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import static org.repoaggr.svnbrk.configuration.Constants.TEST_REPO_ID;

@SpringBootApplication
@EnableSwagger2
@ComponentScan(basePackages = { "org.repoaggr.svnbrk", "org.repoaggr.svnbrk.api" })
public class Swagger2SpringBoot implements CommandLineRunner {

    @Override
    public void run(String... arg0) throws Exception {
        if (arg0.length > 0 && arg0[0].equals("exitcode")) {
            throw new ExitException();
        }
    }

    public static void main(String[] args) throws Exception {
        LocalCacheController.deleteDirectory(TEST_REPO_ID);
        new SpringApplication(Swagger2SpringBoot.class).run(args);
    }

    @Bean
    public FilterRegistrationBean apiOriginFilter() {
        final FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        registrationBean.setFilter(new ApiOriginFilter());
        registrationBean.addUrlPatterns("/*");

        return registrationBean;
    }

    class ExitException extends RuntimeException implements ExitCodeGenerator {
        private static final long serialVersionUID = 1L;

        @Override
        public int getExitCode() {
            return 10;
        }

    }
}
