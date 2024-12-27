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

	private final LinkedList<Double> fpsHistory = new LinkedList<>();
	private final LinkedList<Double> avrFpsHistory = new LinkedList<>();
	private final LinkedList<Double> maxFpsHistory = new LinkedList<>();
	private final LinkedList<Double> minFpsHistory = new LinkedList<>();

	private long lastPollTime = System.currentTimeMillis();
	
	private long lastFrameTime = System.nanoTime();

	private double lastPoll = 0;
	private double lastTimeMainClear = 0;
	private double lastTimeAvrClear = 0;
	private double lastTimeMaxClear = 0;
	private double lastTimeMinClear = 0;

	private double mainFps = 0.0;

	private double displayFps = 0.0;
	private double avrFps = 0.0;
	private double maxFps = 0.0;
	private double minFps = 0.0;

	private static int pollingRate = 100;

	private static int mainUpdateInterval = 200;
	private static int avrUpdateInterval = 2000;
	private static int maxUpdateInterval = 2000;
	private static int minUpdateInterval = 2000;

	private static boolean toggleHUD = true;
	private static int xPos = 5;
	private static int yPos = 5;
	private static int textColor = 0xFFFFFF;
	private static boolean shadow = true;
	private static int precision = 0;
	private static boolean showFps = true;
	private static boolean showAvr = true;
	private static boolean showMax = true;
	private static boolean showMin = true;

	private static String beforeFps = "FPS: ";
	private static String afterFps = "";
	private static String beforeAvr = "Avr: ";
	private static String afterAvr = "";
	private static String beforeMax = "Max: ";
	private static String afterMax = "";
	private static String beforeMin = "Min: ";
	private static String afterMin = "";
	private static String separator = " | ";

	@Override
	public void onInitializeClient() {
		ConfigManager.loadConfig();
		HudRenderCallback.EVENT.register(this::onHudRender);
		WorldRenderEvents.END.register(context -> updateFps());
		LOGGER.info("FPShud initialized with success");
	}

	public void updateFps() {
		long currentTime = System.nanoTime();
		long deltaTime = currentTime - lastFrameTime;
		lastFrameTime = currentTime;
		fpsHistory.add((double) 1_000_000_000L / deltaTime);
		incrementVariables();
	}

	public void incrementVariables() {
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
			for (double fps : fpsHistory) {
				acc += fps;
			}
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
		if (toggleHUD) drawContext.drawText(client.textRenderer, getFullString(), xPos, yPos, textColor, shadow);
	}

	public String getFullString() {
		String currentPrecision = "%." + precision + "f";
		StringBuilder fpsDisplay = new StringBuilder();
		if (showFps)
			fpsDisplay.append(beforeFps).append(String.format(currentPrecision, displayFps)).append(afterFps);
		if (showAvr) {
			if (!showFps)
				fpsDisplay.append(beforeAvr).append(String.format(currentPrecision, avrFps)).append(afterAvr);
			else
				fpsDisplay.append(separator).append(beforeAvr).append(String.format(currentPrecision, avrFps)).append(afterAvr);
		}
		if (showMax) {
			if (!showFps && !showAvr)
				fpsDisplay.append(beforeMax).append(String.format(currentPrecision, maxFps)).append(afterMax);
			else
				fpsDisplay.append(separator).append(beforeMax).append(String.format(currentPrecision, maxFps)).append(afterMax);
		}
		if (showMin) {
			if (!showFps && !showAvr && !showMax)
				fpsDisplay.append(beforeMin).append(String.format(currentPrecision, minFps)).append(afterMin);
			else
				fpsDisplay.append(separator).append(beforeMin).append(String.format(currentPrecision, minFps)).append(afterMin);
		}

		return fpsDisplay.toString();
	}

	public double calcAvrFps () {
		double acc = 0;
		for (double fps : avrFpsHistory)
			acc += fps;
		return acc / avrFpsHistory.size();
	}

	public double calcMaxFps () {
		double max = mainFps;
		for (double fps : maxFpsHistory) {
			if (fps > max)
				max = fps;
		}
		return max;
	}

	public double calcMinFps () {
		double min = mainFps;
		for (double fps : minFpsHistory) {
			if (fps < min)
				min = fps;
		}
		return min;
	}

	public static int maxPollingRate() {
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

	public static int getPollingRate() {
		return pollingRate;
	}

	public static int getMainUpdateInterval() {
		return mainUpdateInterval;
	}

	public static int getAvrUpdateInterval() {
		return avrUpdateInterval;
	}

	public static int getMaxUpdateInterval() {
		return maxUpdateInterval;
	}

	public static int getMinUpdateInterval() {
		return minUpdateInterval;
	}

	public static boolean isToggleHUD() {
		return toggleHUD;
	}

	public static int getXPos() {
		return xPos;
	}

	public static int getYPos() {
		return yPos;
	}

	public static int getTextColor() {
		return textColor;
	}

	public static boolean isShadow() {
		return shadow;
	}

	public static int getPrecision() {
		return precision;
	}

	public static boolean isShowFps() {
		return showFps;
	}

	public static boolean isShowAvr() {
		return showAvr;
	}

	public static boolean isShowMax() {
		return showMax;
	}

	public static boolean isShowMin() {
		return showMin;
	}

	public static String getBeforeFps() {
		return beforeFps;
	}

	public static String getAfterFps() {
		return afterFps;
	}

	public static String getBeforeAvr() {
		return beforeAvr;
	}

	public static String getAfterAvr() {
		return afterAvr;
	}

	public static String getBeforeMax() {
		return beforeMax;
	}

	public static String getAfterMax() {
		return afterMax;
	}

	public static String getBeforeMin() {
		return beforeMin;
	}

	public static String getAfterMin() {
		return afterMin;
	}

	public static String getSeparator() {
		return separator;
	}

	public static void setPollingRate(int pollingRate) {
		FPShudClient.pollingRate = pollingRate;
		ConfigManager.saveConfig();
	}

	public static void setMainUpdateInterval(int mainUpdateInterval) {
		FPShudClient.mainUpdateInterval = mainUpdateInterval;
		ConfigManager.saveConfig();
	}

	public static void setAvrUpdateInterval(int avrUpdateInterval) {
		FPShudClient.avrUpdateInterval = avrUpdateInterval;
		ConfigManager.saveConfig();
	}

	public static void setMaxUpdateInterval(int maxUpdateInterval) {
		FPShudClient.maxUpdateInterval = maxUpdateInterval;
		ConfigManager.saveConfig();
	}

	public static void setMinUpdateInterval(int minUpdateInterval) {
		FPShudClient.minUpdateInterval = minUpdateInterval;
		ConfigManager.saveConfig();
	}

	public static void setToggleHUD(boolean toggleHUD) {
		FPShudClient.toggleHUD = toggleHUD;
		ConfigManager.saveConfig();
	}

	public static void setXPos(int xPos) {
		FPShudClient.xPos = xPos;
		ConfigManager.saveConfig();
	}

	public static void setYPos(int yPos) {
		FPShudClient.yPos = yPos;
		ConfigManager.saveConfig();
	}

	public static void setTextColor(int textColor) {
		FPShudClient.textColor = textColor;
		ConfigManager.saveConfig();
	}

	public static void setShadow(boolean shadow) {
		FPShudClient.shadow = shadow;
		ConfigManager.saveConfig();
	}

	public static void setPrecision(int precision) {
		FPShudClient.precision = precision;
		ConfigManager.saveConfig();
	}

	public static void setShowFps(boolean showFps) {
		FPShudClient.showFps = showFps;
		ConfigManager.saveConfig();
	}

	public static void setShowAvr(boolean showAvr) {
		FPShudClient.showAvr = showAvr;
		ConfigManager.saveConfig();
	}

	public static void setShowMax(boolean showMax) {
		FPShudClient.showMax = showMax;
		ConfigManager.saveConfig();
	}

	public static void setShowMin(boolean showMin) {
		FPShudClient.showMin = showMin;
		ConfigManager.saveConfig();
	}

	public static void setBeforeFps(String beforeFps) {
		FPShudClient.beforeFps = beforeFps;
		ConfigManager.saveConfig();
	}

	public static void setAfterFps(String afterFps) {
		FPShudClient.afterFps = afterFps;
		ConfigManager.saveConfig();
	}

	public static void setBeforeAvr(String beforeAvr) {
		FPShudClient.beforeAvr = beforeAvr;
		ConfigManager.saveConfig();
	}

	public static void setAfterAvr(String afterAvr) {
		FPShudClient.afterAvr = afterAvr;
		ConfigManager.saveConfig();
	}

	public static void setBeforeMax(String beforeMax) {
		FPShudClient.beforeMax = beforeMax;
		ConfigManager.saveConfig();
	}

	public static void setAfterMax(String afterMax) {
		FPShudClient.afterMax = afterMax;
		ConfigManager.saveConfig();
	}

	public static void setBeforeMin(String beforeMin) {
		FPShudClient.beforeMin = beforeMin;
		ConfigManager.saveConfig();
	}

	public static void setAfterMin(String afterMin) {
		FPShudClient.afterMin = afterMin;
		ConfigManager.saveConfig();
	}

	public static void setSeparator(String separator) {
		FPShudClient.separator = separator;
		ConfigManager.saveConfig();
	}
}