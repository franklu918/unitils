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
package org.unitils.dbunit.connection;

import org.junit.Before;
import org.junit.Test;
import org.unitils.mock.Mock;
import org.unitilsnew.UnitilsJUnit4;

import javax.sql.DataSource;
import java.sql.Connection;

/**
 * @author Tim Ducheyne
 */
public class DbUnitDatabaseConnectionCloseJdbcConnectionTest extends UnitilsJUnit4 {

    /* Tested object */
    private DbUnitDatabaseConnection dbUnitDatabaseConnection;

    private Mock<DataSource> dataSourceMock;
    private Mock<Connection> connectionMock;


    @Before
    public void initialize() throws Exception {
        dbUnitDatabaseConnection = new DbUnitDatabaseConnection(dataSourceMock.getMock(), "schema");

        dataSourceMock.returns(connectionMock).getConnection();
    }


    @Test
    public void closeJdbcConnection() throws Exception {
        dbUnitDatabaseConnection.getConnection();

        dbUnitDatabaseConnection.closeJdbcConnection();
        connectionMock.assertInvoked().close();
    }

    @Test
    public void ignoredWhenAlreadyClosed() throws Exception {
        dbUnitDatabaseConnection.getConnection();

        dbUnitDatabaseConnection.closeJdbcConnection();
        dbUnitDatabaseConnection.closeJdbcConnection();
        connectionMock.assertInvoked().close();
        connectionMock.assertNotInvoked().close();
    }

    @Test
    public void ignoredWhenThereIsNoConnection() throws Exception {
        dbUnitDatabaseConnection.closeJdbcConnection();
        connectionMock.assertNotInvoked().close();
    }
}