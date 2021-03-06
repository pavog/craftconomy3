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
package com.greatmancode.craftconomy3.events;

import com.greatmancode.craftconomy3.Common;
import com.greatmancode.craftconomy3.account.Account;
import com.greatmancode.craftconomy3.tools.events.interfaces.EventHandler;
import com.greatmancode.craftconomy3.tools.events.interfaces.Listener;
import com.greatmancode.craftconomy3.tools.events.playerevent.PlayerJoinEvent;
import com.greatmancode.craftconomy3.tools.events.playerevent.PreJoinEvent;

import java.util.logging.Level;

/**
 * This class contains code shared for events.
 */
public class EventManager implements Listener {

    /**
     * Event handler for when a player is connecting to the server.
     *
     * @param event The PlayerJoinEvent associated with the event
     */
    @EventHandler
    public void playerJoinEvent(PlayerJoinEvent event) {
        if (!Common.getInstance().getMainConfig().getBoolean("System.Setup", true)) {
            if (Common.getInstance().getMainConfig().getBoolean("System.CreateOnLogin", false)) {
                Account acc = Common.getInstance().getAccountManager().getAccount(event.getPlayer().getName(), false);
                if (acc != null) Common.getInstance().getLogger().log(Level.FINER, "Account retrieved for " + event.getPlayer().getDisplayName());
                else
                    Common.getInstance().getLogger().log(Level.FINER, "Account retrieval failed for " + event.getPlayer().getDisplayName());

            }
        }
    }

    @EventHandler
    public void preJoinEvent(PreJoinEvent event) {
        if (!Common.getInstance().getMainConfig().getBoolean("System.Setup", true)) {
            //We search if the UUID is in the database
            Account account = Common.getInstance().getStorageHandler().getStorageEngine().getAccount(event.getUuid());
            if (account != null && !event.getName().equals(account.getAccountName())) {
                Common.getInstance().getAccountManager().clearCache(account.getAccountName());
                Common.getInstance().getStorageHandler().getStorageEngine().updateUsername(event.getName().toLowerCase(), event.getUuid());
            } else if (account == null) {
                //We set deh UUID
                Common.getInstance().getStorageHandler().getStorageEngine().updateUUID(event.getName(), event.getUuid());
            }
        }
    }
}
