/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.solr.io.eval;

import java.io.IOException;
import java.util.Locale;
import java.util.concurrent.ConcurrentMap;
import org.apache.solr.io.stream.expr.StreamExpression;
import org.apache.solr.io.stream.expr.StreamFactory;

public class RemoveCacheEvaluator extends RecursiveObjectEvaluator implements ManyValueWorker {
  protected static final long serialVersionUID = 1L;

  public RemoveCacheEvaluator(StreamExpression expression, StreamFactory factory)
      throws IOException {
    super(expression, factory);

    if (2 != containedEvaluators.size()) {
      throw new IOException(
          String.format(
              Locale.ROOT,
              "Invalid expression %s - expecting exactly 3 values but found %d",
              expression,
              containedEvaluators.size()));
    }
  }

  @Override
  public Object doWork(Object... values) throws IOException {
    if (values.length == 2) {
      String space = (String) values[0];
      String key = (String) values[1];
      space = space.replace("\"", "");
      key = key.replace("\"", "");

      ConcurrentMap<String, ConcurrentMap<String, Object>> objectCache =
          this.streamContext.getObjectCache();
      ConcurrentMap<String, Object> spaceCache = objectCache.get(space);
      if (spaceCache != null) {
        return spaceCache.remove(key);
      }

      return false;
    } else {
      throw new IOException("The removeCache function requires two parameters: workspace and key");
    }
  }
}
