package io.github.cgau3.debtinventory.events;

import io.github.cgau3.debtinventory.Config;
import io.github.cgau3.debtinventory.DebtInventory;
import io.github.cgau3.debtinventory.core.DeathPointManager;
import io.github.cgau3.debtinventory.core.DebtManager;
import io.github.cgau3.debtinventory.core.ModAttributeModifiers;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.ServerStatsCounter;
import net.minecraft.stats.Stats;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerSpawnPhantomsEvent;
import net.minecraftforge.event.entity.player.TradeWithVillagerEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = DebtInventory.MODID)
public class ServerPlayerEventListener {
    @SubscribeEvent
    public static void onPlayerClone(PlayerEvent.Clone event) {
        // restoreFrom本来就是ServerPlayer的方法
        // 不过这个可以sync一下
        Player oldPlayer = event.getOriginal();
        Player player = event.getEntity();
        player.giveExperienceLevels(-Config.expLevelLossDeath);
        if (event.isWasDeath()) {
            int x = DebtManager.countDebt(oldPlayer.getInventory());
            DebtManager.setDebt(player,
                DebtManager.getDebt(oldPlayer) + x
            );
            if (player instanceof ServerPlayer sp) {
                DebtManager.syncDebt(sp);
            }
        } else {
            DebtManager.setDebt(player, DebtManager.getDebt(oldPlayer));
        }
        DeathPointManager.cloneDeathPointInfo(oldPlayer, player);
    }

    @SubscribeEvent
    public static void onPlayerDeath(LivingDeathEvent event) {
        if (event.getEntity() instanceof Player player) {
            DeathPointManager.saveDeathPointInfo(player);
        }
    }

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        // 这个是双端事件
        Player player = event.player;
        if (player instanceof ServerPlayer sp
            && player.tickCount % 20 == 0
            && player.isAlive()
        ) {
            DeathPointManager.processPotentialDeathPointArrival(player);
            // 这个是每秒触发一次的定期同步
            DebtManager.syncDebt(sp);
        }
    }

    @SubscribeEvent
    public static void onPlayerLoad(PlayerEvent.PlayerLoggedInEvent event) {
        Player player = event.getEntity();
        // processPotentialModifiers是服务端才有效的
        ModAttributeModifiers.processPotentialModifiers(player, DebtManager.getDebt(player));
    }

    @SubscribeEvent
    public static void onTrySpawnPhantom(PlayerSpawnPhantomsEvent event) {
        if (!Config.enablePunishPhantom) return;
        Player player = event.getEntity();
        if (DebtManager.getDebt(player) > 0 && player instanceof ServerPlayer sp) {
            ServerStatsCounter serverstatscounter = sp.getStats();
            Level level = player.level();
            BlockPos blockpos = sp.blockPosition();
            if (
                level.dimensionType().hasSkyLight()
                && !(blockpos.getY() >= level.getSeaLevel() && level.canSeeSky(blockpos))
            ){
                return;
            }
            int j = Mth.clamp(serverstatscounter.getValue(Stats.CUSTOM.get(Stats.TIME_SINCE_REST)), 1, Integer.MAX_VALUE);
            if ( sp.getRandom().nextInt(j) >= 24000 ) {
                event.setResult(Event.Result.ALLOW);
            }
        }
    }

    @SubscribeEvent
    public static void onTradeWithVillager(TradeWithVillagerEvent event) {
        // 这个似乎是双端事件
        Player player = event.getEntity();
        if (DebtManager.getDebt(player) > 0) {
            DebtManager.addDebt(player, -Config.deductionVillagerTrade);
        }
    }
}
