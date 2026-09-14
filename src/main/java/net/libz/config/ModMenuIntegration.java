package net.libz.config;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import me.shedaniel.autoconfig.AutoConfigClient;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Environment(EnvType.CLIENT)
public class ModMenuIntegration implements ModMenuApi {

    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        // getConfigScreen moved from AutoConfig to the client-only AutoConfigClient in this
        // cloth-config version (common/client source-set split) - same signature and return type.
        return parent -> AutoConfigClient.getConfigScreen(LibzConfig.class, parent).get();
    }
}