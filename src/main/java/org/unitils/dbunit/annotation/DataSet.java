/*
 * Copyright 2006 the original author or authors.
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
package org.unitils.dbunit.annotation;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;
import java.lang.annotation.Retention;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
import java.lang.annotation.Target;

/**
 * If at class level, indicates that before the execution of each test method in this class, a DbUnit datafile must be
 * loaded, either with the default fileName (ClassName.xml), or with a custom specified fileName.
 * If at method level, indicates that before running this method, a DbUnit datafile must be loaded, either with the
 * default fileName (ClassName.xml), or with a custom specified fileName.
 *
 * @author Filip Neven
 * @author Tim Ducheyne
 */
@Target({TYPE, METHOD})
@Retention(RUNTIME)
public @interface DataSet {

    String value();
}
