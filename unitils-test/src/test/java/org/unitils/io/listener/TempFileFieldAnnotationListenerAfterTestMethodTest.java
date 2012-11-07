/*
 * Copyright 2012,  Unitils.org
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

package org.unitils.io.listener;

import org.junit.Before;
import org.junit.Test;
import org.unitils.io.temp.TempService;
import org.unitils.mock.Mock;
import org.unitils.mock.annotation.Dummy;
import org.unitilsnew.UnitilsJUnit4;
import org.unitilsnew.core.TestField;

import java.io.File;

import static org.junit.Assert.fail;

/**
 * @author Tim Ducheyne
 * @author Jeroen Horemans
 * @since 3.3
 */
public class TempFileFieldAnnotationListenerAfterTestMethodTest extends UnitilsJUnit4 {

    /* Tested object */
    private TempFileFieldAnnotationListener tempFileFieldAnnotationListener;

    private Mock<TempService> tempServiceMock;
    private Mock<TestField> testFieldMock;

    @Dummy
    private File testFile;


    @Before
    public void initialize() {
        tempFileFieldAnnotationListener = new TempFileFieldAnnotationListener(tempServiceMock.getMock(), true);
    }


    @Test
    public void cleanup() {
        testFieldMock.returns(testFile).getValue();

        tempFileFieldAnnotationListener.afterTestMethod(null, testFieldMock.getMock(), null, null);

        tempServiceMock.assertInvoked().deleteTempFileOrDir(testFile);
    }

    @Test
    public void nullFile() {
        fail("todo");
    }

    @Test
    public void wrongType() {
        fail("todo");
    }

    @Test
    public void cleanupDisabled() {
        tempFileFieldAnnotationListener = new TempFileFieldAnnotationListener(tempServiceMock.getMock(), false);

        tempFileFieldAnnotationListener.afterTestMethod(null, null, null, null);

        tempServiceMock.assertNotInvoked().deleteTempFileOrDir(null);
    }
}