package com.fpshud;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import net.fabricmc.loader.api.FabricLoader;

import java.io.*;
import java.nio.file.Path;

public class ConfigManager {
    private static Config config;
    private static final Path configPath = FabricLoader.getInstance().getConfigDir().resolve("FPShud.json");

    private static class Config {
        boolean toggleHUD;
        int xPos;
        int yPos;
        int textColor;
        boolean shadow;
        int pollingRate;

        boolean showFps;
        int fpsPosition;
        int fpsPrecision;
        String beforeFps;
        String afterFps;
        int mainUpdateInterval;

        boolean showAvr;
        int avrPosition;
        int avrPrecision;
        String beforeAvr;
        String afterAvr;
        int avrUpdateInterval;

        boolean showMax;
        int maxPosition;
        int maxPrecision;
        String beforeMax;
        String afterMax;
        int maxUpdateInterval;

        boolean showMin;
        int minPosition;
        int minPrecision;
        String beforeMin;
        String afterMin;
        int minUpdateInterval;
    }

    protected static void loadConfig() {
        Gson gson = new Gson();
        File configFile = configPath.toFile();
        if (configFile.exists()) {
            try (FileReader reader = new FileReader(configFile)) {
                config = gson.fromJson(reader, Config.class);
                if (config == null)
                    restoreDefaultConfig();
            } catch (JsonSyntaxException | IOException exception) {
                restoreDefaultConfig();
            }
        } else
            restoreDefaultConfig();
        applyConfig();
    }

    private static void restoreDefaultConfig() {
        config = new Config();

        config.toggleHUD = FPShudClient.toggleHUD;
        config.xPos = FPShudClient.xPos;
        config.yPos = FPShudClient.yPos;
        config.textColor = FPShudClient.textColor;
        config.shadow = FPShudClient.shadow;
        config.pollingRate = FPShudClient.pollingRate;

        config.showFps = FPShudClient.showFps;
        config.fpsPosition = FPShudClient.fpsPosition;
        config.fpsPrecision = FPShudClient.fpsPrecision;
        config.beforeFps = FPShudClient.beforeFps;
        config.afterFps = FPShudClient.afterFps;
        config.mainUpdateInterval = FPShudClient.mainUpdateInterval;

        config.showAvr = FPShudClient.showAvr;
        config.avrPosition = FPShudClient.avrPosition;
        config.avrPrecision = FPShudClient.avrPrecision;
        config.beforeAvr = FPShudClient.beforeAvr;
        config.afterAvr = FPShudClient.afterAvr;
        config.avrUpdateInterval = FPShudClient.avrUpdateInterval;

        config.showMax = FPShudClient.showMax;
        config.maxPosition = FPShudClient.maxPosition;
        config.maxPrecision = FPShudClient.maxPrecision;
        config.beforeMax = FPShudClient.beforeMax;
        config.afterMax = FPShudClient.afterMax;
        config.maxUpdateInterval = FPShudClient.maxUpdateInterval;

        config.showMin = FPShudClient.showMin;
        config.minPosition = FPShudClient.minPosition;
        config.minPrecision = FPShudClient.minPrecision;
        config.beforeMin = FPShudClient.beforeMin;
        config.afterMin = FPShudClient.afterMin;
        config.minUpdateInterval = FPShudClient.minUpdateInterval;

        saveConfig();
    }

    private static void applyConfig() {
        FPShudClient.toggleHUD = config.toggleHUD;
        FPShudClient.xPos = config.xPos;
        FPShudClient.yPos = config.yPos;
        FPShudClient.textColor = config.textColor;
        FPShudClient.shadow = config.shadow;
        FPShudClient.pollingRate = config.pollingRate;

        FPShudClient.showFps = config.showFps;
        FPShudClient.fpsPosition = config.fpsPosition;
        FPShudClient.fpsPrecision = config.fpsPrecision;
        FPShudClient.beforeFps = config.beforeFps;
        FPShudClient.afterFps = config.afterFps;
        FPShudClient.mainUpdateInterval = config.mainUpdateInterval;

        FPShudClient.showAvr = config.showAvr;
        FPShudClient.avrPosition = config.avrPosition;
        FPShudClient.avrPrecision = config.avrPrecision;
        FPShudClient.beforeAvr = config.beforeAvr;
        FPShudClient.afterAvr = config.afterAvr;
        FPShudClient.avrUpdateInterval = config.avrUpdateInterval;

        FPShudClient.showMax = config.showMax;
        FPShudClient.maxPosition = config.maxPosition;
        FPShudClient.maxPrecision = config.maxPrecision;
        FPShudClient.beforeMax = config.beforeMax;
        FPShudClient.afterMax = config.afterMax;
        FPShudClient.maxUpdateInterval = config.maxUpdateInterval;

        FPShudClient.showMin = config.showMin;
        FPShudClient.minPosition = config.minPosition;
        FPShudClient.minPrecision = config.minPrecision;
        FPShudClient.beforeMin = config.beforeMin;
        FPShudClient.afterMin = config.afterMin;
        FPShudClient.minUpdateInterval = config.minUpdateInterval;

        saveConfig();
    }

    protected static void saveConfig() {
        Gson gson = new Gson();
        File configFile = configPath.toFile();
        Config currentConfig = new Config();

        currentConfig.toggleHUD = FPShudClient.toggleHUD;
        currentConfig.xPos = FPShudClient.xPos;
        currentConfig.yPos = FPShudClient.yPos;
        currentConfig.textColor = FPShudClient.textColor;
        currentConfig.shadow = FPShudClient.shadow;
        currentConfig.pollingRate = FPShudClient.pollingRate;

        currentConfig.showFps = FPShudClient.showFps;
        currentConfig.fpsPosition = FPShudClient.fpsPosition;
        currentConfig.fpsPrecision = FPShudClient.fpsPrecision;
        currentConfig.beforeFps = FPShudClient.beforeFps;
        currentConfig.afterFps = FPShudClient.afterFps;
        currentConfig.mainUpdateInterval = FPShudClient.mainUpdateInterval;

        currentConfig.showAvr = FPShudClient.showAvr;
        currentConfig.avrPosition = FPShudClient.avrPosition;
        currentConfig.avrPrecision = FPShudClient.avrPrecision;
        currentConfig.beforeAvr = FPShudClient.beforeAvr;
        currentConfig.afterAvr = FPShudClient.afterAvr;
        currentConfig.avrUpdateInterval = FPShudClient.avrUpdateInterval;

        currentConfig.showMax = FPShudClient.showMax;
        currentConfig.maxPosition = FPShudClient.maxPosition;
        currentConfig.maxPrecision = FPShudClient.maxPrecision;
        currentConfig.beforeMax = FPShudClient.beforeMax;
        currentConfig.afterMax = FPShudClient.afterMax;
        currentConfig.maxUpdateInterval = FPShudClient.maxUpdateInterval;

        currentConfig.showMin = FPShudClient.showMin;
        currentConfig.minPosition = FPShudClient.minPosition;
        currentConfig.minPrecision = FPShudClient.minPrecision;
        currentConfig.beforeMin = FPShudClient.beforeMin;
        currentConfig.afterMin = FPShudClient.afterMin;
        currentConfig.minUpdateInterval = FPShudClient.minUpdateInterval;

        try (FileWriter writer = new FileWriter(configFile)) {
            gson.toJson(currentConfig, writer);
        } catch (IOException exception) {
            restoreDefaultConfig();
        }
    }
}