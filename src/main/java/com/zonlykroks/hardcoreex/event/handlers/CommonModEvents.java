package com.zonlykroks.hardcoreex.event.handlers;

import com.zonlykroks.hardcoreex.HardcoreExtended;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(value = {Dist.CLIENT, Dist.DEDICATED_SERVER}, bus = Mod.EventBusSubscriber.Bus.MOD, modid = HardcoreExtended.MOD_ID)
public class CommonModEvents {
}
