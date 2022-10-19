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
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import org.apache.commons.math3.util.MathArrays;
import org.apache.solr.io.stream.expr.StreamExpression;
import org.apache.solr.io.stream.expr.StreamFactory;

public class EBEMultiplyEvaluator extends RecursiveNumericEvaluator implements TwoValueWorker {
  protected static final long serialVersionUID = 1L;

  public EBEMultiplyEvaluator(StreamExpression expression, StreamFactory factory)
      throws IOException {
    super(expression, factory);
  }

  @Override
  public Object doWork(Object first, Object second) throws IOException {
    if (null == first) {
      throw new IOException(
          String.format(
              Locale.ROOT,
              "Invalid expression %s - null found for the first value",
              toExpression(constructingFactory)));
    }
    if (null == second) {
      throw new IOException(
          String.format(
              Locale.ROOT,
              "Invalid expression %s - null found for the second value",
              toExpression(constructingFactory)));
    }
    if (!(first instanceof List<?>)) {
      throw new IOException(
          String.format(
              Locale.ROOT,
              "Invalid expression %s - found type %s for the first value, expecting a list of numbers",
              toExpression(constructingFactory),
              first.getClass().getSimpleName()));
    }
    if (!(second instanceof List<?>)) {
      throw new IOException(
          String.format(
              Locale.ROOT,
              "Invalid expression %s - found type %s for the second value, expecting a list of numbers",
              toExpression(constructingFactory),
              first.getClass().getSimpleName()));
    }

    double[] result =
        MathArrays.ebeMultiply(
            ((List<?>) first)
                .stream().mapToDouble(value -> ((Number) value).doubleValue()).toArray(),
            ((List<?>) second)
                .stream().mapToDouble(value -> ((Number) value).doubleValue()).toArray());

    List<Number> numbers = new ArrayList<>();
    for (double d : result) {
      numbers.add(d);
    }

    return numbers;
  }
}
