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
package org.apache.solr.io.stream.eval;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.math3.distribution.UniformRealDistribution;
import org.apache.solr.SolrTestCase;
import org.apache.solr.io.Tuple;
import org.apache.solr.io.eval.UniformDistributionEvaluator;
import org.apache.solr.io.stream.expr.StreamFactory;
import org.junit.Test;

public class UniformDistributionEvaluatorTest extends SolrTestCase {

  StreamFactory factory;
  Map<String, Object> values;

  public UniformDistributionEvaluatorTest() {
    super();
    factory = new StreamFactory().withFunctionName("unif", UniformDistributionEvaluator.class);
    values = new HashMap<>();
  }

  @Test
  public void test() throws IOException {

    values.clear();
    values.put("l1", 3);
    values.put("l2", 7);

    UniformRealDistribution dist = new UniformRealDistribution(3, 7);
    assertEquals(
        dist.getNumericalMean(),
        ((UniformRealDistribution)
                factory.constructEvaluator("unif(l1,l2)").evaluate(new Tuple(values)))
            .getNumericalMean(),
        0.01);
  }
}
