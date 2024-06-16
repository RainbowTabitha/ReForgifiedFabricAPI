/*
 * Copyright (c) 2016, 2017, 2018, 2019 FabricMC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package net.fabricmc.fabric.impl.client.keybinding;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import it.unimi.dsi.fastutil.objects.ReferenceArrayList;
import net.fabricmc.fabric.mixin.client.keybinding.KeyBindingAccessor;
import net.minecraft.client.KeyMapping;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;

public final class KeyBindingRegistryImpl {
	private static final List<KeyMapping> MODDED_KEY_BINDINGS = new ReferenceArrayList<>(); // ArrayList with identity based comparisons for contains/remove/indexOf etc., required for correctly handling duplicate keybinds
	private static boolean processed;

	private KeyBindingRegistryImpl() {
	}

	private static Map<String, Integer> getCategoryMap() {
		return KeyBindingAccessor.fabric_getCategoryMap();
	}

	public static boolean addCategory(String categoryTranslationKey) {
		Map<String, Integer> map = getCategoryMap();

		if (map.containsKey(categoryTranslationKey)) {
			return false;
		}

		Optional<Integer> largest = map.values().stream().max(Integer::compareTo);
		int largestInt = largest.orElse(0);
		map.put(categoryTranslationKey, largestInt + 1);
		return true;
	}

	public static KeyMapping registerKeyBinding(KeyMapping binding) {
		if (processed) {
			throw new IllegalStateException("Key bindings have already been processed");
		}

		for (KeyMapping existingKeyBindings : MODDED_KEY_BINDINGS) {
			if (existingKeyBindings == binding) {
				throw new IllegalArgumentException("Attempted to register a key binding twice: " + binding.getName());
			} else if (existingKeyBindings.getName().equals(binding.getName())) {
				throw new IllegalArgumentException("Attempted to register two key bindings with equal ID: " + binding.getName() + "!");
			}
		}

		// This will do nothing if the category already exists.
		addCategory(binding.getCategory());
		MODDED_KEY_BINDINGS.add(binding);
		return binding;
	}

	public static void process(RegisterKeyMappingsEvent event) {
		MODDED_KEY_BINDINGS.forEach(event::register);
		processed = true;
	}
}
