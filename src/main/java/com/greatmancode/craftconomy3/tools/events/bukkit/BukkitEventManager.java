/*
 * This file is part of Craftconomy3.
 *
 * Copyright (c) 2011-2016, Greatman <http://github.com/greatman/>
 * Copyright (c) 2016-2017, Aztorius <http://github.com/Aztorius/>
 * Copyright (c) 2018-2019, Pavog <http://github.com/pavog/>
 *
 * Craftconomy3 is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Craftconomy3 is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with Craftconomy3. If not, see <http://www.gnu.org/licenses/>.
 */
package com.greatmancode.craftconomy3.tools.events.bukkit;

import com.greatmancode.craftconomy3.tools.caller.bukkit.BukkitPlayerCaller;
import com.greatmancode.craftconomy3.tools.events.interfaces.ServerEventManager;
import com.greatmancode.craftconomy3.tools.events.playerevent.PlayerJoinEvent;
import com.greatmancode.craftconomy3.tools.events.playerevent.PreJoinEvent;
import com.greatmancode.craftconomy3.tools.interfaces.BukkitLoader;
import com.greatmancode.craftconomy3.tools.interfaces.caller.ServerCaller;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;

import java.util.HashMap;
import java.util.Map;

public class BukkitEventManager implements ServerEventManager {
    private Map<String, Listener> map = new HashMap<>();

    public BukkitEventManager(BukkitPlayerCaller caller) {
        map.put(PlayerJoinEvent.class.getName(), new PlayerJoinEventListener(caller));
        map.put(PreJoinEvent.class.getName(), new PreJoinEventListener());
    }

    public void eventRegistered(String event, ServerCaller serverCaller) {
        if (map.containsKey(event)) {
            Bukkit.getServer().getPluginManager().registerEvents(map.get(event), ((BukkitLoader) serverCaller.getLoader()));
        }
    }
}
