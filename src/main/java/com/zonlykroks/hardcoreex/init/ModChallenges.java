package com.zonlykroks.hardcoreex.init;

import com.zonlykroks.hardcoreex.HardcoreExtended;
import com.zonlykroks.hardcoreex.challenge.*;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.RegistryBuilder;

import java.util.function.Supplier;

@SuppressWarnings("unused")
public class ModChallenges {
    public static final DeferredRegister<Challenge> CHALLENGES = DeferredRegister.create(Challenge.class, HardcoreExtended.MOD_ID);

    private static final Supplier<IForgeRegistry<Challenge>> registry;

    public static IForgeRegistry<Challenge> getRegistry() {
        return registry.get();
    }

    static {
        // Allow the registry to be registered.
        registry = CHALLENGES.makeRegistry("challenges", RegistryBuilder::new);
    }

    public static final RegistryObject<NoAttackingChallenge> NO_ATTACK = CHALLENGES.register("no_attack", NoAttackingChallenge::new);
    public static final RegistryObject<NoDamageChallenge> NO_DAMAGE = CHALLENGES.register("no_damage", NoDamageChallenge::new);
    public static final RegistryObject<AlwaysNightChallenge> NO_DAY = CHALLENGES.register("no_day", AlwaysNightChallenge::new);
    public static final RegistryObject<JumpParalysisChallenge> NO_JUMPING = CHALLENGES.register("no_jumping", JumpParalysisChallenge::new);
    public static final RegistryObject<NoBlockBreakingChallenge> NO_BLOCK_BREAKING = CHALLENGES.register("no_block_breaking", NoBlockBreakingChallenge::new);
    public static final RegistryObject<NoRegenerationChallenge> NO_HEALING = CHALLENGES.register("no_healing", NoRegenerationChallenge::new);
    public static final RegistryObject<NoSprintChallenge> NO_SPRINT = CHALLENGES.register("no_sprint", NoSprintChallenge::new);
    public static final RegistryObject<InsomniaChallenge> NO_SLEEP = CHALLENGES.register("no_sleep", InsomniaChallenge::new);
    public static final RegistryObject<WalkingParalysisChallenge> NO_WALKING = CHALLENGES.register("no_walking", WalkingParalysisChallenge::new);

    public static final RegistryObject<FishChallenge> ONLY_FISH = CHALLENGES.register("only_fish", FishChallenge::new);
    public static final RegistryObject<TNTRainChallenge> TNT_RAIN = CHALLENGES.register("tnt_rain", TNTRainChallenge::new);
    public static final RegistryObject<TakeDamageDealtChallenge> TAKE_DAMAGE_DEALT = CHALLENGES.register("take_damage_dealt", TakeDamageDealtChallenge::new);
    public static final RegistryObject<RandomBlockDropsChallenge> RANDOM_BLOCK_DROPS = CHALLENGES.register("random_block_drops", RandomBlockDropsChallenge::new);
    public static final RegistryObject<RandomEntityDropsChallenge> RANDOM_ENTITY_DROPS = CHALLENGES.register("random_entity_drops", RandomEntityDropsChallenge::new);
    public static final RegistryObject<ApocalypseChallenge> APOCALYPSE = CHALLENGES.register("apocalypse", ApocalypseChallenge::new);
    public static final RegistryObject<LightningStormChallenge> LIGHTNING_STORM = CHALLENGES.register("lightning_storm", LightningStormChallenge::new);
}
