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

package net.fabricmc.fabric.test.screenhandler.client;

import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

import net.minecraft.client.gui.screen.ingame.Generic3x3ContainerScreen;
import net.minecraft.client.gui.screen.ingame.HandledScreens;

import net.fabricmc.fabric.test.screenhandler.ScreenHandlerTest;

public class ClientScreenHandlerTest {

	public static void onInitializeClient(FMLClientSetupEvent event) {
		HandledScreens.register(ScreenHandlerTest.BAG_SCREEN_HANDLER.get(), Generic3x3ContainerScreen::new);
		HandledScreens.register(ScreenHandlerTest.POSITIONED_BAG_SCREEN_HANDLER.get(), PositionedScreen::new);
		HandledScreens.register(ScreenHandlerTest.BOX_SCREEN_HANDLER.get(), PositionedScreen::new);
	}
}
