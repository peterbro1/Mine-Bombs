package me.gmx.minebomb.listener;


import me.gmx.minebomb.Minebomb;
import me.gmx.minebomb.WorldGuardHook;
import me.gmx.minebomb.config.Lang;
import me.gmx.minebomb.config.Settings;
import me.gmx.minebomb.handler.MinebombHandler;
import me.gmx.minebomb.item.MinebombItemThrown;
import me.gmx.minebomb.util.ItemMetadata;
import net.lightshard.prisonmines.MineAPI;
import net.lightshard.prisonmines.PrisonMines;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftItem;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_12_R1.inventory.CraftItemStack;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.hanging.HangingBreakEvent;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryInteractEvent;
import org.bukkit.event.player.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.MaterialData;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.RegisteredListener;
import org.bukkit.projectiles.ProjectileSource;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class PlayerListener implements Listener {
    private Minebomb ins;
    public PlayerListener(Minebomb ins){
        this.ins = ins;
    }


    @EventHandler
    public void throwBomb(PlayerInteractEvent e){
        if (e.getAction() != Action.RIGHT_CLICK_BLOCK && e.getAction() != Action.RIGHT_CLICK_AIR){
            return;
        }
        if (e.getPlayer().getInventory().getItemInMainHand().getType().equals(Material.MAGMA_CREAM)){
            if (ItemMetadata.hasNBTDataString(e.getPlayer().getInventory().getItemInMainHand(),"minebomb")){
                ItemStack stack = e.getPlayer().getInventory().getItemInMainHand();
                Player player = e.getPlayer();
                if (MinebombHandler.count(player) >= Settings.MAX_BOMBS.getNumber()){
                    player.sendMessage(Lang.PREFIX + "You can only have " + Settings.MAX_BOMBS.getString() + " bombs out at a time!");
                    return;
                }
                Item thrown = player.getWorld().dropItem(player.getLocation(), stack.clone().asOne());
                thrown.setVelocity(player.getLocation().getDirection());
                thrown.setCustomName(Lang.MINEBOMB_PROJECTILE_NAME.toString());
                thrown.setCustomNameVisible(true);
                thrown.setMetadata("owner",new FixedMetadataValue(Minebomb.getInstance(),player.getName()));
                thrown.setPickupDelay(Integer.MAX_VALUE);
                MinebombHandler.add(thrown,player);
                int radius = Integer.parseInt(ItemMetadata.getNBTDataString(CraftItemStack.asNMSCopy(stack),"minebomb"));
                if (stack.getAmount() > 1){
                    stack.setAmount(stack.getAmount()-1);
                }else if (stack.getAmount() == 1){
                    player.getInventory().removeItem(stack);
                }
                new BukkitRunnable(){
                    public void run(){
                        if (!player.isOnline()){
                            thrown.remove();
                            return;
                        }
                        final Block block = thrown.getLocation().getBlock(); //placed block
                        block.getWorld().createExplosion(thrown,radius*6,false,true);
                        //multiply *6 so that five is == 30 (good size)
                        thrown.remove();
                        MinebombHandler.remove(thrown);

                    }
                }.runTaskLater(Minebomb.getInstance(), Settings.EXPLODE_DELAY.getNumber()*20);
            }
        }

    }

    @EventHandler
    public void logoff(PlayerQuitEvent e){
        MinebombHandler.removeFromPlayer(e.getPlayer());
    }

    @EventHandler(priority = EventPriority.HIGHEST,ignoreCancelled = false)
    public void explode(EntityExplodeEvent e) {
        if (e.getEntityType() == EntityType.DROPPED_ITEM) {
            //Bukkit.broadcastMessage(((Item) e).getItemStack().getType().toString());
            //if (MinebombHandler.isBomb(e.getEntity().getUniqueId()))
            if (((Item) e.getEntity()).getItemStack().getType() == Material.MAGMA_CREAM) {
                if (!((Item) e.getEntity()).hasMetadata("owner")){
                    return;
                }
                e.setCancelled(true);
                e.setYield(0);
                Player p = null;

                   if ((p = Bukkit.getPlayer(((Item)e.getEntity()).getMetadata("owner").get(0).asString())) != null){
                       Player finalP = p;
                       if (!p.isOnline()){
                           return;
                       }
                       p.sendMessage(Lang.MINEBOMB_EXPL_MESSAGE.toMsg());
                       if (Settings.PARTICLE_EFFECT.getBoolean())
                                   e.getEntity().getWorld().spawnParticle(Particle.EXPLOSION_LARGE,e.getEntity().getLocation(),1);

                     /*  new BukkitRunnable(){
                           public void run(){*/

                               e.blockList().removeIf(block -> !WorldGuardHook
                                       .canBreak(finalP, block));
                            /*   new BukkitRunnable(){
                                   public void run(){*/

                                    for (Block b : e.blockList()){


                                                b.getDrops().forEach(item ->
                                                        new BukkitRunnable(){
                                                    public void run(){
                                                        finalP.getInventory().addItem(item);
                                                        finalP.getInventory().addItem(item);
                                                        finalP.getInventory().addItem(item);

                                                    }
                                        }.runTaskLater(Minebomb.getInstance(),1));
                                        b.setType(Material.AIR,true);

                                    }
                                    if (Bukkit.getPluginManager().getPlugin("PrisonMines") != null)
                                        PrisonMines.getAPI().onBlockBreak(PrisonMines.getAPI().getByLocation(e.getEntity().getLocation()),e.blockList().size());


                             /*      }
                               }.runTask(Minebomb.getInstance());*/
                          /* }
                       }.runTaskAsynchronously(Minebomb.getInstance());*/
                    }



            }
        }

        }

        @EventHandler
    public void breakFrame(HangingBreakEvent e){
        if (e.getCause().equals(HangingBreakEvent.RemoveCause.EXPLOSION)){
            e.setCancelled(true);
        }
    }



    }




