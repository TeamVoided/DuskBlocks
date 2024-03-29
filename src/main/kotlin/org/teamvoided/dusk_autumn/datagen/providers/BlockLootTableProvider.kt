package org.teamvoided.dusk_autumn.datagen.providers

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider
import net.minecraft.block.Block
import net.minecraft.block.Blocks
import net.minecraft.data.server.loot_table.VanillaBlockLootTableGenerator.JUNGLE_SAPLING_DROP_CHANCES
import net.minecraft.item.Items
import net.minecraft.loot.LootPool
import net.minecraft.loot.LootTable
import net.minecraft.loot.condition.BlockStatePropertyLootCondition
import net.minecraft.loot.entry.AlternativeEntry
import net.minecraft.loot.entry.ItemEntry
import net.minecraft.loot.entry.LootTableEntry
import net.minecraft.loot.function.SetCountLootFunction
import net.minecraft.loot.provider.number.ConstantLootNumberProvider
import net.minecraft.loot.provider.number.UniformLootNumberProvider
import net.minecraft.predicate.StatePredicate
import org.teamvoided.dusk_autumn.block.LeafPileBlock
import org.teamvoided.dusk_autumn.init.DuskBlocks

class BlockLootTableProvider(o: FabricDataOutput) : FabricBlockLootTableProvider(o) {


    val dropsItSelf = listOf(
        DuskBlocks.CASCADE_SAPLING,
        DuskBlocks.CASCADE_LOG,
        DuskBlocks.STRIPPED_CASCADE_LOG,
        DuskBlocks.CASCADE_PLANKS,
        DuskBlocks.CASCADE_TRAPDOOR,
        DuskBlocks.GOLDEN_BIRCH_SAPLING
    )

    override fun generate() {
        dropsItSelf.forEach(::addDrop)

        add(DuskBlocks.CASCADE_DOOR, ::doorDrops)
        add(DuskBlocks.BLUE_DOOR, ::doorDrops)

        add(DuskBlocks.CASCADE_LEAVES) { leavesDrops(it, DuskBlocks.CASCADE_SAPLING, *JUNGLE_SAPLING_DROP_CHANCES) }
        add(DuskBlocks.GOLDEN_BIRCH_LEAVES) {
            oakLeavesDrops(
                it,
                DuskBlocks.GOLDEN_BIRCH_SAPLING,
                *LEAVES_SAPLING_DROP_CHANCES
            )
        }

        add(DuskBlocks.OAK_LEAF_PILE) { leafPile(it, Blocks.OAK_LEAVES) }
        add(DuskBlocks.SPRUCE_LEAF_PILE) { leafPile(it, Blocks.SPRUCE_LEAVES) }
        add(DuskBlocks.BIRCH_LEAF_PILE) { leafPile(it, Blocks.BIRCH_LEAVES) }
        add(DuskBlocks.JUNGLE_LEAF_PILE) { leafPile(it, Blocks.JUNGLE_LEAVES) }
        add(DuskBlocks.ACACIA_LEAF_PILE) { leafPile(it, Blocks.ACACIA_LEAVES) }
        add(DuskBlocks.DARK_OAK_LEAF_PILE) { leafPile(it, Blocks.DARK_OAK_LEAVES) }
        add(DuskBlocks.MANGROVE_LEAF_PILE) { leafPile(it, Blocks.MANGROVE_LEAVES) }
        add(DuskBlocks.AZALEA_LEAF_PILE) { leafPile(it, Blocks.AZALEA_LEAVES) }
        add(DuskBlocks.FLOWERING_AZALEA_LEAF_PILE) { leafPile(it, Blocks.FLOWERING_AZALEA_LEAVES) }
        add(DuskBlocks.CHERRY_LEAF_PILE) { leafPile(it, Blocks.CHERRY_LEAVES) }
        add(DuskBlocks.CASCADE_LEAF_PILE) { leafPile(it, DuskBlocks.CASCADE_LEAVES) }
        add(DuskBlocks.GOLDEN_BIRCH_LEAF_PILE) { leafPile(it, DuskBlocks.GOLDEN_BIRCH_LEAVES) }

        add(DuskBlocks.BLUE_PETALS, ::method_49358)
        add(DuskBlocks.POTTED_CASCADE_SAPLING) { pottedPlantDrops(DuskBlocks.CASCADE_SAPLING) }
        add(DuskBlocks.POTTED_GOLDEN_BIRCH_SAPLING) { pottedPlantDrops(DuskBlocks.GOLDEN_BIRCH_SAPLING) }
        add(DuskBlocks.POTTED_VIOLET_DAISY, ::pottedPlantDrops)
    }

    private fun constantLootNumber(i: Number): ConstantLootNumberProvider =
        ConstantLootNumberProvider.create(i.toFloat())

    fun leafPile(pile: Block, leaves: Block): LootTable.Builder {
        return LootTable.builder().pool(
            LootPool.builder().with(
                AlternativeEntry.builder(
                    AlternativeEntry.builder(LeafPileBlock.PILE_LAYERS.values) { layers ->
                        if (layers == 4) LootTableEntry.builder(leaves.lootTableId)
                        else
                            applyExplosionDecay(
                                leaves,
                                ItemEntry.builder(Items.STICK)
                                    .apply(
                                        SetCountLootFunction.builder(
                                            UniformLootNumberProvider.create(
                                                0.0f,
                                                layers.toFloat() * (2.0f / 3.0f)
                                            )
                                        )
                                    )
                            ).conditionally(
                                BlockStatePropertyLootCondition.builder(pile).properties(
                                    StatePredicate.Builder.create().exactMatch(LeafPileBlock.PILE_LAYERS, layers)
                                )
                            )
                    },
                    AlternativeEntry.builder(LeafPileBlock.PILE_LAYERS.values) { layers ->
                        if (layers == 4) LootTableEntry.builder(leaves.lootTableId)
                        else
                            ItemEntry.builder(pile)
                                .apply(SetCountLootFunction.builder(constantLootNumber(layers)))
                                .conditionally(
                                    BlockStatePropertyLootCondition.builder(pile).properties(
                                        StatePredicate.Builder.create().exactMatch(LeafPileBlock.PILE_LAYERS, layers)
                                    )
                                )
                    }.conditionally(WITH_SHEARS_OR_SILK_TOUCH)
                )
            )
        )
    }
}