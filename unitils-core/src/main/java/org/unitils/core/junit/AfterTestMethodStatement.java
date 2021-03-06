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
package org.unitils.core.junit;

import java.lang.reflect.Method;

import org.junit.runners.model.Statement;
import org.unitils.core.TestListener;

/**
 * @author Tim Ducheyne
 */
public class AfterTestMethodStatement extends Statement {

    protected TestListener unitilsTestListener;
    protected Statement nextStatement;
    private Object testObject;
    private Method testMethod;


    public AfterTestMethodStatement(TestListener unitilsTestListener, Statement nextStatement, Method testMethod, Object testObject) {
        this.unitilsTestListener = unitilsTestListener;
        this.nextStatement = nextStatement;
        this.testObject = testObject;
        this.testMethod = testMethod;
    }


    @Override
    public void evaluate() throws Throwable {
        Throwable testThrowable = null;
        try {
            nextStatement.evaluate();
        } catch (Throwable e) {
            testThrowable = e;
        }
        try {
            unitilsTestListener.afterTestMethod(testObject, testMethod, testThrowable);
        } catch (Throwable e) {
            if (testThrowable == null) {
                throw e;
            }
        }
        if (testThrowable != null) {
            throw testThrowable;
        }
    }
}
