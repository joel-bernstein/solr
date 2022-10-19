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
import java.time.temporal.ChronoField;
import java.time.temporal.TemporalAccessor;
import org.apache.solr.io.stream.expr.StreamExpression;
import org.apache.solr.io.stream.expr.StreamFactory;

/** Provides a day stream evaluator */
public class TemporalEvaluatorSecond extends RecursiveTemporalEvaluator {
  protected static final long serialVersionUID = 1L;

  public static final String FUNCTION_NAME = "second";

  public TemporalEvaluatorSecond(StreamExpression expression, StreamFactory factory)
      throws IOException {
    super(expression, factory, FUNCTION_NAME);
  }

  @Override
  protected Object getDatePart(TemporalAccessor value) {
    return value.get(ChronoField.SECOND_OF_MINUTE);
  }
}
