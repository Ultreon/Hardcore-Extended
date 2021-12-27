package com.zonlykroks.hardcoreex.challenge;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class NoBlockBreakingChallenge extends Challenge {

    public NoBlockBreakingChallenge() {
        super();
    }

    @Override
    protected void tick() {
    }

    /**
     * Event handler for block breaking.
     *
     * @param event the event that this method subscribes to.
     * @apiNote should not be invoked outside event bus.
     */
    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public void onBlockBreak(BlockEvent.BreakEvent event) {
        if (event.getPlayer() != null) {
            event.setCanceled(true);
        }
    }
}
