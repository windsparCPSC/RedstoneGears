package com.windspar.redstonegears;

import java.util.stream.Collectors;

import com.windspar.redstonegears.lists.BlockList;
import com.windspar.redstonegears.lists.ItemList;
import com.windspar.redstonegears.objects.blocks.RedstoneGear;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.InterModComms;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

// The value here should match an entry in the META-INF/mods.toml file
@Mod("redstonegears")
public class RedstoneGears
{
	public static RedstoneGears instance;
	public static final String modid = "redstonegears";
	public static final ItemGroup REDSTONE_GEARS = new RedstoneGearGroup("Redstone Gear Set");
	//private static final Logger LOGGER = LogManager.getLogManager().getLogger(modid);

    public RedstoneGears() {
        // Register the setup method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        // Register the enqueueIMC method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::enqueueIMC);
        // Register the processIMC method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::processIMC);
        // Register the doClientStuff method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::doClientStuff);

        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);
    }

    private void setup(final FMLCommonSetupEvent event)
    {
        // some preinit code
        System.out.println("HELLO FROM PREINIT");
        System.out.println("DIRT BLOCK >> {" + Blocks.DIRT.getRegistryName() + "}");
    }

    private void doClientStuff(final FMLClientSetupEvent event) {
        // do something that can only be done on the client
        System.out.println("Got game settings {" + event.getMinecraftSupplier().get().gameSettings + "}");
    }

    private void enqueueIMC(final InterModEnqueueEvent event)
    {
        // some example code to dispatch IMC to another mod
        InterModComms.sendTo("examplemod", "helloworld", () -> { System.out.println("Hello world from the MDK"); return "Hello world";});
    }

    private void processIMC(final InterModProcessEvent event)
    {
        // some example code to receive and process InterModComms from other mods
        System.out.println("Got IMC {" + event.getIMCStream().
                map(m->m.getMessageSupplier().get()).
                collect(Collectors.toList()) + "}");
    }
    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(FMLServerStartingEvent event) {
        // do something when the server starts
        System.out.println("HELLO from server starting");
    }

    // You can use EventBusSubscriber to automatically subscribe events on the contained class (this is subscribing to the MOD
    // Event bus for receiving Registry Events)
    @Mod.EventBusSubscriber(bus=Mod.EventBusSubscriber.Bus.MOD)
    public static class RegistryEvents {
    	//=======================================================================================
		
    			@SubscribeEvent
    			public static void registerItems(final RegistryEvent.Register<Item> event) {
    				
    				event.getRegistry().registerAll
    				(	
    					ItemList.redstone_gear = new BlockItem(BlockList.redstone_gear, new Item.Properties().group(REDSTONE_GEARS))
    						.setRegistryName(BlockList.redstone_gear.getRegistryName())
    				);

    				System.out.println("Items registered.");
    			}
    			
    			//=======================================================================================
    			
    			@SubscribeEvent
    			public static void registerBlocks(final RegistryEvent.Register<Block> event) {
    				
    				event.getRegistry().registerAll
    				(
    						BlockList.redstone_gear = new RedstoneGear
    						(
    							Block.Properties.create(Material.ROCK).hardnessAndResistance(3.0f, 15.0f).sound(SoundType.STONE).harvestLevel(0)
    						).setRegistryName(location("redstone_gear"))
    				);
    				
    				System.out.println("Blocks registered.");
    			}
    }
    
    public static ResourceLocation location(String name) {
		return new ResourceLocation(modid, name);
	}	
}
