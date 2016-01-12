/******************************************************************************
 * Copyright (C) 2010-2016 CERN. All rights not expressly granted are reserved.
 * 
 * This file is part of the CERN Control and Monitoring Platform 'C2MON'.
 * C2MON is free software: you can redistribute it and/or modify it under the
 * terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation, either version 3 of the license.
 * 
 * C2MON is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for
 * more details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with C2MON. If not, see <http://www.gnu.org/licenses/>.
 *****************************************************************************/
package cern.c2mon.web.restapi.service;

import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cern.c2mon.client.common.listener.DataTagUpdateListener;
import cern.c2mon.client.common.tag.ClientDataTagValue;
import cern.c2mon.web.restapi.cache.ClientDataTagCache;
import cern.c2mon.web.restapi.exception.UnknownResourceException;

/**
 * Service bean for accessing {@link ClientDataTagValue} objects from the C2MON
 * server.
 *
 * @author Justin Lewis Salmon
 */
@Service
public class DataTagService implements DataTagUpdateListener {

  private static final Logger LOG = LoggerFactory.getLogger(DataTagService.class);

  /**
   * Reference to the service gateway bean.
   */
  @Autowired
  private ServiceGateway gateway;

  /**
   * Reference to the data tag cache.
   */
  @Autowired
  private ClientDataTagCache cache;

  /**
   * Retrieve a {@link ClientDataTagValue} object.
   *
   * @param id the ID of the {@link ClientDataTagValue} to retrieve
   * @return the {@link ClientDataTagValue} object
   *
   * @throws UnknownResourceException if no datatag could be found with the
   *           given ID
   */
  public ClientDataTagValue getDataTagValue(Long id) throws UnknownResourceException {
    ClientDataTagValue tag;

    // Try to get the tag from the cache
    if (cache.contains(id)) {
      return cache.get(id);
    }

    // Otherwise, try to get the tag from the server
    List<ClientDataTagValue> list = (List<ClientDataTagValue>) gateway.getTagManager().getDataTags(Arrays.asList(id));
    if (list.isEmpty()) {
      throw new UnknownResourceException("No datatag with id " + id + " was found.");
    } else {
      tag = list.get(0);
    }

    // Subscribe to the tag and add it to the cache
    gateway.getTagManager().subscribeDataTag(id, this);
    cache.add(tag);

    return tag;
  }

  @Override
  public void onUpdate(ClientDataTagValue tagUpdate) {
    // Update the tag in the cache
    cache.add(tagUpdate);
  }
}
