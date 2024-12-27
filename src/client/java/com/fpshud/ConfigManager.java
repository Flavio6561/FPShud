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
        int pollingRate;
        int mainUpdateInterval;
        int avrUpdateInterval;
        int maxUpdateInterval;
        int minUpdateInterval;
        boolean toggleHUD;
        int xPos;
        int yPos;
        int textColor;
        boolean shadow;
        int precision;
        boolean showFps;
        boolean showAvr;
        boolean showMax;
        boolean showMin;
        String beforeFps;
        String afterFps;
        String beforeAvr;
        String afterAvr;
        String beforeMax;
        String afterMax;
        String beforeMin;
        String afterMin;
        String separator;
    }

    public static void loadConfig() {
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
        config.pollingRate = FPShudClient.getPollingRate();
        config.mainUpdateInterval = FPShudClient.getMainUpdateInterval();
        config.avrUpdateInterval = FPShudClient.getAvrUpdateInterval();
        config.maxUpdateInterval = FPShudClient.getMaxUpdateInterval();
        config.minUpdateInterval = FPShudClient.getMinUpdateInterval();
        config.toggleHUD = FPShudClient.isToggleHUD();
        config.xPos = FPShudClient.getXPos();
        config.yPos = FPShudClient.getYPos();
        config.textColor = FPShudClient.getTextColor();
        config.shadow = FPShudClient.isShadow();
        config.precision = FPShudClient.getPrecision();
        config.showFps = FPShudClient.isShowFps();
        config.showAvr = FPShudClient.isShowAvr();
        config.showMax = FPShudClient.isShowMax();
        config.showMin = FPShudClient.isShowMin();
        config.beforeFps = FPShudClient.getBeforeFps();
        config.afterFps = FPShudClient.getAfterFps();
        config.beforeAvr = FPShudClient.getBeforeAvr();
        config.afterAvr = FPShudClient.getAfterAvr();
        config.beforeMax = FPShudClient.getBeforeMax();
        config.afterMax = FPShudClient.getAfterMax();
        config.beforeMin = FPShudClient.getBeforeMin();
        config.afterMin = FPShudClient.getAfterMin();
        config.separator = FPShudClient.getSeparator();
        saveConfig();
    }

    private static void applyConfig() {
        FPShudClient.setPollingRate(config.pollingRate);
        FPShudClient.setMainUpdateInterval(config.mainUpdateInterval);
        FPShudClient.setAvrUpdateInterval(config.avrUpdateInterval);
        FPShudClient.setMaxUpdateInterval(config.maxUpdateInterval);
        FPShudClient.setMinUpdateInterval(config.minUpdateInterval);
        FPShudClient.setToggleHUD(config.toggleHUD);
        FPShudClient.setXPos(config.xPos);
        FPShudClient.setYPos(config.yPos);
        FPShudClient.setTextColor(config.textColor);
        FPShudClient.setShadow(config.shadow);
        FPShudClient.setPrecision(config.precision);
        FPShudClient.setShowFps(config.showFps);
        FPShudClient.setShowAvr(config.showAvr);
        FPShudClient.setShowMax(config.showMax);
        FPShudClient.setShowMin(config.showMin);
        FPShudClient.setBeforeFps(config.beforeFps);
        FPShudClient.setAfterFps(config.afterFps);
        FPShudClient.setBeforeAvr(config.beforeAvr);
        FPShudClient.setAfterAvr(config.afterAvr);
        FPShudClient.setBeforeMax(config.beforeMax);
        FPShudClient.setAfterMax(config.afterMax);
        FPShudClient.setBeforeMin(config.beforeMin);
        FPShudClient.setAfterMin(config.afterMin);
        FPShudClient.setSeparator(config.separator);
    }

    public static void saveConfig() {
        Gson gson = new Gson();
        File configFile = configPath.toFile();
        Config currentConfig = new Config();
        currentConfig.pollingRate = FPShudClient.getPollingRate();
        currentConfig.mainUpdateInterval = FPShudClient.getMainUpdateInterval();
        currentConfig.avrUpdateInterval = FPShudClient.getAvrUpdateInterval();
        currentConfig.maxUpdateInterval = FPShudClient.getMaxUpdateInterval();
        currentConfig.minUpdateInterval = FPShudClient.getMinUpdateInterval();
        currentConfig.toggleHUD = FPShudClient.isToggleHUD();
        currentConfig.xPos = FPShudClient.getXPos();
        currentConfig.yPos = FPShudClient.getYPos();
        currentConfig.textColor = FPShudClient.getTextColor();
        currentConfig.shadow = FPShudClient.isShadow();
        currentConfig.precision = FPShudClient.getPrecision();
        currentConfig.showFps = FPShudClient.isShowFps();
        currentConfig.showAvr = FPShudClient.isShowAvr();
        currentConfig.showMax = FPShudClient.isShowMax();
        currentConfig.showMin = FPShudClient.isShowMin();
        currentConfig.beforeFps = FPShudClient.getBeforeFps();
        currentConfig.afterFps = FPShudClient.getAfterFps();
        currentConfig.beforeAvr = FPShudClient.getBeforeAvr();
        currentConfig.afterAvr = FPShudClient.getAfterAvr();
        currentConfig.beforeMax = FPShudClient.getBeforeMax();
        currentConfig.afterMax = FPShudClient.getAfterMax();
        currentConfig.beforeMin = FPShudClient.getBeforeMin();
        currentConfig.afterMin = FPShudClient.getAfterMin();
        currentConfig.separator = FPShudClient.getSeparator();

        try (FileWriter writer = new FileWriter(configFile)) {
            gson.toJson(currentConfig, writer);
        } catch (IOException exception) {
            restoreDefaultConfig();
        }
    }
}