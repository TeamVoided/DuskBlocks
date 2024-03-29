package org.teamvoided.dusk_autumn.datagen

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput
import net.fabricmc.fabric.api.datagen.v1.provider.FabricDynamicRegistryProvider
import net.minecraft.registry.HolderLookup
import net.minecraft.registry.RegistryKeys
import java.util.concurrent.CompletableFuture

class DuskAutumnsWorldGenerator(o: FabricDataOutput, r: CompletableFuture<HolderLookup.Provider>) :
    FabricDynamicRegistryProvider(o, r) {

    override fun getName(): String = "Data Gen"

    override fun configure(reg: HolderLookup.Provider, e: Entries) {
        e.addAll(reg.getLookupOrThrow(RegistryKeys.BIOME))
        e.addAll(reg.getLookupOrThrow(RegistryKeys.PLACED_FEATURE))
        e.addAll(reg.getLookupOrThrow(RegistryKeys.CONFIGURED_FEATURE))
    }
}