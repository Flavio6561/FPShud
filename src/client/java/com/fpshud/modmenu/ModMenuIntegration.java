package com.fpshud.modmenu;

import com.fpshud.ConfigScreen;
import com.terraformersmc.modmenu.api.*;

public class ModMenuIntegration implements ModMenuApi {
    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return ConfigScreen::getConfigScreen;
    }
}