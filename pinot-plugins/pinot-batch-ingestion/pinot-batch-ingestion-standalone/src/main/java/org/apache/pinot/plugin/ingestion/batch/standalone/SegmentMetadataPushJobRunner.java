/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.apache.pinot.plugin.ingestion.batch.standalone;

import java.util.List;
import org.apache.pinot.plugin.ingestion.batch.common.BaseSegmentPushJobRunner;
import org.apache.pinot.segment.local.utils.ConsistentDataPushUtils;
import org.apache.pinot.segment.local.utils.SegmentPushUtils;
import org.apache.pinot.spi.ingestion.batch.spec.SegmentGenerationJobSpec;

public class SegmentMetadataPushJobRunner extends BaseSegmentPushJobRunner {

  public SegmentMetadataPushJobRunner() {
  }

  public SegmentMetadataPushJobRunner(SegmentGenerationJobSpec spec) {
    init(spec);
  }

  public void getSegmentsToPush() {
    _segmentUriToTarPathMap = SegmentPushUtils.getSegmentUriToTarPathMap(_outputDirURI, _spec.getPushJobSpec(), _files);
  }

  public List<String> getSegmentsTo() {
    return ConsistentDataPushUtils.getMetadataSegmentsTo(_segmentUriToTarPathMap);
  }

  public void uploadSegments() throws Exception {
    SegmentPushUtils.sendSegmentUriAndMetadata(_spec, _outputDirFS, _segmentUriToTarPathMap);
  }
}
