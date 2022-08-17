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
    public static <T> T unsafeRunForSide(Supplier<Runnable> clientTarget, Supplier<Runnable> serverTarget) {
        switch (FMLEnvironment.dist) {
            case CLIENT:
                clientTarget.get().run();
                serverTarget.get().run();
            case DEDICATED_SERVER:
                serverTarget.get().run();
            default:
                throw new IllegalArgumentException("UNSIDED?");
        }
    }

    public static <T> T unsafeGetForSide(LogicalSide side, Supplier<Supplier<T>> clientTarget, Supplier<Supplier<T>> serverTarget) {
        switch (side) {
            case CLIENT:
                return clientTarget.get().get();
            case SERVER:
                return serverTarget.get().get();
            default:
                throw new IllegalArgumentException("UNSIDED?");
        }
    }

    public static <T> T unsafeGetForSide(Entity entity, Supplier<Supplier<T>> clientTarget, Supplier<Supplier<T>> serverTarget) {
        switch (FMLEnvironment.dist) {
            case CLIENT:
                return entity.getCommandSenderWorld().isClientSide() ? clientTarget.get().get() : serverTarget.get().get();
            case DEDICATED_SERVER:
                return serverTarget.get().get();
            default:
                throw new IllegalArgumentException("UNSIDED?");
        }
    }

    public static <T> T unsafeGetForSide(Player entity, Supplier<Supplier<T>> clientTarget, Supplier<Supplier<T>> serverTarget) {
        switch (FMLEnvironment.dist) {
            case CLIENT:
                return !entity.isEffectiveAi() ? clientTarget.get().get() : serverTarget.get().get();
            case DEDICATED_SERVER:
                return serverTarget.get().get();
            default:
                throw new IllegalArgumentException("UNSIDED?");
        }
    }

    public static <T> T unsafeGetForSide(Level world, Supplier<Supplier<T>> clientTarget, Supplier<Supplier<T>> serverTarget) {
        switch (FMLEnvironment.dist) {
            case CLIENT:
                return world.isClientSide() ? clientTarget.get().get() : serverTarget.get().get();
            case DEDICATED_SERVER:
                return serverTarget.get().get();
            default:
                throw new IllegalArgumentException("UNSIDED?");
        }
    }
}
