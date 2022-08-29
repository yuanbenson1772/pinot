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
package org.apache.pinot.plugin.ingestion.batch.hadoop;

import java.io.Serializable;
import java.net.URI;
import java.util.List;
import org.apache.pinot.plugin.ingestion.batch.common.BaseSegmentPushJobRunner;
import org.apache.pinot.segment.local.utils.ConsistentDataPushUtils;
import org.apache.pinot.segment.local.utils.SegmentPushUtils;
import org.apache.pinot.spi.ingestion.batch.spec.Constants;
import org.apache.pinot.spi.ingestion.batch.spec.SegmentGenerationJobSpec;

public class HadoopSegmentUriPushJobRunner extends BaseSegmentPushJobRunner
    implements Serializable {

  public HadoopSegmentUriPushJobRunner() {
  }

  public HadoopSegmentUriPushJobRunner(SegmentGenerationJobSpec spec) {
    init(spec);
  }

  public void getSegmentsToPush() {
    for (String file : _files) {
      URI uri = URI.create(file);
      if (uri.getPath().endsWith(Constants.TAR_GZ_FILE_EXT)) {
        URI updatedURI = SegmentPushUtils
            .generateSegmentTarURI(_outputDirURI, uri, _spec.getPushJobSpec().getSegmentUriPrefix(),
                _spec.getPushJobSpec().getSegmentUriSuffix());
        _segmentsToPush.add(updatedURI.toString());
      }
    }
  }

  public List<String> getSegmentsTo() {
    return ConsistentDataPushUtils.getUriSegmentsTo(_segmentsToPush);
  }

  public void uploadSegments() throws Exception {
    SegmentPushUtils.sendSegmentUris(_spec, _segmentsToPush);
  }
}
