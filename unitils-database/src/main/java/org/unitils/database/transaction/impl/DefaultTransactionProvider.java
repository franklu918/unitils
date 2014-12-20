/*
 * Copyright 2013,  Unitils.org
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
package org.unitils.database.transaction.impl;

import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.unitils.database.transaction.TransactionProvider;

import javax.sql.DataSource;
import java.util.IdentityHashMap;
import java.util.Map;

/**
 * @author Tim Ducheyne
 */
public class DefaultTransactionProvider implements TransactionProvider {

    protected Map<DataSource, DataSourceTransactionManager> dataSourceTransactionManagers = new IdentityHashMap<DataSource, DataSourceTransactionManager>(3);


    public synchronized PlatformTransactionManager getPlatformTransactionManager(String transactionManagerName, DataSource dataSource) {
        DataSourceTransactionManager dataSourceTransactionManager = dataSourceTransactionManagers.get(dataSource);
        if (dataSourceTransactionManager == null) {
            dataSourceTransactionManager = new DataSourceTransactionManager(dataSource);
            dataSourceTransactionManagers.put(dataSource, dataSourceTransactionManager);
        }
        return dataSourceTransactionManager;
    }
}
