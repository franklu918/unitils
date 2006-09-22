/*
 * Copyright (C) 2006, Ordina
 *
 * Distributable under LGPL license.
 * See terms of license at gnu.org.
 */
package be.ordina.unitils.module;

import be.ordina.unitils.util.ReflectionUtils;
import be.ordina.unitils.util.UnitilsConfiguration;
import org.apache.commons.configuration.Configuration;

import java.util.*;

/**
 * A class for loading unitils modules.
 * <p/>
 * The module names set by the {@link #PROPERTY_MODULES} property which modules will be loaded. These names can then
 * be used to construct properties that define the classnames and optionally the dependencies of these modules. E.g.
 * <pre><code>
 * unitils.modules= a, b, c
 * unitils.module.a.className= be.ordina.A
 * unitils.module.a.runAfter= b, c
 * unitils.module.b.className= be.ordina.B
 * unitils.module.b.runAfter= c
 * unitils.module.c.className= be.ordina.C
 * </code></pre>
 * The above configuration will load 3 module classes A, B and C and will always perform processing in
 * order C, B, A.
 * <p/>
 * If a circular dependency is found in the runAfter configuration, a runtime exception will be thrown.
 */
public class UnitilsModulesLoader {

    /**
     * Property that contains the names of the modules that are to be loaded
     */
    public static final String PROPERTY_MODULES = "unitils.modules";

    /**
     * First part of all module specific properties
     */
    public static final String PROPERTY_MODULE_PREFIX = "unitils.module.";

    /**
     * Last part of the module specific property that specifies the classname of the module
     */
    public static final String PROPERTY_MODULE_SUFFIX_CLASS_NAME = ".className";

    /**
     * Last part of the module specific property that specifies the names of the modules that should be run before this module
     */
    public static final String PROPERTY_MODULE_SUFFIX_RUN_AFTER = ".runAfter";


    /**
     * Loads all unitils modules as described in the class javadoc.
     *
     * @return the modules, not null
     */
    public List<UnitilsModule> loadModules() {

        Configuration configuration = UnitilsConfiguration.getInstance();

        // get all declared modules
        String[] moduleNames = configuration.getStringArray(PROPERTY_MODULES);

        // get all module dependencies
        Map<String, String[]> runAfters = new HashMap<String, String[]>();
        for (String moduleName : moduleNames) {

            // get dependencies for module
            String[] runAfterModuleNames = configuration.getStringArray(PROPERTY_MODULE_PREFIX + moduleName + PROPERTY_MODULE_SUFFIX_RUN_AFTER);
            runAfters.put(moduleName, runAfterModuleNames);
        }

        // Count each time a module is (indirectly) used in runAfter and order by count
        Map<Integer, List<String>> runAfterCounts = new TreeMap<Integer, List<String>>();
        for (String moduleName : moduleNames) {

            // calculate the nr of times a module is (indirectly) referenced
            int count = countRunAfters(moduleName, runAfters, new HashMap<String, String>());

            // store in map with count as key and a list corresponding modules as values
            List<String> countModuleNames = runAfterCounts.get(count);
            if (countModuleNames == null) {
                countModuleNames = new ArrayList<String>();
                runAfterCounts.put(count, countModuleNames);
            }
            countModuleNames.add(moduleName);
        }

        // Create module instances in the correct sequence
        List<UnitilsModule> modules = new ArrayList<UnitilsModule>();
        for (List<String> moduleNameList : runAfterCounts.values()) {
            for (String moduleName : moduleNameList) {

                // get module class name
                String className = configuration.getString(PROPERTY_MODULE_PREFIX + moduleName + PROPERTY_MODULE_SUFFIX_CLASS_NAME);

                // create module instance
                UnitilsModule module = ReflectionUtils.getInstance(className);
                modules.add(module);
            }
        }
        return modules;
    }


    /**
     * Count each time a module is (indirectly) used in runAfter and order by count.
     * <p/>
     * This way all modules can be ordered in such a way that all module dependencies (runAfterz) are met.
     * If no such order can be found (circular dependency) a runtime exception is thrown
     *
     * @param moduleName           the module to count, not null
     * @param allRunAfters         all dependencies as (moduleName, run-after moduleNames) entries, not null
     * @param traversedModuleNames all moduleNames that were already counted as (moduleName, moduleName) entries, not null
     * @return the count
     * @throws RuntimeException if an infinite loop (circular dependency) is found
     */
    private int countRunAfters(String moduleName, Map<String, String[]> allRunAfters, Map<String, String> traversedModuleNames) {

        // Check for infinite loops
        if (traversedModuleNames.containsKey(moduleName)) {

            throw new RuntimeException("Unable to load modules, circular dependency found for modules: " + traversedModuleNames.keySet());
        }
        traversedModuleNames.put(moduleName, moduleName);

        int count = 1;
        String[] runAfters = allRunAfters.get(moduleName);
        for (String currentModuleName : runAfters) {

            // recursively count all dependencies
            count += countRunAfters(currentModuleName, allRunAfters, traversedModuleNames);
        }

        traversedModuleNames.remove(moduleName);
        return count;
    }
}