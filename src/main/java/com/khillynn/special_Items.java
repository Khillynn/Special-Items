package com.khillynn;


import org.bukkit.*;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
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


            //if there is white wool under TNT then 5 stacks of FEATHERS will be dropped upon detonation
            if (loc.getBlock().getRelative(BlockFace.DOWN).getType().equals(Material.WOOL) && loc.getBlock().getRelative(BlockFace.DOWN).getData() == DyeColor.WHITE.getWoolData()) {
                e.setCancelled(true);
                e.getLocation().getWorld().createExplosion(loc, 0.0F);
                for(int stackNum = 1; stackNum <= 5; stackNum++)
                    loc.getWorld().dropItem(loc, new ItemStack(Material.FEATHER, 64));
            }

            //if there is red wool under TNT then 5 stacks of ROTTEN FLESH will be dropped upon detonation
            if (loc.getBlock().getRelative(BlockFace.DOWN).getType().equals(Material.WOOL) && loc.getBlock().getRelative(BlockFace.DOWN).getData() == DyeColor.RED.getWoolData()) {
                e.setCancelled(true);
                e.getLocation().getWorld().createExplosion(loc, 0.0F);
                for(int stackNum = 1; stackNum <= 5; stackNum++)
                    loc.getWorld().dropItem(loc, new ItemStack(Material.ROTTEN_FLESH, 64));
            }

            //if there is orange wool under TNT then a ton of FIREWORKS will be spawned upon detonation
            if (loc.getBlock().getRelative(BlockFace.DOWN).getType().equals(Material.WOOL) && loc.getBlock().getRelative(BlockFace.DOWN).getData() == DyeColor.ORANGE.getWoolData()) {
                e.setCancelled(true);
                e.getLocation().getWorld().createExplosion(loc, 0.0F);
                //only 15 can be rendered by the client so..
                for (int x = 1; x <= 5; x++) {
                    for (int y = 1; y < 5; y++){
                        loc.getWorld().spawn(loc, Firework.class);
                    }
                    for (int y = 1; y < 5; y++){
                        loc.getWorld().spawn(loc, Firework.class);
                    }
                    for (int y = 1; y < 5; y++){
                        loc.getWorld().spawn(loc, Firework.class);
                    }
                }
            }

            //if there is green wool under TNT then 5 CREEPERS will be spawned upon detonation
            if (loc.getBlock().getRelative(BlockFace.DOWN).getType().equals(Material.WOOL) && loc.getBlock().getRelative(BlockFace.DOWN).getData() == DyeColor.GREEN.getWoolData()) {
                e.setCancelled(true);
                e.getLocation().getWorld().createExplosion(loc, 0.0F);
                for(int creeperNum = 1; creeperNum <= 5; creeperNum++)
                    loc.getWorld().spawnEntity(loc, EntityType.CREEPER);
            }
        }
    }

    //this prevents dropped items from being destroyed by TNT
    @EventHandler
    public void onDroppedItemDamagedByTNT (EntityDamageByEntityEvent e) {
        if(e.getEntityType().equals(EntityType.DROPPED_ITEM) && e.getDamager() instanceof TNTPrimed)
            e.setCancelled(true);
    }

    //allows for a type of armor (IRON in this case) to keep the player from being burned by fire or lava, also allows a type of armor to act as longfall boots
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

    //prevents damage to tamed wolves and ocelots
    @EventHandler
    public void onPetDamage (EntityDamageEvent e){
        if((e.getEntity() instanceof Wolf) && ((Wolf) e.getEntity()).isTamed())
            e.setCancelled(true);
        if(e.getEntity() instanceof Ocelot && ((Ocelot) e.getEntity()).isTamed())
            e.setCancelled(true);
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
            world.playEffect(loc, Effect.STEP_SOUND, 100);
            world.createExplosion(loc.getBlockX(), loc.getBlockY(), loc.getBlockZ(), 3.0F, false, false);
        }
    }

}
