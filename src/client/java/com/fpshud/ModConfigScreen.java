package com.fpshud;

import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigCategory;
import me.shedaniel.clothconfig2.impl.builders.*;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;

import java.util.Optional;

public class ModConfigScreen {
    public static Screen getConfigScreen(Screen parent) {
        ConfigBuilder builder = ConfigBuilder.create()
                .setParentScreen(parent)
                .setTitle(Text.translatable("title.fpshud.config"));

        ConfigCategory hudOptions = builder.getOrCreateCategory(Text.translatable("category.fpshud.hudOptions"));

        BooleanToggleBuilder toggleHUDBuilder = builder.entryBuilder()
                .startBooleanToggle(Text.translatable("option.fpshud.toggleHUD"), FPShudClient.isToggleHUD())
                .setDefaultValue(true)
                .setSaveConsumer(FPShudClient::setToggleHUD)
                .setTooltip(Text.translatable("tooltip.fpshud.toggleHUD"));
        hudOptions.addEntry(toggleHUDBuilder.build());

        BooleanToggleBuilder showFpsBuilder = builder.entryBuilder()
                .startBooleanToggle(Text.translatable("option.fpshud.showFps"), FPShudClient.isShowFps())
                .setDefaultValue(true)
                .setSaveConsumer(FPShudClient::setShowFps)
                .setTooltip(Text.translatable("tooltip.fpshud.showFps"));
        hudOptions.addEntry(showFpsBuilder.build());

        BooleanToggleBuilder showAvrBuilder = builder.entryBuilder()
                .startBooleanToggle(Text.translatable("option.fpshud.showAvr"), FPShudClient.isShowAvr())
                .setDefaultValue(true)
                .setSaveConsumer(FPShudClient::setShowAvr)
                .setTooltip(Text.translatable("tooltip.fpshud.showAvr"));
        hudOptions.addEntry(showAvrBuilder.build());

        BooleanToggleBuilder showMaxBuilder = builder.entryBuilder()
                .startBooleanToggle(Text.translatable("option.fpshud.showMax"), FPShudClient.isShowMax())
                .setDefaultValue(true)
                .setSaveConsumer(FPShudClient::setShowMax)
                .setTooltip(Text.translatable("tooltip.fpshud.showMax"));
        hudOptions.addEntry(showMaxBuilder.build());

        BooleanToggleBuilder showMinBuilder = builder.entryBuilder()
                .startBooleanToggle(Text.translatable("option.fpshud.showMin"), FPShudClient.isShowMin())
                .setDefaultValue(true)
                .setSaveConsumer(FPShudClient::setShowMin)
                .setTooltip(Text.translatable("tooltip.fpshud.showMin"));
        hudOptions.addEntry(showMinBuilder.build());

        IntFieldBuilder xPosBuilder = builder.entryBuilder()
                .startIntField(Text.translatable("option.fpshud.xPos"), FPShudClient.getXPos())
                .setDefaultValue(5)
                .setMin(0)
                .setMax(Integer.MAX_VALUE - 1)
                .setSaveConsumer(FPShudClient::setXPos)
                .setTooltip(Text.translatable("tooltip.fpshud.xPos"));
        hudOptions.addEntry(xPosBuilder.build());

        IntFieldBuilder yPosBuilder = builder.entryBuilder()
                .startIntField(Text.translatable("option.fpshud.yPos"), FPShudClient.getYPos())
                .setDefaultValue(5)
                .setMin(0)
                .setMax(Integer.MAX_VALUE - 1)
                .setSaveConsumer(FPShudClient::setYPos)
                .setTooltip(Text.translatable("tooltip.fpshud.yPos"));
        hudOptions.addEntry(yPosBuilder.build());

        TextFieldBuilder textColorBuilder = builder.entryBuilder()
                .startTextField(Text.translatable("option.fpshud.textColor"), String.format("#%06X", FPShudClient.getTextColor()))
                .setDefaultValue("#FFFFFF")
                .setSaveConsumer(newValue -> {
                    try {
                        int colorValue = Integer.parseInt(newValue.replace("#", ""), 16);
                        FPShudClient.setTextColor(colorValue);
                    } catch (NumberFormatException ignored) {}
                })
                .setTooltip(Text.translatable("tooltip.fpshud.textColor"))
                .setErrorSupplier(newValue -> {
                    if (!newValue.matches("^#?[0-9A-Fa-f]{6}$"))
                        return Optional.of(Text.translatable("error.fpshud.invalidColor"));
                    return Optional.empty();
                });
        hudOptions.addEntry(textColorBuilder.build());

        BooleanToggleBuilder shadowBuilder = builder.entryBuilder()
                .startBooleanToggle(Text.translatable("option.fpshud.shadow"), FPShudClient.isShadow())
                .setDefaultValue(true)
                .setSaveConsumer(FPShudClient::setShadow)
                .setTooltip(Text.translatable("tooltip.fpshud.shadow"));
        hudOptions.addEntry(shadowBuilder.build());

        TextFieldBuilder beforeFpsBuilder = builder.entryBuilder()
                .startTextField(Text.translatable("option.fpshud.beforeFps"), FPShudClient.getBeforeFps())
                .setDefaultValue("FPS: ")
                .setSaveConsumer(FPShudClient::setBeforeFps)
                .setTooltip(Text.translatable("tooltip.fpshud.beforeFps"));
        hudOptions.addEntry(beforeFpsBuilder.build());

        TextFieldBuilder afterFpsBuilder = builder.entryBuilder()
                .startTextField(Text.translatable("option.fpshud.afterFps"), FPShudClient.getAfterFps())
                .setDefaultValue("")
                .setSaveConsumer(FPShudClient::setAfterFps)
                .setTooltip(Text.translatable("tooltip.fpshud.afterFps"));
        hudOptions.addEntry(afterFpsBuilder.build());

        TextFieldBuilder beforeAvrBuilder = builder.entryBuilder()
                .startTextField(Text.translatable("option.fpshud.beforeAvr"), FPShudClient.getBeforeAvr())
                .setDefaultValue("Avr: ")
                .setSaveConsumer(FPShudClient::setBeforeAvr)
                .setTooltip(Text.translatable("tooltip.fpshud.beforeAvr"));
        hudOptions.addEntry(beforeAvrBuilder.build());

        TextFieldBuilder afterAvrBuilder = builder.entryBuilder()
                .startTextField(Text.translatable("option.fpshud.afterAvr"), FPShudClient.getAfterAvr())
                .setDefaultValue("")
                .setSaveConsumer(FPShudClient::setAfterAvr)
                .setTooltip(Text.translatable("tooltip.fpshud.afterAvr"));
        hudOptions.addEntry(afterAvrBuilder.build());

        TextFieldBuilder beforeMaxBuilder = builder.entryBuilder()
                .startTextField(Text.translatable("option.fpshud.beforeMax"), FPShudClient.getBeforeMax())
                .setDefaultValue("Max: ")
                .setSaveConsumer(FPShudClient::setBeforeMax)
                .setTooltip(Text.translatable("tooltip.fpshud.beforeMax"));
        hudOptions.addEntry(beforeMaxBuilder.build());

        TextFieldBuilder afterMaxBuilder = builder.entryBuilder()
                .startTextField(Text.translatable("option.fpshud.afterMax"), FPShudClient.getAfterMax())
                .setDefaultValue("")
                .setSaveConsumer(FPShudClient::setAfterMax)
                .setTooltip(Text.translatable("tooltip.fpshud.afterMax"));
        hudOptions.addEntry(afterMaxBuilder.build());

        TextFieldBuilder beforeMinBuilder = builder.entryBuilder()
                .startTextField(Text.translatable("option.fpshud.beforeMin"), FPShudClient.getBeforeMin())
                .setDefaultValue("Min: ")
                .setSaveConsumer(FPShudClient::setBeforeMin)
                .setTooltip(Text.translatable("tooltip.fpshud.beforeMin"));
        hudOptions.addEntry(beforeMinBuilder.build());

        TextFieldBuilder afterMinBuilder = builder.entryBuilder()
                .startTextField(Text.translatable("option.fpshud.afterMin"), FPShudClient.getAfterMin())
                .setDefaultValue("")
                .setSaveConsumer(FPShudClient::setAfterMin)
                .setTooltip(Text.translatable("tooltip.fpshud.afterMin"));
        hudOptions.addEntry(afterMinBuilder.build());

        TextFieldBuilder separatorBuilder = builder.entryBuilder()
                .startTextField(Text.translatable("option.fpshud.separator"), FPShudClient.getSeparator())
                .setDefaultValue(" | ")
                .setSaveConsumer(FPShudClient::setSeparator)
                .setTooltip(Text.translatable("tooltip.fpshud.separator"));
        hudOptions.addEntry(separatorBuilder.build());


        ConfigCategory advancedOptions = builder.getOrCreateCategory(Text.translatable("category.fpshud.advanced"));

        IntFieldBuilder pollingRateBuilder = builder.entryBuilder()
                .startIntField(Text.translatable("option.fpshud.pollingRate"), FPShudClient.getPollingRate())
                .setDefaultValue(100)
                .setMin(1)
                .setMax(FPShudClient.maxPollingRate())
                .setSaveConsumer(FPShudClient::setPollingRate)
                .setTooltip(Text.translatable("tooltip.fpshud.pollingRate"));
        advancedOptions.addEntry(pollingRateBuilder.build());

        IntFieldBuilder mainUpdateIntervalBuilder = builder.entryBuilder()
                .startIntField(Text.translatable("option.fpshud.mainUpdateInterval"), FPShudClient.getMainUpdateInterval())
                .setDefaultValue(200)
                .setMin(FPShudClient.getPollingRate() + 1)
                .setMax(Integer.MAX_VALUE - 1)
                .setSaveConsumer(FPShudClient::setMainUpdateInterval)
                .setTooltip(Text.translatable("tooltip.fpshud.mainUpdateInterval"));
        advancedOptions.addEntry(mainUpdateIntervalBuilder.build());

        IntFieldBuilder avrUpdateIntervalBuilder = builder.entryBuilder()
                .startIntField(Text.translatable("option.fpshud.avrUpdateInterval"), FPShudClient.getAvrUpdateInterval())
                .setDefaultValue(2000)
                .setMin(FPShudClient.getPollingRate() + 1)
                .setMax(Integer.MAX_VALUE - 1)
                .setSaveConsumer(FPShudClient::setAvrUpdateInterval)
                .setTooltip(Text.translatable("tooltip.fpshud.avrUpdateInterval"));
        advancedOptions.addEntry(avrUpdateIntervalBuilder.build());

        IntFieldBuilder maxUpdateIntervalBuilder = builder.entryBuilder()
                .startIntField(Text.translatable("option.fpshud.maxUpdateInterval"), FPShudClient.getMaxUpdateInterval())
                .setDefaultValue(2000)
                .setMin(FPShudClient.getPollingRate() + 1)
                .setMax(Integer.MAX_VALUE - 1)
                .setSaveConsumer(FPShudClient::setMaxUpdateInterval)
                .setTooltip(Text.translatable("tooltip.fpshud.maxUpdateInterval"));
        advancedOptions.addEntry(maxUpdateIntervalBuilder.build());

        IntFieldBuilder minUpdateIntervalBuilder = builder.entryBuilder()
                .startIntField(Text.translatable("option.fpshud.minUpdateInterval"), FPShudClient.getMinUpdateInterval())
                .setDefaultValue(2000)
                .setMin(FPShudClient.getPollingRate() + 1)
                .setMax(Integer.MAX_VALUE - 1)
                .setSaveConsumer(FPShudClient::setMinUpdateInterval)
                .setTooltip(Text.translatable("tooltip.fpshud.minUpdateInterval"));
        advancedOptions.addEntry(minUpdateIntervalBuilder.build());

        IntSliderBuilder precisionBuilder = builder.entryBuilder()
                .startIntSlider(Text.translatable("option.fpshud.precision"), FPShudClient.getPrecision(), 0, 4)
                .setDefaultValue(0)
                .setSaveConsumer(FPShudClient::setPrecision)
                .setTooltip(Text.translatable("tooltip.fpshud.precision"));
        advancedOptions.addEntry(precisionBuilder.build());

        return builder.build();
    }
}