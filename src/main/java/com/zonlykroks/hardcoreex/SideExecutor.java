package com.zonlykroks.hardcoreex;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;
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
                return entity.getEntityWorld().isRemote() ? clientTarget.get().get() : serverTarget.get().get();
            case DEDICATED_SERVER:
                return serverTarget.get().get();
            default:
                throw new IllegalArgumentException("UNSIDED?");
        }
    }

    public static <T> T unsafeGetForSide(PlayerEntity entity, Supplier<Supplier<T>> clientTarget, Supplier<Supplier<T>> serverTarget) {
        switch (FMLEnvironment.dist) {
            case CLIENT:
                return !entity.isServerWorld() ? clientTarget.get().get() : serverTarget.get().get();
            case DEDICATED_SERVER:
                return serverTarget.get().get();
            default:
                throw new IllegalArgumentException("UNSIDED?");
        }
    }

    public static <T> T unsafeGetForSide(World world, Supplier<Supplier<T>> clientTarget, Supplier<Supplier<T>> serverTarget) {
        switch (FMLEnvironment.dist) {
            case CLIENT:
                return world.isRemote() ? clientTarget.get().get() : serverTarget.get().get();
            case DEDICATED_SERVER:
                return serverTarget.get().get();
            default:
                throw new IllegalArgumentException("UNSIDED?");
        }
    }
}
