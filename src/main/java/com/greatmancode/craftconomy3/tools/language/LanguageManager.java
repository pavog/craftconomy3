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
package com.greatmancode.craftconomy3.tools.language;

import com.greatmancode.craftconomy3.tools.configuration.Config;
import com.greatmancode.craftconomy3.tools.configuration.ConfigurationManager;
import com.greatmancode.craftconomy3.tools.interfaces.caller.ServerCaller;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class LanguageManager {
    private Config languageFile = null;
    private Map<String, String> languageList = new HashMap<>();
    private ServerCaller serverCaller;
    private File path;
    private String fileName;

    public LanguageManager(ServerCaller serverCaller, File path, String fileName) {
        languageFile = new ConfigurationManager(serverCaller).loadFile(path, fileName);
        this.serverCaller = serverCaller;
        this.path = path;
        this.fileName = fileName;
        loadLanguage();

    }

    private void loadLanguage() {
        try {
            languageList = languageFile.getStringMap("", new HashMap<>());
        } catch (ClassCastException e) {
            new File(path, fileName).delete();
            languageFile = new ConfigurationManager(serverCaller).loadFile(path, fileName);
            languageList = languageFile.getStringMap("", null);
        }

    }

    public void addLanguageEntry(String key, String value) {
        if (!languageList.containsKey(key)) {
            languageFile.setValue(key, value);
            loadLanguage();
        }
    }

    public String parse(String key, Object... args) {
        return String.format(languageList.get(key), args);
    }

    public String getString(String key) {
        return languageList.get(key);
    }
}
