package io.github.cgau3.debtinventory;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.config.ModConfigEvent;

@Mod.EventBusSubscriber(modid = DebtInventory.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class Config {
    private static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();

    private static final ForgeConfigSpec.IntValue EXP_LEVEL_LOSS_DEATH = BUILDER
        .comment("Exp levels lost on death")
        .defineInRange("expLevelLossDeath", 7, 0, Integer.MAX_VALUE);

    private static final ForgeConfigSpec.IntValue DEBT_STACK64 = BUILDER
        .comment("Debt added for each >=64-stack item")
        .defineInRange("debtStack64", 1, 0, Integer.MAX_VALUE);

    private static final ForgeConfigSpec.IntValue DEBT_STACK16 = BUILDER
        .comment("Debt added for each >=16-stack item")
        .defineInRange("debtStack16", 3, 0, Integer.MAX_VALUE);

    private static final ForgeConfigSpec.IntValue DEBT_STACK1 = BUILDER
        .comment("Debt added for each 1-stack item")
        .defineInRange("debtStack1", 24, 0, Integer.MAX_VALUE);

    private static final ForgeConfigSpec.IntValue DEBT_STACK_OTHER = BUILDER
        .comment("Debt added for each other-stack item")
        .defineInRange("debtStackOther", 8, 0, Integer.MAX_VALUE);

    private static final ForgeConfigSpec.DoubleValue DEDUCTION_DEATH_POINT = BUILDER
        .comment("Debt deducted when the player reaches last death point, in proportion of debt caused by that death")
        .defineInRange("deductionDeathPoint", 0.75, 0, 1.0);

    private static final ForgeConfigSpec.IntValue DEDUCTION_CHEST_LOOT = BUILDER
        .comment("Debt deducted when the player generates chest loot")
        .defineInRange("deductionChestLoot", 96, 0, Integer.MAX_VALUE);

    private static final ForgeConfigSpec.IntValue DEDUCTION_ARCHEOLOGY_LOOT = BUILDER
        .comment("Debt deducted when the player generates archeology loot")
        .defineInRange("deductionArcheologyLoot", 32, 0, Integer.MAX_VALUE);

    private static final ForgeConfigSpec.IntValue DEDUCTION_FISH_STACK64 = BUILDER
        .comment("Debt deducted for each >=64-stack item fished up")
        .defineInRange("deductionFish64", 10, 0, Integer.MAX_VALUE);

    private static final ForgeConfigSpec.IntValue DEDUCTION_FISH_STACK16 = BUILDER
        .comment("Debt deducted for each >=16-stack item fished up")
        .defineInRange("deductionFish16", 20, 0, Integer.MAX_VALUE);

    private static final ForgeConfigSpec.IntValue DEDUCTION_FISH_STACK1 = BUILDER
        .comment("Debt deducted for each 1-stack item fished up")
        .defineInRange("deductionFish1", 56, 0, Integer.MAX_VALUE);

    private static final ForgeConfigSpec.IntValue DEDUCTION_FISH_STACK_OTHER = BUILDER
        .comment("Debt deducted for each other-stack item fished up")
        .defineInRange("deductionFishOther", 36, 0, Integer.MAX_VALUE);

    private static final ForgeConfigSpec.DoubleValue DEDUCTION_MOB_HEALTH = BUILDER
        .comment("Debt deducted when killing a mob, in proportion to the max health of the mob")
        .defineInRange("deductionMobHealth", 0.50, 0, 100);

    private static final ForgeConfigSpec.IntValue DEDUCTION_MOB_MAX = BUILDER
        .comment("Max debt deducted when killing a mob")
        .defineInRange("deductionMobMax", 512, 0, Integer.MAX_VALUE);

    private static final ForgeConfigSpec.IntValue DEDUCTION_PHANTOM = BUILDER
        .comment("Extra debt deducted when killing a phantom")
        .defineInRange("deductionPhantom", 16, 0, Integer.MAX_VALUE);

    private static final ForgeConfigSpec.IntValue DEDUCTION_VILLAGER_TRADE = BUILDER
        .comment("Debt deducted when trading with a villager")
        .defineInRange("deductionVillager", 16, 0, Integer.MAX_VALUE);

    private static final ForgeConfigSpec.DoubleValue DEDUCTION_BUFF_COMPASS = BUILDER
        .comment("Debt deduction buff when holding the recovery compass in proportions")
        .defineInRange("deductionBuffCompass", 0.25, 0, 100);

    private static final ForgeConfigSpec.BooleanValue ENABLE_PUNISH_LUCK = BUILDER
        .comment("Whether debt punishment: luck is enabled")
        .define("enablePunishLuck", true);

    private static final ForgeConfigSpec.BooleanValue ENABLE_PUNISH_LOOTING = BUILDER
        .comment("Whether debt punishment: looting enchantment is enabled")
        .define("enablePunishLooting", true);

    private static final ForgeConfigSpec.BooleanValue ENABLE_PUNISH_PHANTOM = BUILDER
        .comment("Whether debt punishment: insomnia is enabled")
        .define("enablePunishPhantom", true);

    private static final ForgeConfigSpec.DoubleValue PUNISH_MOB_EXP = BUILDER
        .comment("The amount of exp reduced by debt punishment: mob exp in proportions")
        .defineInRange("punishMobExp", 0.10, 0, 1.0);

    static final ForgeConfigSpec SPEC = BUILDER.build();

    public static int expLevelLossDeath;
    public static int debtStack64;
    public static int debtStack16;
    public static int debtStack1;
    public static int debtStackOther;
    public static float deductionDeathPoint;
    public static int deductionChestLoot;
    public static int deductionArcheologyLoot;
    public static int deductionFish64;
    public static int deductionFish16;
    public static int deductionFish1;
    public static int deductionFishOther;
    public static float deductionMobHealth;
    public static int deductionMobMax;
    public static int deductionPhantom;
    public static int deductionVillagerTrade;
    public static float deductionBuffCompass;
    public static boolean enablePunishLuck;
    public static boolean enablePunishLooting;
    public static boolean enablePunishPhantom;
    public static float punishMobExp;

    private static void reloadConfigCaches() {
        expLevelLossDeath = EXP_LEVEL_LOSS_DEATH.get();
        debtStack64 = DEBT_STACK64.get();
        debtStack16 = DEBT_STACK16.get();
        debtStack1 = DEBT_STACK1.get();
        debtStackOther = DEBT_STACK_OTHER.get();
        deductionDeathPoint = DEDUCTION_DEATH_POINT.get().floatValue();
        deductionChestLoot = DEDUCTION_CHEST_LOOT.get();
        deductionArcheologyLoot = DEDUCTION_ARCHEOLOGY_LOOT.get();
        deductionFish64 = DEDUCTION_FISH_STACK64.get();
        deductionFish16 = DEDUCTION_FISH_STACK16.get();
        deductionFish1 = DEDUCTION_FISH_STACK1.get();
        deductionFishOther = DEDUCTION_FISH_STACK_OTHER.get();
        deductionMobHealth = DEDUCTION_MOB_HEALTH.get().floatValue();
        deductionMobMax = DEDUCTION_MOB_MAX.get();
        deductionPhantom = DEDUCTION_PHANTOM.get();
        deductionVillagerTrade = DEDUCTION_VILLAGER_TRADE.get();
        deductionBuffCompass = DEDUCTION_BUFF_COMPASS.get().floatValue();
        enablePunishLuck = ENABLE_PUNISH_LUCK.get();
        enablePunishLooting = ENABLE_PUNISH_LOOTING.get();
        enablePunishPhantom = ENABLE_PUNISH_PHANTOM.get();
        punishMobExp = PUNISH_MOB_EXP.get().floatValue();
    }

    @SubscribeEvent
    public static void onReloadConfigs(ModConfigEvent.Reloading event) {
        reloadConfigCaches();
    }

    @SubscribeEvent
    public static void onLoadConfig(ModConfigEvent.Loading event) {
        reloadConfigCaches();
    }
}
