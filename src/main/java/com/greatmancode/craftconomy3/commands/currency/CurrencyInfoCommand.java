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
package com.greatmancode.craftconomy3.commands.currency;

import com.greatmancode.craftconomy3.Common;
import com.greatmancode.craftconomy3.commands.AbstractCommand;
import com.greatmancode.craftconomy3.currency.Currency;
import com.greatmancode.craftconomy3.tools.commands.CommandSender;

public class CurrencyInfoCommand extends AbstractCommand {
    public CurrencyInfoCommand(String name) {
        super(name);
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (args.length > 0 && Common.getInstance().getCurrencyManager().getCurrency(args[0]) != null) {
            Currency currency = Common.getInstance().getCurrencyManager().getCurrency(args[0]);
            sendMessage(sender, "{{DARK_GREEN}}======== {{WHITE}}" + currency.getName() + " {{DARK_GREEN}}========");
            sendMessage(sender, Common.getInstance().getLanguageManager().parse("currency_info_name", currency.getName()));
            sendMessage(sender, Common.getInstance().getLanguageManager().parse("currency_info_name_plural", currency.getPlural()));
            sendMessage(sender, Common.getInstance().getLanguageManager().parse("currency_info_minor", currency.getMinor()));
            sendMessage(sender, Common.getInstance().getLanguageManager().parse("currency_info_minor_plural", currency.getMinorPlural()));
        } else {
            sendMessage(sender, Common.getInstance().getLanguageManager().getString("currency_not_exist"));
        }
    }

    @Override
    public String help() {
        return Common.getInstance().getLanguageManager().getString("currency_info_cmd_help");
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
        return "craftconomy.currency.info";
    }
}
