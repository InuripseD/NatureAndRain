package fr.inuripse.naturerain.util;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.minecraftforge.client.settings.KeyConflictContext;
import org.lwjgl.glfw.GLFW;

public class ModKeyBindings {

    public static final String NATURERAIN_KEY_CATEGORY = "key.category.naturerain.naturerain";
    public static final String ENTER_SHELL_KEY = "key.naturerain.enter_shell";

    public static final KeyMapping ENTERING_SHELL_KEY = new KeyMapping(ENTER_SHELL_KEY, KeyConflictContext.IN_GAME,
            InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_H, NATURERAIN_KEY_CATEGORY);

}
