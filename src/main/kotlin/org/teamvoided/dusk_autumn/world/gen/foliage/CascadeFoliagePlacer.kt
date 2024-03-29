package org.teamvoided.dusk_autumn.world.gen.foliage

import com.mojang.serialization.Codec
import com.mojang.serialization.codecs.RecordCodecBuilder
import net.minecraft.util.math.int_provider.IntProvider
import net.minecraft.util.random.RandomGenerator
import net.minecraft.world.TestableWorld
import net.minecraft.world.gen.feature.TreeFeatureConfig
import net.minecraft.world.gen.foliage.FoliagePlacer
import net.minecraft.world.gen.foliage.FoliagePlacerType
import org.teamvoided.dusk_autumn.init.DuskWorldgen

class CascadeFoliagePlacer(intProvider: IntProvider?, intProvider2: IntProvider?) :
    FoliagePlacer(intProvider, intProvider2) {
    override fun getType(): FoliagePlacerType<*> {
        return DuskWorldgen.CASCADE_FOLIAGE_PLACER
    }

    override fun method_23448(
        world: TestableWorld,
        c_pwcqvmho: C_pwcqvmho,
        random: RandomGenerator,
        treeFeatureConfig: TreeFeatureConfig,
        i: Int,
        treeNode: TreeNode,
        j: Int,
        k: Int,
        l: Int
    ) {
        val blockPos = treeNode.center.up(l)
        val giantTrunk = treeNode.isGiantTrunk
        if (giantTrunk) {
            this.generateSquare(world, c_pwcqvmho, random, treeFeatureConfig, blockPos, k + 2, -1, giantTrunk)
            this.generateSquare(world, c_pwcqvmho, random, treeFeatureConfig, blockPos, k + 3, 0, giantTrunk)
            this.generateSquare(world, c_pwcqvmho, random, treeFeatureConfig, blockPos, k + 2, 1, giantTrunk)
            if (random.nextBoolean()) {
                this.generateSquare(world, c_pwcqvmho, random, treeFeatureConfig, blockPos, k, 2, giantTrunk)
            }
        } else {
            this.generateSquare(world, c_pwcqvmho, random, treeFeatureConfig, blockPos, k + 2, -1, giantTrunk)
            this.generateSquare(world, c_pwcqvmho, random, treeFeatureConfig, blockPos, k + 1, 0, giantTrunk)
        }
    }

    override fun getRandomHeight(random: RandomGenerator, trunkHeight: Int, config: TreeFeatureConfig): Int {
        return 4
    }

    override fun isPositionInvalid(
        random: RandomGenerator,
        dx: Int,
        y: Int,
        dz: Int,
        radius: Int,
        giantTrunk: Boolean
    ): Boolean {
        return if (y != 0 || !giantTrunk || dx != -radius && dx < radius || dz != -radius && dz < radius) super.isPositionInvalid(
            random,
            dx,
            y,
            dz,
            radius,
            giantTrunk
        ) else true
    }

    override fun isInvalidForLeaves(
        random: RandomGenerator,
        dx: Int,
        y: Int,
        dz: Int,
        radius: Int,
        giantTrunk: Boolean
    ): Boolean {
        return if (y == -1 && !giantTrunk) {
            dx == radius && dz == radius
        } else if (y == 1) {
            dx + dz > radius * 2 - 2
        } else {
            false
        }
    }

    companion object {
        val CODEC: Codec<CascadeFoliagePlacer> =
            RecordCodecBuilder.create { fillFoliagePlacerFields(it)
                .apply(it, ::CascadeFoliagePlacer) }
    }
}