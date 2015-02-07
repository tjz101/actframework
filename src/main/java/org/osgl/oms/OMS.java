package org.osgl.oms;

import org.osgl._;
import org.osgl.exception.NotAppliedException;
import org.osgl.logging.L;
import org.osgl.logging.Logger;
import org.osgl.mvc.annotation.*;
import org.osgl.oms.app.AppManager;
import org.osgl.oms.app.AppScanner;
import org.osgl.oms.cls.BootstrapClassLoader;
import org.osgl.oms.conf.OmsConfLoader;
import org.osgl.oms.conf.OmsConfig;
import org.osgl.oms.util.Banner;
import org.osgl.util.C;
import org.osgl.util.E;

import java.lang.annotation.Annotation;
import java.util.List;

/**
 * The OSGL MVC Server object
 */
public final class OMS {

    public static enum Mode {
        PROD, UAT, SIT, DEV () {
            @Override
            public AppScanner appScanner() {
                return AppScanner.DEV_MODE_SCANNER;
            }
        };

        private final String confPrefix = "%" + name().toLowerCase() + ".";

        /**
         * DEV mode is special as OMS might load classes
         * directly from source code when running in this mode
         */
        public boolean isDev() {
            return DEV == this;
        }

        public String configKey(String key) {
            return confPrefix + key;
        }

        public AppScanner appScanner() {
            return AppScanner.DEF_SCANNER;
        }

        public static Mode valueOfIgnoreCase(String mode) {
            return valueOf(mode.trim().toUpperCase());
        }
    }

    private static OmsConfig conf;
    private static Logger logger = L.get(OMS.class);
    private static Mode mode = Mode.PROD;
    private static AppManager appManager;
    private static List<Class<? extends Annotation>> actionAnnotationTypes = C.list(
            Action.class, GetAction.class, PostAction.class,
            PutAction.class, DeleteAction.class);


    public static BootstrapClassLoader classLoader() {
        return (BootstrapClassLoader)OMS.class.getClassLoader();
    }
    public static Mode mode() {
        return mode;
    }
    public static boolean isDev() {
        return mode.isDev();
    }
    public static OmsConfig conf() {
        return conf;
    }

    public static boolean isActionAnnotation(Class<?> type) {
        return actionAnnotationTypes.contains(type);
    }

    public static void start() {
        Banner.print("0.0.1-SNAPSHOT");
        loadConfig();
        loadPlugins();
        initExecuteService();
        initNetworkLayer();
        initApplicationManager();
        startNetworkLayer();
    }

    private static void loadConfig() {
        logger.debug("loading configuration ...");

        String s = System.getProperty("oms.mode");
        if (null != s) {
            mode = Mode.valueOfIgnoreCase(s);
        }
        logger.info("OMS start in %s mode", mode);

        conf = new OmsConfLoader().load(null);
    }

    private static void loadPlugins() {
        E.tbd("load plugins");
    }

    private static void initExecuteService() {
        E.tbd("init execute service");
    }

    private static void initNetworkLayer() {
        E.tbd("init network server");
    }

    private static void initApplicationManager() {
        appManager = AppManager.scan();
    }

    private static void startNetworkLayer() {

    }

    public static enum F {
        ;
        public static final _.F0<Mode> MODE_ACCESSOR = new  _.F0<Mode>() {
            @Override
            public Mode apply() throws NotAppliedException, _.Break {
                return mode;
            }
        };
    }

}
