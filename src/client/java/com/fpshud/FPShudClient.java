package com.fpshud;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.RenderTickCounter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderEvents;

import java.util.LinkedList;

public class FPShudClient implements ClientModInitializer {
    private static final Logger LOGGER = LoggerFactory.getLogger(FPShudClient.class);
    private static final LinkedList<Double> fpsHistory = new LinkedList<>();
    private static final LinkedList<Double> avrFpsHistory = new LinkedList<>();
    private static final LinkedList<Double> maxFpsHistory = new LinkedList<>();
    private static final LinkedList<Double> minFpsHistory = new LinkedList<>();
    private static long lastPollTime = System.currentTimeMillis();
    private static long lastFrameTime = System.nanoTime();
    private static double lastPoll = 0;
    private static double lastTimeMainClear = 0;
    private static double lastTimeAvrClear = 0;
    private static double lastTimeMaxClear = 0;
    private static double lastTimeMinClear = 0;
    private static double mainFps = 0.0;
    private static double displayFps = 0.0;
    private static double avrFps = 0.0;
    private static double maxFps = 0.0;
    private static double minFps = 0.0;

    protected static boolean toggleHUD = true;
    protected static int xPos = 5;
    protected static int yPos = 5;
    protected static int textColor = 0xFFFFFF;
    protected static boolean shadow = true;
    protected static int pollingRate = 100;

    protected static boolean showFps = true;
    protected static int fpsPosition = 1;
    protected static int fpsPrecision = 0;
    protected static String beforeFps = "FPS: ";
    protected static String afterFps = " | ";
    protected static int mainUpdateInterval = 250;

    protected static boolean showAvr = true;
    protected static int avrPosition = 2;
    protected static int avrPrecision = 0;
    protected static String beforeAvr = "Avr: ";
    protected static String afterAvr = " | ";
    protected static int avrUpdateInterval = 2000;

    protected static boolean showMax = true;
    protected static int maxPosition = 3;
    protected static int maxPrecision = 0;
    protected static String beforeMax = "Max: ";
    protected static String afterMax = " | ";
    protected static int maxUpdateInterval = 2000;

    protected static boolean showMin = true;
    protected static int minPosition = 4;
    protected static int minPrecision = 0;
    protected static String beforeMin = "Min: ";
    protected static String afterMin = "";
    protected static int minUpdateInterval = 2000;

    @Override
    public void onInitializeClient() {
        ConfigManager.loadConfig();
        HudRenderCallback.EVENT.register(this::onHudRender);
        WorldRenderEvents.END.register(context -> updateFps());
        LOGGER.info("FPShud initialized");
    }

    private static void updateFps() {
        long currentTime = System.nanoTime();
        long deltaTime = currentTime - lastFrameTime;
        lastFrameTime = currentTime;
        fpsHistory.add((double) 1_000_000_000L / deltaTime);
        incrementVariables();
    }

    private static void incrementVariables() {
        long deltaTime = System.currentTimeMillis() - lastPollTime;

        if (deltaTime > 0) {
            lastPoll += deltaTime;
            lastTimeMainClear += deltaTime;
            lastTimeAvrClear += deltaTime;
            lastTimeMaxClear += deltaTime;
            lastTimeMinClear += deltaTime;
            lastPollTime = System.currentTimeMillis();
        }
        if (lastPoll > pollingRate) {
            double acc = 0;
            for (double fps : fpsHistory)
                acc += fps;
            mainFps = acc / fpsHistory.size();
            fpsHistory.clear();
            avrFpsHistory.add(mainFps);
            maxFpsHistory.add(mainFps);
            minFpsHistory.add(mainFps);
            lastPoll = 0;
        }
        if (lastTimeMainClear > mainUpdateInterval) {
            displayFps = mainFps;
            lastTimeMainClear = 0;
        }
        if (lastTimeAvrClear > avrUpdateInterval) {
            avrFps = calcAvrFps();
            avrFpsHistory.clear();
            lastTimeAvrClear = 0;
        }
        if (lastTimeMaxClear > maxUpdateInterval) {
            maxFps = calcMaxFps();
            maxFpsHistory.clear();
            lastTimeMaxClear = 0;
        }
        if (lastTimeMinClear > minUpdateInterval) {
            minFps = calcMinFps();
            minFpsHistory.clear();
            lastTimeMinClear = 0;
        }
    }

    public void onHudRender(DrawContext drawContext, RenderTickCounter renderTickCounter) {
        MinecraftClient client = MinecraftClient.getInstance();
        if (toggleHUD)
            drawContext.drawText(client.textRenderer, getFullString(), xPos, yPos, textColor, shadow);
    }

    public String getFullString() {
        String[] components = new String[4];

        if (showFps)
            components[fpsPosition - 1] = beforeFps + String.format(("%." + fpsPrecision + "f"), displayFps) + afterFps;
        if (showAvr)
            components[avrPosition - 1] = beforeAvr + String.format(("%." + avrPrecision + "f"), avrFps) + afterAvr;
        if (showMax)
            components[maxPosition - 1] = beforeMax + String.format(("%." + maxPrecision + "f"), maxFps) + afterMax;
        if (showMin)
            components[minPosition - 1] = beforeMin + String.format(("%." + minPrecision + "f"), minFps) + afterMin;

        StringBuilder fpsDisplay = new StringBuilder();
        for (String component : components) {
            if (component != null)
                fpsDisplay.append(component);
        }

        return fpsDisplay.toString();
    }

    private static double calcAvrFps() {
        double acc = 0;
        for (double fps : avrFpsHistory)
            acc += fps;
        return acc / avrFpsHistory.size();
    }

    private static double calcMaxFps() {
        double max = mainFps;
        for (double fps : maxFpsHistory) {
            if (fps > max)
                max = fps;
        }
        return max;
    }

    private static double calcMinFps() {
        double min = mainFps;
        for (double fps : minFpsHistory) {
            if (fps < min)
                min = fps;
        }
        return min;
    }

    protected static int maxPollingRate() {
        int min = Integer.MAX_VALUE;
        if (mainUpdateInterval < min)
            min = mainUpdateInterval;
        if (avrUpdateInterval < min)
            min = avrUpdateInterval;
        if (maxUpdateInterval < min)
            min = maxUpdateInterval;
        if (minUpdateInterval < min)
            min = maxUpdateInterval;
        return min - 1;
    }
}