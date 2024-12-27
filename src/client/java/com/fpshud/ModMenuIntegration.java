package com.fpshud;

import com.terraformersmc.modmenu.api.*;

public class ModMenuIntegration implements ModMenuApi {
    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return ModConfigScreen::getConfigScreen;
    }
}