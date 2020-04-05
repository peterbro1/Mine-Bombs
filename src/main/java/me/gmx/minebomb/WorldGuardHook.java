package me.gmx.minebomb;

import com.sk89q.worldguard.LocalPlayer;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.flags.DefaultFlag;
import com.sk89q.worldguard.protection.flags.Flag;
import com.sk89q.worldguard.protection.flags.Flags;
import com.sk89q.worldguard.protection.flags.StateFlag;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

public class WorldGuardHook
{
    public static boolean canBreak(Player player,Block location) {
            try {
                WorldGuardPlugin worldGuard = WorldGuardPlugin.inst();
                LocalPlayer localPlayer = worldGuard.wrapPlayer(player);
                return(worldGuard.getRegionManager(location.getWorld()).getApplicableRegions(location.getLocation()).testState(localPlayer, DefaultFlag.BLOCK_BREAK));


                //return worldGuard.canBuild(player, location);
            } catch (Exception var3) {
                var3.printStackTrace();
            }


        return false;
    }

    public static boolean isInRegion(Block block, List<ProtectedRegion> regions) {
        if (block != null && !regions.isEmpty()) {
            try {
                WorldGuardPlugin worldGuard = WorldGuardPlugin.inst();
                RegionManager regionManager = worldGuard.getRegionManager(block.getWorld());
                if (regionManager != null)
                {
                    for (ProtectedRegion o : regions) {
                        ProtectedRegion region = regionManager.getRegion(o.getId());
                        if (region.contains(block.getX(),block.getY(),block.getZ()) && region != null){
                            return true;
                        }

                    }
                }
            } catch (Exception var7) {
                var7.printStackTrace();
            }
        }

        return false;
    }
}
