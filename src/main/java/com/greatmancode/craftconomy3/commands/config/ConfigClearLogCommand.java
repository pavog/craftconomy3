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
package com.greatmancode.craftconomy3.commands.config;

import com.greatmancode.craftconomy3.Common;
import com.greatmancode.craftconomy3.commands.AbstractCommand;
import com.greatmancode.craftconomy3.tools.commands.CommandSender;

import java.sql.Timestamp;
import java.util.Calendar;

public class ConfigClearLogCommand extends AbstractCommand {
    public ConfigClearLogCommand(String name) {
        super(name);
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        Calendar calendar = Calendar.getInstance();
        try {
            calendar.add(Calendar.DAY_OF_MONTH, -Integer.parseInt(args[0]));
            Common.getInstance().getStorageHandler().getStorageEngine().cleanLog(new Timestamp(calendar.getTimeInMillis()));
            sendMessage(sender, Common.getInstance().getLanguageManager().getString("log_cleared"));
        } catch (NumberFormatException e) {
            sendMessage(sender, Common.getInstance().getLanguageManager().getString("invalid_time_log"));
        }
    }

    @Override
    public String help() {
        return Common.getInstance().getLanguageManager().getString("craftconomy_clearlog_cmd_help");
    }

    @Override
    public int maxArgs() {
        return 1;
    }

    @Override
    public int minArgs() {
        return 1;
    }

    @Override
    public boolean playerOnly() {
        return false;
    }

    @Override
    public String getPermissionNode() {
        return "craftconomy.config.clearlog";
    }
}
