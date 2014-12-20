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
package org.unitils.core.util;

import org.junit.Test;

import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * @author Tim Ducheyne
 */
public class CollectionUtilsAsSetTest {

    @Test
    public void set() {
        Set<String> result = CollectionUtils.asSet("1", "2", "2", "1");
        assertEquals(2, result.size());
        assertTrue(result.contains("1"));
        assertTrue(result.contains("2"));
    }

    @Test
    public void emptySet() {
        Set<String> result = CollectionUtils.asSet();
        assertTrue(result.isEmpty());
    }

    @Test
    public void nullValue() {
        Set<String> result = CollectionUtils.asSet((String[]) null);
        assertTrue(result.isEmpty());
    }
}
