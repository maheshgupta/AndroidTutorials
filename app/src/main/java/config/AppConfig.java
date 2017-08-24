package config;


import com.tutorials.andorid.app.BuildConfig;

public class AppConfig {

    private static boolean isReleaseBuild() {
        return BuildConfig.BUILD_TYPE.equalsIgnoreCase("release");
    }

    public static final boolean isLoggingEnabled = isReleaseBuild() ? false : true;
    public static final String LOGIN_WEBSERVICE_URL = isReleaseBuild() ? "https://mycompny.com/login/auth" : "http://mycompany.stage.com/login/auth";

}
