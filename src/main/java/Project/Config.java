package Project;

import java.io.InputStream;
import java.util.Properties;

public class Config {
    public static double EPSILON;
    public static double LINE_LENGTH;
    public static double HITBOX;
    public static double ZOOM_SPEED;
    public static double POINT_RADIUS;
    public static double POINT_HUB;
    public static double LINE_HUB;
    public static double CIRCLE_HUB;
    public static double MAX_SCALE;
    public static double MIN_SCALE;
    public static double LABEL_DEFAULT_X_OFFSET;
    public static double LABEL_DEFAULT_Y_OFFSET;
    private static Properties properties;

    public static void load() {
        properties = new Properties();
        try {
            InputStream in = Config.class.getClassLoader().getResourceAsStream("default.properties");
            properties.load(in);
            in.close();
        }
        catch (Exception e) {
            System.err.println(e);
        }
        //default values in case default.properties is missing
        EPSILON = Double.parseDouble(properties.getProperty("epsilon", "1e-9"));
        LINE_LENGTH = Double.parseDouble(properties.getProperty("lineLength", "5000"));
        HITBOX = Double.parseDouble(properties.getProperty("hitbox", "10"));
        ZOOM_SPEED = Double.parseDouble(properties.getProperty("zoomSpeed", "0.002"));
        POINT_RADIUS = Double.parseDouble(properties.getProperty("pointRadius", "4"));
        POINT_HUB = Double.parseDouble(properties.getProperty("pointHub", "8"));
        LINE_HUB = Double.parseDouble(properties.getProperty("lineHub", "4"));
        CIRCLE_HUB = Double.parseDouble(properties.getProperty("circleHub", "4"));
        MAX_SCALE = Double.parseDouble(properties.getProperty("maxScale", "1000"));
        MIN_SCALE = Double.parseDouble(properties.getProperty("minScale", "0.001"));
        LABEL_DEFAULT_X_OFFSET = Double.parseDouble(properties.getProperty("labelDefaultXOffset", "5"));
        LABEL_DEFAULT_Y_OFFSET = Double.parseDouble(properties.getProperty("labelDefaultYOffset", "15"));
    }
}
