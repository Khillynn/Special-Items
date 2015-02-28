package com.khillynn;


import org.bukkit.*;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerEggThrowEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

public class special_Items extends JavaPlugin implements Listener {
    @Override
    public void onEnable() {
        Bukkit.getPluginManager().registerEvents(this, this);
        getLogger().info("Special-Items is Enabled! =D");
    }

    //different kinds of prank tnt
    @EventHandler
    public void onEntityExplode(EntityExplodeEvent e) {
        if (e.getEntityType() == EntityType.PRIMED_TNT) {
            Location loc = e.getLocation();


            if (loc.getBlock().getRelative(BlockFace.DOWN).getType().equals(Material.WOOL) && loc.getBlock().getRelative(BlockFace.DOWN).getData() == DyeColor.WHITE.getWoolData()) {
                e.blockList().clear();
                loc.getWorld().dropItem(loc, new ItemStack(Material.FEATHER, 64));
            }

            if (loc.getBlock().getRelative(BlockFace.DOWN).getType().equals(Material.WOOL) && loc.getBlock().getRelative(BlockFace.DOWN).getData() == DyeColor.RED.getWoolData()) {
                e.blockList().clear();
                loc.getWorld().dropItem(loc, new ItemStack(Material.ROTTEN_FLESH, 64));
            }

            if (loc.getBlock().getRelative(BlockFace.DOWN).getType().equals(Material.WOOL) && loc.getBlock().getRelative(BlockFace.DOWN).getData() == DyeColor.ORANGE.getWoolData()) {
                e.blockList().clear();
                for (int x = 1; x < 5; x++) {
                    for (int y = 1; y < 4; y++){
                        loc.getWorld().spawn(loc, Firework.class);
                    }
                    for (int y = 1; y < 4; y++){
                        loc.getWorld().spawn(loc, Firework.class);
                    }
                    for (int y = 1; y < 4; y++){
                        loc.getWorld().spawn(loc, Firework.class);
                    }
                }
            }
        }
    }

    //allows for a type of armor to keep the player from being burned by fire or lava, also allows a type of armor to act as longfall boots
    @EventHandler
    public void onPlayerInLava (EntityDamageEvent e){
        if (e.getEntity() instanceof Player) {
            Player player = (Player) e.getEntity();
            if(e.getCause().equals(EntityDamageEvent.DamageCause.LAVA) || e.getCause().equals(EntityDamageEvent.DamageCause.FIRE) || e.getCause().equals(EntityDamageEvent.DamageCause.FIRE_TICK)){
                if(player.getInventory().getLeggings().getType().equals(Material.IRON_LEGGINGS)) {
                    e.setCancelled(true);
                }
            }

            if(e.getCause().equals(EntityDamageEvent.DamageCause.FALL)){
                if(e.getEntity() instanceof Player){
                    if(player.getInventory().getBoots().getType().equals(Material.IRON_BOOTS)){
                        e.setCancelled(true);
                    }
                }
            }
        }
    }

    /* This will set a 3x3 area of water to ice when walked on. Seems best to run and jump across
    @EventHandler
    public void deathKnightBoots(final PlayerMoveEvent e){
        Player player = e.getPlayer();
        Location ground = e.getPlayer().getLocation().add(0.0D, -1.0D, 0.0D);
        Location ground1 = e.getPlayer().getLocation().add(0.0D, -1.0D, 0.0D);
        Location ground2 = e.getPlayer().getLocation().add(0.0D, -1.0D, 0.0D);
        Location ground3 = e.getPlayer().getLocation().add(0.0D, -1.0D, 0.0D);
        Location ground4 = e.getPlayer().getLocation().add(0.0D, -1.0D, 0.0D);
        Location ground5 = e.getPlayer().getLocation().add(0.0D, -1.0D, 0.0D);
        Location ground6 = e.getPlayer().getLocation().add(0.0D, -1.0D, 0.0D);
        Location ground7 = e.getPlayer().getLocation().add(0.0D, -1.0D, 0.0D);
        Location ground8 = e.getPlayer().getLocation().add(0.0D, -1.0D, 0.0D);
        if ((ground.getBlock().getType() == Material.WATER) || (ground.getBlock().getType() == Material.STATIONARY_WATER)) {
            Block b1 = ground.getBlock();
            Block bx1 = ground1.add(1.0D, 0.0D, 0.0D).getBlock();
            Block bx2 = ground2.add(-1.0D, 0.0D, 0.0D).getBlock();
            Block bx3 = ground3.add(1.0D, 0.0D, 1.0D).getBlock();
            Block bx4 = ground4.add(-1.0D, 0.0D, 1.0D).getBlock();
            Block bx5 = ground5.add(1.0D, 0.0D, -1.0D).getBlock();
            Block bx6 = ground6.add(-1.0D, 0.0D, -1.0D).getBlock();
            Block bx7 = ground7.add(0.0D, 0.0D, 1.0D).getBlock();
            Block bx8 = ground8.add(0.0D, 0.0D, -1.0D).getBlock();
            Block[] b = (Block[]) Arrays.asList(new Block[]{b1, bx1, bx2, bx3, bx4, bx5, bx6, bx7, bx8}).toArray();
            for (Block bl : b) {
                if ((bl.getType().equals(Material.WATER)) || (bl.getType().equals(Material.STATIONARY_WATER))) {
                    bl.setType(Material.PACKED_ICE);
                }
            }
        }
    }*/

    //these next two go together to create grenades from eggs
    @EventHandler
    public void onThrow (PlayerEggThrowEvent e){
        e.setHatching(false);
    }

    @EventHandler
    public void onHit (ProjectileHitEvent e){
        if (e.getEntity() instanceof Egg){
            Location loc = e.getEntity().getLocation();
            World world = e.getEntity().getWorld();
            world.playEffect(loc, Effect.STEP_SOUND, 50);
            world.createExplosion(loc.getBlockX(), loc.getBlockY(), loc.getBlockZ(), 3.0F, false, false);
        }
    }

}
