/*
 * WorldEdit, a Minecraft world manipulation toolkit
 * Copyright (C) sk89q <http://www.sk89q.com>
 * Copyright (C) WorldEdit team and contributors
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package com.mohistmc.worldeditfix;

import com.sk89q.worldedit.WorldEdit;
import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.extension.input.InputParseException;
import com.sk89q.worldedit.extension.input.ParserContext;
import com.sk89q.worldedit.registry.state.Property;
import com.sk89q.worldedit.world.block.BlockState;
import com.sk89q.worldedit.world.block.BlockType;
import com.sk89q.worldedit.world.block.FuzzyBlockState;
import com.sk89q.worldedit.world.item.ItemType;
import org.bukkit.Material;

import java.util.Map;

/**
 * Code to fix WorldEdit in mohist.
 */
public class WorldEditFix {

    public static void init() {
        for (Material material : Material.values()) {
            if (material.isForgeBlock()) {
                String name = material.getKeyForge().toString();

                if (material.isBlock()) {
                    BlockType.REGISTRY.register(name, new BlockType(name, blockState -> {
                        ParserContext context = new ParserContext();
                        context.setPreferringWildcard(true);
                        context.setTryLegacy(false);
                        context.setRestricted(false);
                        try {
                            FuzzyBlockState state = (FuzzyBlockState) WorldEdit.getInstance().getBlockFactory().parseFromInput(
                                    BukkitAdapter.adapt(blockState.getBlockType()).createBlockData().getAsString(), context
                            ).toImmutableState();
                            BlockState defaultState = blockState.getBlockType().getAllStates().get(0);
                            for (Map.Entry<Property<?>, Object> propertyObjectEntry : state.getStates().entrySet()) {
                                //noinspection unchecked
                                defaultState = defaultState.with((Property<Object>) propertyObjectEntry.getKey(), propertyObjectEntry.getValue());
                            }
                            return defaultState;
                        } catch (InputParseException e) {
                            return blockState;
                        }
                    }));
                } else if (material.isItem()) {
                    ItemType.REGISTRY.register(name, new ItemType(name));
                }
            }
        }
    }
}
