package com.fpshud;

import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigCategory;
import me.shedaniel.clothconfig2.impl.builders.*;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;

import java.util.Optional;

public class ConfigScreen {
    public static Screen getConfigScreen(Screen parent) {
        ConfigBuilder builder = ConfigBuilder.create()
                .setParentScreen(parent)
                .setTitle(Text.translatable("title.fpshud.config"));

        ConfigCategory generalOptions = builder.getOrCreateCategory(Text.translatable("category.fpshud.general"));

        BooleanToggleBuilder toggleHUDBuilder = builder.entryBuilder()
                .startBooleanToggle(Text.translatable("option.fpshud.toggleHUD"), FPShudClient.toggleHUD)
                .setDefaultValue(true)
                .setSaveConsumer(newValue -> {
                    FPShudClient.toggleHUD = newValue;
                    ConfigManager.saveConfig();
                })
                .setTooltip(Text.translatable("tooltip.fpshud.toggleHUD"));
        generalOptions.addEntry(toggleHUDBuilder.build());

        IntFieldBuilder xPosBuilder = builder.entryBuilder()
                .startIntField(Text.translatable("option.fpshud.xPos"), FPShudClient.xPos)
                .setDefaultValue(5)
                .setMin(0)
                .setMax(Integer.MAX_VALUE - 1)
                .setSaveConsumer(newValue -> {
                    FPShudClient.xPos = newValue;
                    ConfigManager.saveConfig();
                })
                .setTooltip(Text.translatable("tooltip.fpshud.xPos"));
        generalOptions.addEntry(xPosBuilder.build());

        IntFieldBuilder yPosBuilder = builder.entryBuilder()
                .startIntField(Text.translatable("option.fpshud.yPos"), FPShudClient.yPos)
                .setDefaultValue(5)
                .setMin(0)
                .setMax(Integer.MAX_VALUE - 1)
                .setSaveConsumer(newValue -> {
                    FPShudClient.yPos = newValue;
                    ConfigManager.saveConfig();
                })
                .setTooltip(Text.translatable("tooltip.fpshud.yPos"));
        generalOptions.addEntry(yPosBuilder.build());

        TextFieldBuilder textColorBuilder = builder.entryBuilder()
                .startTextField(Text.translatable("option.fpshud.textColor"), String.format("#%06X", FPShudClient.textColor))
                .setDefaultValue("#FFFFFF")
                .setSaveConsumer(newValue -> {
                    try {
                        FPShudClient.textColor = Integer.parseInt(newValue.replace("#", ""), 16);
                        ConfigManager.saveConfig();
                    } catch (NumberFormatException ignored) {
                    }
                })
                .setTooltip(Text.translatable("tooltip.fpshud.textColor"))
                .setErrorSupplier(newValue -> {
                    if (!newValue.matches("^#?[0-9A-Fa-f]{6}$"))
                        return Optional.of(Text.translatable("error.fpshud.invalidColor"));
                    return Optional.empty();
                });
        generalOptions.addEntry(textColorBuilder.build());

        BooleanToggleBuilder shadowBuilder = builder.entryBuilder()
                .startBooleanToggle(Text.translatable("option.fpshud.shadow"), FPShudClient.shadow)
                .setDefaultValue(true)
                .setSaveConsumer(newValue -> {
                    FPShudClient.shadow = newValue;
                    ConfigManager.saveConfig();
                })
                .setTooltip(Text.translatable("tooltip.fpshud.shadow"));
        generalOptions.addEntry(shadowBuilder.build());

        IntFieldBuilder pollingRateBuilder = builder.entryBuilder()
                .startIntField(Text.translatable("option.fpshud.pollingRate"), FPShudClient.pollingRate)
                .setDefaultValue(100)
                .setMin(1)
                .setMax(FPShudClient.maxPollingRate())
                .setSaveConsumer(newValue -> {
                    FPShudClient.pollingRate = newValue;
                    ConfigManager.saveConfig();
                })
                .setTooltip(Text.translatable("tooltip.fpshud.pollingRate"));
        generalOptions.addEntry(pollingRateBuilder.build());

        ConfigCategory fpsDisplayOptions = builder.getOrCreateCategory(Text.translatable("category.fpshud.fpsDisplay"));

        BooleanToggleBuilder showFpsBuilder = builder.entryBuilder()
                .startBooleanToggle(Text.translatable("option.fpshud.showFps"), FPShudClient.showFps)
                .setDefaultValue(true)
                .setSaveConsumer(newValue -> {
                    FPShudClient.showFps = newValue;
                    ConfigManager.saveConfig();
                })
                .setTooltip(Text.translatable("tooltip.fpshud.showFps"));
        fpsDisplayOptions.addEntry(showFpsBuilder.build());

        IntSliderBuilder fpsPositionBuilder = builder.entryBuilder()
                .startIntSlider(Text.translatable("option.fpshud.fpsPosition"), FPShudClient.fpsPosition, 1, 4)
                .setDefaultValue(1)
                .setSaveConsumer(newValue -> {
                    FPShudClient.fpsPosition = newValue;
                    ConfigManager.saveConfig();
                })
                .setTooltip(Text.translatable("tooltip.fpshud.fpsPosition"));
        fpsDisplayOptions.addEntry(fpsPositionBuilder.build());

        IntSliderBuilder precisionBuilder = builder.entryBuilder()
                .startIntSlider(Text.translatable("option.fpshud.fpsPrecision"), FPShudClient.fpsPrecision, 0, 4)
                .setDefaultValue(0)
                .setSaveConsumer(newValue -> {
                    FPShudClient.fpsPrecision = newValue;
                    ConfigManager.saveConfig();
                })
                .setTooltip(Text.translatable("tooltip.fpshud.fpsPrecision"));
        fpsDisplayOptions.addEntry(precisionBuilder.build());

        TextFieldBuilder beforeFpsBuilder = builder.entryBuilder()
                .startTextField(Text.translatable("option.fpshud.beforeFps"), FPShudClient.beforeFps)
                .setDefaultValue("FPS: ")
                .setSaveConsumer(newValue -> {
                    FPShudClient.beforeFps = newValue;
                    ConfigManager.saveConfig();
                })
                .setTooltip(Text.translatable("tooltip.fpshud.beforeFps"));
        fpsDisplayOptions.addEntry(beforeFpsBuilder.build());

        TextFieldBuilder afterFpsBuilder = builder.entryBuilder()
                .startTextField(Text.translatable("option.fpshud.afterFps"), FPShudClient.afterFps)
                .setDefaultValue(" | ")
                .setSaveConsumer(newValue -> {
                    FPShudClient.afterFps = newValue;
                    ConfigManager.saveConfig();
                })
                .setTooltip(Text.translatable("tooltip.fpshud.afterFps"));
        fpsDisplayOptions.addEntry(afterFpsBuilder.build());

        IntFieldBuilder mainUpdateIntervalBuilder = builder.entryBuilder()
                .startIntField(Text.translatable("option.fpshud.mainUpdateInterval"), FPShudClient.mainUpdateInterval)
                .setDefaultValue(250)
                .setMin(FPShudClient.pollingRate + 1)
                .setMax(3600000)
                .setSaveConsumer(newValue -> {
                    FPShudClient.mainUpdateInterval = newValue;
                    ConfigManager.saveConfig();
                })
                .setTooltip(Text.translatable("tooltip.fpshud.mainUpdateInterval"));
        fpsDisplayOptions.addEntry(mainUpdateIntervalBuilder.build());

        ConfigCategory avrDisplayOptions = builder.getOrCreateCategory(Text.translatable("category.fpshud.avrDisplay"));

        BooleanToggleBuilder showAvrBuilder = builder.entryBuilder()
                .startBooleanToggle(Text.translatable("option.fpshud.showAvr"), FPShudClient.showAvr)
                .setDefaultValue(true)
                .setSaveConsumer(newValue -> {
                    FPShudClient.showAvr = newValue;
                    ConfigManager.saveConfig();
                })
                .setTooltip(Text.translatable("tooltip.fpshud.showAvr"));
        avrDisplayOptions.addEntry(showAvrBuilder.build());

        IntSliderBuilder avrPositionBuilder = builder.entryBuilder()
                .startIntSlider(Text.translatable("option.fpshud.avrPosition"), FPShudClient.avrPosition, 1, 4)
                .setDefaultValue(2)
                .setSaveConsumer(newValue -> {
                    FPShudClient.avrPosition = newValue;
                    ConfigManager.saveConfig();
                })
                .setTooltip(Text.translatable("tooltip.fpshud.avrPosition"));
        avrDisplayOptions.addEntry(avrPositionBuilder.build());

        IntSliderBuilder avrPrecisionBuilder = builder.entryBuilder()
                .startIntSlider(Text.translatable("option.fpshud.avrPrecision"), FPShudClient.avrPrecision, 0, 4)
                .setDefaultValue(0)
                .setSaveConsumer(newValue -> {
                    FPShudClient.avrPrecision = newValue;
                    ConfigManager.saveConfig();
                })
                .setTooltip(Text.translatable("tooltip.fpshud.avrPrecision"));
        avrDisplayOptions.addEntry(avrPrecisionBuilder.build());

        TextFieldBuilder beforeAvrBuilder = builder.entryBuilder()
                .startTextField(Text.translatable("option.fpshud.beforeAvr"), FPShudClient.beforeAvr)
                .setDefaultValue("Avr: ")
                .setSaveConsumer(newValue -> {
                    FPShudClient.beforeAvr = newValue;
                    ConfigManager.saveConfig();
                })
                .setTooltip(Text.translatable("tooltip.fpshud.beforeAvr"));
        avrDisplayOptions.addEntry(beforeAvrBuilder.build());

        TextFieldBuilder afterAvrBuilder = builder.entryBuilder()
                .startTextField(Text.translatable("option.fpshud.afterAvr"), FPShudClient.afterAvr)
                .setDefaultValue(" | ")
                .setSaveConsumer(newValue -> {
                    FPShudClient.afterAvr = newValue;
                    ConfigManager.saveConfig();
                })
                .setTooltip(Text.translatable("tooltip.fpshud.afterAvr"));
        avrDisplayOptions.addEntry(afterAvrBuilder.build());

        IntFieldBuilder avrUpdateIntervalBuilder = builder.entryBuilder()
                .startIntField(Text.translatable("option.fpshud.avrUpdateInterval"), FPShudClient.avrUpdateInterval)
                .setDefaultValue(2000)
                .setMin(FPShudClient.pollingRate + 1)
                .setMax(3600000)
                .setSaveConsumer(newValue -> {
                    FPShudClient.avrUpdateInterval = newValue;
                    ConfigManager.saveConfig();
                })
                .setTooltip(Text.translatable("tooltip.fpshud.avrUpdateInterval"));
        avrDisplayOptions.addEntry(avrUpdateIntervalBuilder.build());

        ConfigCategory maxDisplayOptions = builder.getOrCreateCategory(Text.translatable("category.fpshud.maxDisplay"));

        BooleanToggleBuilder showMaxBuilder = builder.entryBuilder()
                .startBooleanToggle(Text.translatable("option.fpshud.showMax"), FPShudClient.showMax)
                .setDefaultValue(true)
                .setSaveConsumer(newValue -> {
                    FPShudClient.showMax = newValue;
                    ConfigManager.saveConfig();
                })
                .setTooltip(Text.translatable("tooltip.fpshud.showMax"));
        maxDisplayOptions.addEntry(showMaxBuilder.build());

        IntSliderBuilder maxPositionBuilder = builder.entryBuilder()
                .startIntSlider(Text.translatable("option.fpshud.maxPosition"), FPShudClient.maxPosition, 1, 4)
                .setDefaultValue(3)
                .setSaveConsumer(newValue -> {
                    FPShudClient.maxPosition = newValue;
                    ConfigManager.saveConfig();
                })
                .setTooltip(Text.translatable("tooltip.fpshud.maxPosition"));
        maxDisplayOptions.addEntry(maxPositionBuilder.build());

        IntSliderBuilder maxPrecisionBuilder = builder.entryBuilder()
                .startIntSlider(Text.translatable("option.fpshud.maxPrecision"), FPShudClient.maxPrecision, 0, 4)
                .setDefaultValue(0)
                .setSaveConsumer(newValue -> {
                    FPShudClient.maxPrecision = newValue;
                    ConfigManager.saveConfig();
                })
                .setTooltip(Text.translatable("tooltip.fpshud.maxPrecision"));
        maxDisplayOptions.addEntry(maxPrecisionBuilder.build());

        TextFieldBuilder beforeMaxBuilder = builder.entryBuilder()
                .startTextField(Text.translatable("option.fpshud.beforeMax"), FPShudClient.beforeMax)
                .setDefaultValue("Max: ")
                .setSaveConsumer(newValue -> {
                    FPShudClient.beforeMax = newValue;
                    ConfigManager.saveConfig();
                })
                .setTooltip(Text.translatable("tooltip.fpshud.beforeMax"));
        maxDisplayOptions.addEntry(beforeMaxBuilder.build());

        TextFieldBuilder afterMaxBuilder = builder.entryBuilder()
                .startTextField(Text.translatable("option.fpshud.afterMax"), FPShudClient.afterMax)
                .setDefaultValue(" | ")
                .setSaveConsumer(newValue -> {
                    FPShudClient.afterMax = newValue;
                    ConfigManager.saveConfig();
                })
                .setTooltip(Text.translatable("tooltip.fpshud.afterMax"));
        maxDisplayOptions.addEntry(afterMaxBuilder.build());

        IntFieldBuilder maxUpdateIntervalBuilder = builder.entryBuilder()
                .startIntField(Text.translatable("option.fpshud.maxUpdateInterval"), FPShudClient.maxUpdateInterval)
                .setDefaultValue(2000)
                .setMin(FPShudClient.pollingRate + 1)
                .setMax(3600000)
                .setSaveConsumer(newValue -> {
                    FPShudClient.maxUpdateInterval = newValue;
                    ConfigManager.saveConfig();
                })
                .setTooltip(Text.translatable("tooltip.fpshud.maxUpdateInterval"));
        maxDisplayOptions.addEntry(maxUpdateIntervalBuilder.build());

        ConfigCategory minDisplayOptions = builder.getOrCreateCategory(Text.translatable("category.fpshud.minDisplay"));

        BooleanToggleBuilder showMinBuilder = builder.entryBuilder()
                .startBooleanToggle(Text.translatable("option.fpshud.showMin"), FPShudClient.showMin)
                .setDefaultValue(true)
                .setSaveConsumer(newValue -> {
                    FPShudClient.showMin = newValue;
                    ConfigManager.saveConfig();
                })
                .setTooltip(Text.translatable("tooltip.fpshud.showMin"));
        minDisplayOptions.addEntry(showMinBuilder.build());

        IntSliderBuilder minPositionBuilder = builder.entryBuilder()
                .startIntSlider(Text.translatable("option.fpshud.minPosition"), FPShudClient.minPosition, 1, 4)
                .setDefaultValue(4)
                .setSaveConsumer(newValue -> {
                    FPShudClient.minPosition = newValue;
                    ConfigManager.saveConfig();
                })
                .setTooltip(Text.translatable("tooltip.fpshud.minPosition"));
        minDisplayOptions.addEntry(minPositionBuilder.build());

        IntSliderBuilder minPrecisionBuilder = builder.entryBuilder()
                .startIntSlider(Text.translatable("option.fpshud.minPrecision"), FPShudClient.minPrecision, 0, 4)
                .setDefaultValue(0)
                .setSaveConsumer(newValue -> {
                    FPShudClient.minPrecision = newValue;
                    ConfigManager.saveConfig();
                })
                .setTooltip(Text.translatable("tooltip.fpshud.minPrecision"));
        minDisplayOptions.addEntry(minPrecisionBuilder.build());

        TextFieldBuilder beforeMinBuilder = builder.entryBuilder()
                .startTextField(Text.translatable("option.fpshud.beforeMin"), FPShudClient.beforeMin)
                .setDefaultValue("Min: ")
                .setSaveConsumer(newValue -> {
                    FPShudClient.beforeMin = newValue;
                    ConfigManager.saveConfig();
                })
                .setTooltip(Text.translatable("tooltip.fpshud.beforeMin"));
        minDisplayOptions.addEntry(beforeMinBuilder.build());

        TextFieldBuilder afterMinBuilder = builder.entryBuilder()
                .startTextField(Text.translatable("option.fpshud.afterMin"), FPShudClient.afterMin)
                .setDefaultValue("")
                .setSaveConsumer(newValue -> {
                    FPShudClient.afterMin = newValue;
                    ConfigManager.saveConfig();
                })
                .setTooltip(Text.translatable("tooltip.fpshud.afterMin"));
        minDisplayOptions.addEntry(afterMinBuilder.build());

        IntFieldBuilder minUpdateIntervalBuilder = builder.entryBuilder()
                .startIntField(Text.translatable("option.fpshud.minUpdateInterval"), FPShudClient.minUpdateInterval)
                .setDefaultValue(2000)
                .setMin(FPShudClient.pollingRate + 1)
                .setMax(3600000)
                .setSaveConsumer(newValue -> {
                    FPShudClient.minUpdateInterval = newValue;
                    ConfigManager.saveConfig();
                })
                .setTooltip(Text.translatable("tooltip.fpshud.minUpdateInterval"));
        minDisplayOptions.addEntry(minUpdateIntervalBuilder.build());

        return builder.build();
    }
}