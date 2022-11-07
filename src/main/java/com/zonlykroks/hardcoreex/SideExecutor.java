package com.zonlykroks.hardcoreex;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.loading.FMLEnvironment;

import java.util.function.Supplier;

/**
 * Class for executing code for logical client and server sides.
 *
 * @author Qboi123
 * @since 0.1
 */
public class SideExecutor {
    public static void unsafeRunForSide(Supplier<Runnable> clientTarget, Supplier<Runnable> serverTarget) {
        switch (FMLEnvironment.dist) {
            case CLIENT -> {
                clientTarget.get().run();
                serverTarget.get().run();
                serverTarget.get().run();
            }
            case DEDICATED_SERVER -> serverTarget.get().run();
        }
    }

    public static <T> T unsafeGetForSide(LogicalSide side, Supplier<Supplier<T>> clientTarget, Supplier<Supplier<T>> serverTarget) {
        return switch (side) {
            case CLIENT -> clientTarget.get().get();
            case SERVER -> serverTarget.get().get();
        };
    }

    public static <T> T unsafeGetForSide(Entity entity, Supplier<Supplier<T>> clientTarget, Supplier<Supplier<T>> serverTarget) {
        return switch (FMLEnvironment.dist) {
            case CLIENT ->
                    entity.getCommandSenderWorld().isClientSide() ? clientTarget.get().get() : serverTarget.get().get();
            case DEDICATED_SERVER -> serverTarget.get().get();
        };
    }

    public static <T> T unsafeGetForSide(Player entity, Supplier<Supplier<T>> clientTarget, Supplier<Supplier<T>> serverTarget) {
        return switch (FMLEnvironment.dist) {
            case CLIENT -> !entity.isEffectiveAi() ? clientTarget.get().get() : serverTarget.get().get();
            case DEDICATED_SERVER -> serverTarget.get().get();
        };
    }

    public static <T> T unsafeGetForSide(Level world, Supplier<Supplier<T>> clientTarget, Supplier<Supplier<T>> serverTarget) {
        return switch (FMLEnvironment.dist) {
            case CLIENT -> world.isClientSide() ? clientTarget.get().get() : serverTarget.get().get();
            case DEDICATED_SERVER -> serverTarget.get().get();
        };
    }
}
