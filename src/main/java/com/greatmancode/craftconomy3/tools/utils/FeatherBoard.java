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
package com.greatmancode.craftconomy3.tools.utils;

import be.maximvdw.placeholderapi.PlaceholderAPI;
import com.greatmancode.craftconomy3.tools.interfaces.BukkitLoader;
import com.greatmancode.craftconomy3.tools.interfaces.Loader;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class FeatherBoard {
    public static void registerPlaceHolder(Loader loader, String name, final FeatherBoardReplaceEvent event) {
        if (loader instanceof BukkitLoader) {
            if (Bukkit.getPluginManager().isPluginEnabled("MVdWPlaceholderAPI")) {
                PlaceholderAPI.registerPlaceholder((Plugin) loader, name, placeholderReplaceEvent -> event.getValue(placeholderReplaceEvent.getOfflinePlayer().getName(), placeholderReplaceEvent.isOnline()));
            }
        }
    }


    public static abstract class FeatherBoardReplaceEvent {
        private Map<String, UserData> cache = new HashMap<>();

        public String getValue(String username, boolean isOnline) {
            if (cache.containsKey(username)) {
                UserData data = cache.get(username);
                if (new Date().getTime() <= (data.getDate().getTime() + 60000)) { //1 minute cache
                    return data.getValue();
                } else {
                    String result = getResult(username, isOnline);
                    cache.put(username, new UserData(new Date(), result));
                    return getResult(username, isOnline);
                }
            } else {
                String result = getResult(username, isOnline);
                cache.put(username, new UserData(new Date(), result));
                return getResult(username, isOnline);
            }
        }

        public abstract String getResult(String username, boolean isOnline);
    }


    private static class UserData {
        private Date date;
        private String value;

        public UserData(final Date date, final String value) {
            this.date = date;
            this.value = value;
        }

        public Date getDate() {
            return this.date;
        }

        public String getValue() {
            return this.value;
        }

        public void setDate(final Date date) {
            this.date = date;
        }

        public void setValue(final String value) {
            this.value = value;
        }
    }
}
